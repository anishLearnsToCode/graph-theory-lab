import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UnDirectedWeightedGraph implements Iterable<UnDirectedWeightedGraph.Vertex> {
    public final Map<Integer, Vertex> vertices = new HashMap<>();
    private int numberOfVertices = 0;
    private int degree = 0;
    private int batchSize = 0;

    public static UnDirectedWeightedGraph example9() {
        UnDirectedWeightedGraph graph = new UnDirectedWeightedGraph();
        graph.addVertices(6);
        graph.addEdge(0, 1, 7);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 4, 3);
        graph.addEdge(0, 2, 8);
        graph.addEdge(2, 3, 4);
        graph.addEdge(1, 3, 6);
        graph.addEdge(1, 3, 8);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(4, 5, 2);
        graph.addEdge(5, 5, 1);
        return graph;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "order=" + numberOfVertices +
                ", size=" + degree / 2 +
                '}';
    }

    @Override
    public Iterator<Vertex> iterator() {
        return vertices.values().iterator();
    }

    public static class Vertex implements Iterable<Edge> {
        private final Set<Edge> edges = new HashSet<>();
        private final int data;
        private int degree = 0;

        private Vertex(int data) {
            this.data = data;
        }

        private void addEdge(Edge edge) {
            edges.add(edge);
            degree++;
        }

        private boolean pathTo(Vertex to) {
            return pathTo(to, new HashSet<>());
        }

        private boolean pathTo(Vertex to, Set<Vertex> visited) {
            if (visited.contains(this)) {
                return false;
            }
            if (this == to) {
                return true;
            }
            visited.add(this);
            for (Edge edge : this) {
                if (edge.to.pathTo(other(this, edge), visited)) {
                    return true;
                }
            }
            return false;
        }

        private Vertex other(Edge edge) {
            return other(this, edge);
        }

        private Vertex other(Vertex vertex, Edge edge) {
            if (edge.from == vertex) {
                return edge.to;
            }
            return edge.from;
        }

        @Override
        public Iterator<Edge> iterator() {
            return edges.iterator();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "data=" + data +
                    ", degree=" + degree +
                    ", edges= [" + edgesFrom() + "]" +
                    '}';
        }

        private String edgesFrom() {
            StringBuilder result = new StringBuilder();
            for (Edge edge : edges) {
                result.append(other(edge).data).append(" ");
            }
            return result.toString();
        }
    }

    public static class Edge implements Comparable<Edge> {
        private final Vertex from;
        private final Vertex to;
        private final int weight;

        private Edge(Vertex from, Vertex to) {
            this(from, to, 1);
        }

        private Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from.data +
                    ", to=" + to.data +
                    ", weight=" + weight +
                    '}';
        }
    }

    private void addVertex(int data) {
        if (!vertices.containsKey(data)) {
            vertices.put(data, new Vertex(data));
            numberOfVertices++;
        }
    }

    public void addVertices(int quantity) {
        for (int i = 0 ; i < quantity ; i++) {
            addVertex(batchSize++);
        }
    }

    public boolean containsVertex(int data) {
        return vertices.containsKey(data);
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    private void addEdge(Edge edge) {
        int from = edge.from.data;
        int to = edge.to.data;
        addVertex(from);
        addVertex(to);
        addEdge(from, to, edge.weight);
    }

    public void addEdge(int from, int to, int weight) {
        Vertex fromVertex = vertices.get(from);
        Vertex toVertex = vertices.get(to);
        Edge edge = new Edge(fromVertex, toVertex, weight);
        fromVertex.addEdge(edge);
        toVertex.addEdge(edge);
        degree += 2;
    }

    public UnDirectedWeightedGraph minimumSpanningTreePrims() {
        UnDirectedWeightedGraph graph = new UnDirectedWeightedGraph();
        Set<Vertex> included = new HashSet<>();
        Queue<Edge> minEdgeHeap = new PriorityQueue<>(Edge::compareTo);
        if (numberOfVertices == 0) {
            return graph;
        }
        graph.addVertex(0);
        included.add(vertices.get(0));
        minEdgeHeap.addAll(vertices.get(0).edges);

        while (!minEdgeHeap.isEmpty()) {
            Edge edge = minEdgeHeap.poll();
            if (included.contains(edge.from) && included.contains(edge.to)) {
                continue;
            }

            graph.addEdge(edge);
            included.add(edge.from);
            included.add(edge.to);
            minEdgeHeap.addAll(edge.from.edges);
            minEdgeHeap.addAll(edge.to.edges);
        }

        return graph;
    }

    public void print() {
        for (Vertex vertex : this) {
            System.out.println(vertex);
        }
    }

    public boolean pathBetween(int from, int to) {
        if (containsVertex(from) && containsVertex(to)) {
            return vertices.get(from).pathTo(vertices.get(to));
        }
        return false;
    }

    public UnDirectedWeightedGraph minimumSpanningTreeKruskal() {
        Set<Edge> edges = edges();
        Queue<Edge> heap = new PriorityQueue<>(Edge::compareTo);
        heap.addAll(edges);
        UnDirectedWeightedGraph result = new UnDirectedWeightedGraph();
        while (!heap.isEmpty()) {
            Edge edge = heap.poll();
            if (edge.from == edge.to) {
                continue;
            }
            if (!result.pathBetween(edge.from.data, edge.to.data)) {
                result.addEdge(edge);
            }
        }
        return result;
    }

    private Set<Edge> edges() {
        Set<Edge> result = new HashSet<>();
        for (Vertex vertex : this) {
            for (Edge edge : vertex) {
                result.add(edge);
            }
        }
        return result;
    }

    public int edgeSum() {
        int sum = 0;
        Set<Edge> considered = new HashSet<>();
        for (Vertex vertex : this) {
            for (Edge edge : vertex) {
                if (!considered.contains(edge) && !considered.contains(edge)) {
                    sum += edge.weight;
                    considered.add(edge);
                }
            }
        }
        return sum;
    }

    private class DijkstraInfo implements Comparable<DijkstraInfo> {
        private final Vertex vertex;
        private final int minDistance;

        DijkstraInfo(Vertex vertex, int minDistance) {
            this.vertex = vertex;
            this.minDistance = minDistance;
        }

        @Override
        public int compareTo(DijkstraInfo other) {
            return Integer.compare(this.minDistance, other.minDistance);
        }
    }

    public Map<Integer, Integer> dijkstra(int source) {
        Map<Integer, Integer> distances = new HashMap<>();
        distances.put(source, 0);
        Queue<DijkstraInfo> queue = new PriorityQueue<>(DijkstraInfo::compareTo);
        queue.add(new DijkstraInfo(vertices.get(0), 0));
        Set<Vertex> computed = new HashSet<>();

        while (!queue.isEmpty()) {
            DijkstraInfo info = queue.poll();
            if (computed.contains(info.vertex)) {
                continue;
            }
            computed.add(info.vertex);
            for (Edge edge : info.vertex) {
                Vertex to = info.vertex.other(edge);
                int shortestDistance = Math.min(distances.getOrDefault(to.data, Integer.MAX_VALUE),
                        distances.getOrDefault(info.vertex.data, Integer.MAX_VALUE) + edge.weight);
                distances.put(to.data, shortestDistance);
                queue.add(new DijkstraInfo(to, shortestDistance));
            }
        }

        return distances;
    }

    private void computeMinDistances(Vertex vertex, Map<Integer, Integer> distances, Set<Vertex> computed) {
        if (computed.contains(vertex)) {
            return;
        }
        computed.add(vertex);

        for (Edge edge : vertex) {
            Vertex to = vertex.other(edge);
            int shortestDistance = Math.min(distances.getOrDefault(to.data, Integer.MAX_VALUE),
                    distances.getOrDefault(vertex.data, Integer.MAX_VALUE) + edge.weight);
            distances.put(to.data, shortestDistance);
            computeMinDistances(to, distances, computed);
        }
    }
}
