import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
Find number of even and odd vertices
 */
public class Lab1 {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int vertices = SCANNER.nextInt();
        int[][] matrix = getMatrix(vertices);
        Graph graph = Graph.fromMatrix(matrix);
        System.out.println(graph);
        System.out.println(graph.evenVertices());
        System.out.println(graph.oddVertices());
    }

    private static int[][] getMatrix(int vertices) {
        int[][] matrix = new int[vertices][vertices];
        for (int row = 0 ; row < vertices ; row++) {
            for (int column = 0 ; column < vertices ; column++) {
                matrix[row][column] = SCANNER.nextInt();
            }
        }
        return matrix;
    }

    private static class Graph {
        private final Map<Integer, Vertex> vertices = new HashMap<>();
        private int numberOfVertices;
        private int degree;

        public Graph() {
        }

        public Graph(int numberOfVertices) {
            for (int index = 0 ; index < numberOfVertices ; index++) {
                addVertex(index);
            }
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "vertices=" + vertices.keySet() +
                    ", numberOfVertices=" + numberOfVertices +
                    ", degree=" + degree +
                    '}';
        }

        private static class Vertex {
            int data;
            int degree = 0;
            Set<Edge> edges = new HashSet<>();

            public Vertex(int data) {
                this.data = data;
            }

            public void addEdge(Edge edge) {
                this.edges.add(edge);
                degree++;
            }

            @Override
            public String toString() {
                return "Vertex{" +
                        "data=" + data +
                        ", degree=" + degree +
                        '}';
            }
        }

        private static class Edge {
            Vertex from;
            Vertex to;

            public Edge(Vertex from, Vertex to) {
                this.from = from;
                this.to = to;
            }
        }

        public static Graph fromMatrix(int[][] matrix) {
            Graph graph = new Graph(matrix.length);
            for (int row = 0 ; row < matrix.length ; row++) {
                for (int column = row + 1 ; column < matrix.length ; column++) {
                    if (matrix[row][column] == 1) {
                        graph.addEdge(row, column);
                    }
                }
            }
            return graph;
        }

        public void addVertex(int data) {
            vertices.put(data, new Vertex(data));
            numberOfVertices++;
        }

        public void addEdge(int from, int to) {
            Vertex fromVertex = this.vertices.get(from);
            Vertex toVertex = this.vertices.get(to);
            Edge edge = new Edge(fromVertex, toVertex);
            fromVertex.addEdge(edge);
            toVertex.addEdge(edge);
            degree += 2;
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
    }
}
