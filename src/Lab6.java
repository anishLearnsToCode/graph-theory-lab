public class Lab6 {
    public static void main(String[] args) {
        DirectedGraph graph = DirectedGraph.example2();
        System.out.println(graph);
        long[][] matrix = graph.floydWarshallMinDistances();
        Printer.print(matrix);
    }
}
