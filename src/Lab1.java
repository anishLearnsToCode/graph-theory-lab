import java.util.Scanner;

/*
Find number of even and odd vertices
 */
public class Lab1 {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int vertices = SCANNER.nextInt();
        int[][] matrix = getMatrix(vertices);
        UnDirectedGraph graph = UnDirectedGraph.fromMatrix(matrix);
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
}
