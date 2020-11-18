public class Lab2 {
    public static void main(String[] args) {
        int[][] matrix1 = {
                {0, 1, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        int[][] matrix2 = {
                {0, 1, 1, 1},
                {1, 0, 1, 0},
                {1, 1, 0, 0},
                {1, 0, 0, 0}
        };

        // testing union
        UnDirectedGraph graph = UnDirectedGraph.fromMatrix(matrix1).union(matrix2);
        System.out.println("Union of 2 Graphs");
        System.out.println(graph);

        // testing intersection
        UnDirectedGraph intersection = UnDirectedGraph.fromMatrix(matrix1).intersection(matrix2);
        System.out.println("\nIntersection of 2 Graphs");
        System.out.println(intersection);

        // testing ring sum
        UnDirectedGraph ringSum = UnDirectedGraph.fromMatrix(matrix1).ringSum(matrix2);
        System.out.println("\nRing Sum of 2 Graphs");
        System.out.println(ringSum);

        // testing complement of graph
        UnDirectedGraph complement = UnDirectedGraph.fromMatrix(matrix1).complement();
        System.out.println("\nComplement of Graph");
        System.out.println(complement);
    }
}
