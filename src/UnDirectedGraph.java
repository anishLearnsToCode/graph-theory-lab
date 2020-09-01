import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnDirectedGraph {
    private final int[][] adjacencyMatrix;
    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private int numberOfVertices = 0;
    private int degree = 0;

    public static UnDirectedGraph fromMatrix(int[][] matrix) {
        return new UnDirectedGraph(matrix);
    }

    private UnDirectedGraph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        for (int index = 0 ; index < adjacencyMatrix.length ; index++) {
            addVertex(index);
        }
        for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
            for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                if (adjacencyMatrix[row][column] == 1) {
                    addEdge(row, column);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Graph{" + "\n"  +
                "adjacencyMatrix=\n" + toString(adjacencyMatrix) +
                ", numberOfVertices=" + numberOfVertices +
                ", degree=" + degree + "\n" +
                '}';
    }

    private String toString(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int[] row : matrix) {
            result.append(toString(row)).append("\n");
        }
        return result.toString();
    }

    private String toString(int[] array) {
        return Arrays.toString(array);
    }

    private static class Vertex {
        Set<Edge> edges = new HashSet<>();
        int degree = 0;

        public void addEdge(Edge edge) {
            edges.add(edge);
            degree++;
        }
    }

    private static class Edge {
        private final Vertex from;
        private final Vertex to;

        Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }
    }

    public void addVertex(int data) {
        this.vertices.put(data, new Vertex());
        numberOfVertices++;
    }

    public void addEdge(int from, int to) {
        Vertex fromVertex = vertices.get(from);
        Vertex toVertex = vertices.get(to);
        Edge edge = new Edge(fromVertex, toVertex);
        fromVertex.addEdge(edge);
        toVertex.addEdge(edge);
        degree += 2;
    }

    public UnDirectedGraph union(UnDirectedGraph other) {
        return union(other.adjacencyMatrix);
    }

    public UnDirectedGraph union(int[][] other) {
        if (dimensionsMismatch(other)) {
            System.out.println("Dimensions Mismatch");
            return null;
        }

        int[][] result = new int[this.adjacencyMatrix.length][this.adjacencyMatrix.length];
        for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
            for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                result[row][column] = adjacencyMatrix[row][column] + other[row][column] > 0 ? 1 : 0;
            }
        }

        return UnDirectedGraph.fromMatrix(result);
    }

    public UnDirectedGraph intersection(UnDirectedGraph other) {
        return intersection(other.adjacencyMatrix);
    }

    public UnDirectedGraph intersection(int[][] other) {
        if (dimensionsMismatch(other)) {
            System.out.println("Dimensions Mismatch");
            return null;
        }

        int[][] result = new int[adjacencyMatrix.length][adjacencyMatrix.length];
        for (int row = 0 ; row < result.length ; row++) {
            for (int column = 0 ; column < result.length ; column++) {
                result[row][column] = adjacencyMatrix[row][column] + other[row][column] == 2 ? 1 : 0;
            }
        }
        return UnDirectedGraph.fromMatrix(result);
    }

    public UnDirectedGraph ringSum(UnDirectedGraph other) {
        return ringSum(other.adjacencyMatrix);
    }

    public UnDirectedGraph ringSum(int[][] other) {
        if (dimensionsMismatch(other)) {
            System.out.println("Dimensions Mismatch");
            return null;
        }

        int[][] result = new int[adjacencyMatrix.length][adjacencyMatrix.length];
        for (int row = 0 ; row < result.length ; row++) {
            for (int column = 0 ; column < result.length ; column++) {
                result[row][column] = adjacencyMatrix[row][column] + other[row][column] == 1 ? 1 : 0;
            }
        }

        return UnDirectedGraph.fromMatrix(result);
    }

    private boolean dimensionsMismatch(int[][] other) {
        return this.adjacencyMatrix.length != other.length
                || this.adjacencyMatrix[0].length != other[0].length;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public int getDegree() {
        return degree;
    }

    public int evenVertices() {
        int even = 0;
        for (Vertex vertex : this.vertices.values()) {
            even += vertex.degree % 2 == 0 ? 1 : 0;
        }
        return even;
    }

    public int oddVertices() {
        return numberOfVertices - evenVertices();
    }

    public UnDirectedGraph complement() {
        return complement(this.adjacencyMatrix);
    }

    public UnDirectedGraph complement(int[][] adjacencyMatrix) {
        int[][] result = new int[adjacencyMatrix.length][adjacencyMatrix.length];
        for (int row = 0 ; row < result.length ; row++) {
            for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                result[row][column] = adjacencyMatrix[row][column] == 1 ? 0 : 1;
            }
        }
        return UnDirectedGraph.fromMatrix(result);
    }
}
