import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class FordFulkerson implements Iterable<FordFulkerson.Vertex> {
    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private final int[][] adjacencyMatrix;

    private FordFulkerson(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        addVertices(adjacencyMatrix.length);
        for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
            for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                if (adjacencyMatrix[row][column] > 0) {
                    this.addEdge(row, column, adjacencyMatrix[row][column]);
                }
            }
        }
    }

    @Override
    public Iterator<Vertex> iterator() {
        return vertices.values().iterator();
    }

    @Override
    public String toString() {
        return "DirectedGraph{" +
                "vertices=" + vertices.values() +
                ", adjacencyMatrix=" + toString(adjacencyMatrix) +
                '}';
    }

    public static FordFulkerson example3() {
        // max flow: 4
        int[][] adjacencyMatrix = {
                {0, 4},
                {0, 0}
        };
        return FordFulkerson.from(adjacencyMatrix);
    }

    public static FordFulkerson example4() {
        // max flow: 6
        int[][] matrix = {
                {0, 5, 3, 0},
                {0, 0, 0, 3},
                {0, 1, 2, 10},
                {0, 0, 0, 0}
        };
        return FordFulkerson.from(matrix);
    }

    public static FordFulkerson example5() {
        // max flow: 5
        int[][] matrix = {
                {5, 10, 0},
                {0, 20, 5},
                {0, 0, 1}
        };
        return FordFulkerson.from(matrix);
    }

    public static FordFulkerson example6() {
        // max flow: 4
        int[][] matrix = {
                {0, 2, 3, 0, 0},
                {0, 0, 0, 3, 0},
                {0, 1, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0}
        };
        return FordFulkerson.from(matrix);
    }

    public static FordFulkerson example7() {
        // max flow: 5
        int[][] matrix = {
                {0, 3, 0, 3, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 1, 2, 0, 0},
                {0, 0, 0, 0, 2, 6, 0},
                {0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0}
        };
        return FordFulkerson.from(matrix);
    }

    private String toString(int[][] matrix) {
        StringBuilder result = new StringBuilder().append('[');
        for (int[] row : matrix) {
            result.append(Arrays.toString(row)).append(' ');
        }
        return result.append(']').toString();
    }

    static class Vertex implements Iterable<Edge> {
        private final int data;
        private final Map<Vertex, Edge> edges = new HashMap<>();

        Vertex(int data) {
            this.data = data;
        }

        public void addEdgeTo(Vertex to, Edge edge) {
            this.edges.put(to, edge);
        }

        @Override
        public Iterator<Edge> iterator() {
            return edges.values().iterator();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "val=" + data +
                    ", out degree=" + edges.size() +
                    '}';
        }
    }

    static class Edge {
        private final Vertex from;
        private final Vertex to;
        private int residualCapacity;

        private Edge(Vertex from, Vertex to, int capacity) {
            this.from = from;
            this.to = to;
            this.residualCapacity = capacity;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from.data +
                    ", to=" + to.data +
                    ", residualCapacity=" + residualCapacity +
                    '}';
        }
    }

    public static FordFulkerson from(int[][] adjacencyMatrix) {
        assertIsSquareMatrix(adjacencyMatrix);
        assertEdgesArePositive(adjacencyMatrix);
        return new FordFulkerson(adjacencyMatrix);
    }

    private static void assertIsSquareMatrix(int[][] matrix) {
        int degree  = matrix.length;
        for (int[] row : matrix) {
            if (row.length != degree) {
                throw new RuntimeException("Matrix isn't a square Matrix");
            }
        }
    }

    private static void assertEdgesArePositive(int[][] matrix) {
        for (int[] row : matrix) {
            for(int element : row) {
                if (element < 0) {
                    throw new RuntimeException("Edges should have positive weights");
                }
            }
        }
    }

    private void addVertices(int numberOfVertices) {
        for (int i = 0 ; i < numberOfVertices ; i++) {
            this.vertices.put(i, new Vertex(i));
        }
    }

    private void addEdge(int from, int to, int weight) {
        Vertex fromVertex = vertices.get(from);
        Vertex toVertex = vertices.get(to);
        Edge edge = new Edge(fromVertex, toVertex, weight);
        fromVertex.addEdgeTo(toVertex, edge);
    }

    public int maxFlow(int source, int sink) {
        assertSourceAndSinkNotTheSame(source, sink);
        assertNoParallelEdges();

        FordFulkerson graph = this.withParallelFlowEdges();
        Vertex sourceVertex = graph.vertices.get(source);
        Vertex sinkVertex = graph.vertices.get(sink);
        return graph.maxFlow(sourceVertex, sinkVertex);
    }

    private int maxFlow(Vertex source, Vertex sink) {
        int maxFlow = 0;
        Queue<Vertex> queue = new LinkedList<>();
        Map<Vertex, Vertex> parentMap = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        addSourceAsStartVertex(source, queue, visited);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (current == sink) {
                List<Edge> augmentingPath = getAugmentingPath(current, parentMap);
                int bottleneck = getBottleneck(augmentingPath);
                updateResidualCapacity(augmentingPath, bottleneck);
                maxFlow += bottleneck;
                clearMaxFlowCache(visited, parentMap, queue);
                addSourceAsStartVertex(source, queue, visited);
                continue;
            }

            for (Edge edge : current) {
                if (edge.residualCapacity > 0 && !visited.contains(edge.to)) {
                    queue.add(edge.to);
                    visited.add(edge.to);
                    parentMap.put(edge.to, edge.from);
                }
            }
        }

        return maxFlow;
    }

    private void clearMaxFlowCache(Set<Vertex> visited, Map<Vertex, Vertex> parentMap, Queue<Vertex> queue) {
        visited.clear();
        parentMap.clear();
        queue.clear();
    }

    private void addSourceAsStartVertex(Vertex source, Queue<Vertex> queue, Set<Vertex> visited) {
        queue.add(source);
        visited.add(source);
    }

    private void assertSourceAndSinkNotTheSame(int source, int sink) {
        if (source == sink) {
            throw new RuntimeException("Invalid Args: source and sink can't be the same");
        }
    }

    private FordFulkerson withParallelFlowEdges() {
        FordFulkerson graph = new FordFulkerson(this.adjacencyMatrix);
        for (Vertex vertex : graph) {
            for (Edge edge : vertex) {
                if (edge.residualCapacity > 0) {
                    edge.to.addEdgeTo(edge.from, new Edge(edge.to, edge.from, 0));
                }
            }
        }
        return graph;
    }

    private void assertNoParallelEdges() {
        for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
            for (int column = row + 1 ; column < adjacencyMatrix.length ; column++) {
                if (adjacencyMatrix[row][column] > 0 && adjacencyMatrix[column][row] > 0) {
                    throw new RuntimeException("Parallel Edges in the Graph. There shouldn't be any parallel edges " +
                            "to find the Max Flow");
                }
            }
        }
    }

    private List<Edge> getAugmentingPath(Vertex sink, Map<Vertex, Vertex> parentMap) {
        List<Edge> edges = new ArrayList<>();
        while (parentMap.containsKey(sink)) {
            Vertex parent = parentMap.get(sink);
            edges.add(parent.edges.get(sink));
            sink = parent;
        }

        Collections.reverse(edges);
        return edges;
    }

    private int getBottleneck(List<Edge> edges) {
        int bottleneck = Integer.MAX_VALUE;
        for (Edge edge : edges) {
            bottleneck = Math.min(bottleneck, edge.residualCapacity);
        }
        return bottleneck;
    }

    private void updateResidualCapacity(List<Edge> augmentingPath, int bottleneck) {
        for (Edge edge : augmentingPath) {
            edge.residualCapacity -= bottleneck;
            edge.to.edges.get(edge.from).residualCapacity += bottleneck;
        }
    }
}
