public class Lab6 {
    public static void main(String[] args) {
        DirectedGraph graph = DirectedGraph.example1();
        System.out.println(graph);
        long[][] matrix = graph.floydWarshallMinDistances();
        Printer.print(matrix);

        /*
            [0, 3, 5, 6]
            [5, 0, 2, 3]
            [3, 6, 0, 1]
            [2, 5, 7, 0]
         */
    }
}
