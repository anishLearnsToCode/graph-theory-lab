import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DirectedGraph implements Iterable<DirectedGraph.Vertex>  {
    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private int numberOfVertices = 0;
    private int degree = 0;
    private int batchIndex = 0;

    public static DirectedGraph example1() {
        DirectedGraph graph = new DirectedGraph();
        graph.addVertices(4);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);
        graph.addEdge(1, 0, 8);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 5);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 0, 2);
        return graph;
    }

    @Override
    public String toString() {
        return "Graph {" +
                "vertices=" + vertices.keySet() + ", " +
                "numberOfVertices=" + numberOfVertices +
                ", degree=" + degree +
                '}';
    }

    @Override
    public Iterator<Vertex> iterator() {
        return vertices.values().iterator();
    }

    public static class Vertex implements Iterable<Edge> {
        private final int data;
        private final Map<Vertex, List<Edge>> edges = new HashMap<>();

        Vertex(int data) {
            this.data = data;
        }

        private void addEdge(Edge edge) {
            List<Edge> edgeList = edges.getOrDefault(edge.to, new ArrayList<>());
            edgeList.add(edge);
            edges.put(edge.to, edgeList);
        }

        @Override
        public Iterator<Edge> iterator() {
            Map<Vertex, Edge> shortestEdges = getShortestEdges();
            return shortestEdges.values().iterator();
        }

        private Map<Vertex, Edge> getShortestEdges() {
            Map<Vertex, Edge> result = new HashMap<>();
            for (Map.Entry<Vertex, List<Edge>> entry : edges.entrySet()) {
                Edge smallestEdge = entry.getValue().stream().min(Edge::compareTo).get();
                result.put(entry.getKey(), smallestEdge);
            }
            return result;
        }
    }

    private static class Edge implements Comparable<Edge> {
        private final Vertex from;
        private final Vertex to;
        private final int weight;

        Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }

    public void addVertex(int data) {
        if (!vertices.containsKey(data)) {
            vertices.put(data, new Vertex(data));
            numberOfVertices++;
        }
    }

    public void addVertices(int batch) {
        while (batch-- > 0) {
            addVertex(batchIndex++);
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        Vertex fromVertex = vertices.get(from);
        Vertex toVertex = vertices.get(to);
        Edge edge = new Edge(fromVertex, toVertex, weight);
        fromVertex.addEdge(edge);
        degree++;
    }

    public long[][] floydWarshallMinDistances() {
        long[][] adjacencyMatrix = getAdjacencyMatrix();
        for (int k = 0 ; k < adjacencyMatrix.length ; k++) {
            for (int i = 0 ; i < adjacencyMatrix.length ; i++) {
                for (int j = 0 ; j < adjacencyMatrix.length ; j++) {
                    adjacencyMatrix[i][j] = Math.min(
                            adjacencyMatrix[i][j],
                            adjacencyMatrix[i][k] + adjacencyMatrix[k][j]
                    );
                }
            }
        }
        return adjacencyMatrix;
    }

    private long[][] getAdjacencyMatrix() {
        long[][] matrix = new long[numberOfVertices][numberOfVertices];
        assignAllInfinity(matrix);
        for (Vertex vertex : this) {
            matrix[vertex.data][vertex.data] = 0;
            for (Edge edge : vertex) {
                matrix[vertex.data][edge.to.data] = edge.weight;
            }
        }
        return matrix;
    }

    private void assignAllInfinity(long[][] matrix) {
        for (int i = 0 ; i < matrix.length ; i++) {
            for (int j = 0 ; j < matrix.length ; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }
}
