public class Lab10 {

    public static void main(String[] args) {
        FordFulkerson graph = FordFulkerson.example7();
        System.out.println(graph);
        System.out.println(graph.maxFlow(0, 6));
    }
}
