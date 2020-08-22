import java.util.*;

public class UnDirectedWeightedGraph implements Iterable<UnDirectedWeightedGraph.Vertex> {
    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private int numberOfVertices = 0;
    private int degree = 0;
    private int batchSize = 0;

    @Override
    public String toString() {
        return "Graph{" +
                "numberOfVertices=" + numberOfVertices +
                ", degree=" + degree +
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

        @Override
        public Iterator<Edge> iterator() {
            return edges.iterator();
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

    public int edgeSum() {
        int sum = 0;
        Set<Edge> considered = new HashSet<>();
        for (Vertex vertex : this) {
            for (Edge edge : vertex) {
                if (!considered.contains(edge)) {
                    sum += edge.weight;
                    considered.add(edge);
                }
            }
        }
        return sum;
    }
}
