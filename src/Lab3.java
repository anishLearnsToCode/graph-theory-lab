/*
    Implementing Prim's Algorithm to find minimum Spanning Tree
 */
public class Lab3 {
    public static void main(String[] args) {
        System.out.println("The Original Graph");
        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example9();
        System.out.println(graph);
        System.out.println("Edge Sum: " + graph.edgeSum());

//        UnDirectedWeightedGraph graph = new UnDirectedWeightedGraph();
//        graph.addVertices(3);
//        graph.addEdge(0, 0, 10);
//        graph.addEdge(0, 1, 5);
//        graph.addEdge(0, 1, 10);
//        graph.addEdge(1, 2, 5);

//        System.out.println(graph);
//        System.out.println(graph.edgeSum());
        graph.print();

        System.out.println("\nThe new Graph After Applying Prim's Algorithm");
        UnDirectedWeightedGraph prims = graph.minimumSpanningTreePrims();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
        prims.print();
    }
}
