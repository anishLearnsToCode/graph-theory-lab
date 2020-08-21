import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Lab2 {
    public static void main(String[] args) {
        int[][] matrix1 = {
                {0, 1, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        int[][] matrix2 = {
                {0, 0, 1, 1},
                {0, 0, 1, 0},
                {1, 1, 0, 0},
                {1, 0, 0, 0}
        };

        // testing union
        UnDirectedGraph graph = UnDirectedGraph.fromMatrix(matrix1).union(matrix2);
        System.out.println(graph);

        // testing intersection
        UnDirectedGraph intersection = UnDirectedGraph.fromMatrix(matrix1).intersection(matrix2);
        System.out.println(intersection);
    }
}
