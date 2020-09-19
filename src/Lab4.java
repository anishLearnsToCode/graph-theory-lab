/*
    Implementing Kruskal's Algorithm for finding Minimum Spanning Tree
 */

public class Lab4 {
    public static void main(String[] args) {
        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example9();
        System.out.println(graph);
        System.out.println("Edge Sum: " + graph.edgeSum());
        graph.print();
        System.out.println();

        UnDirectedWeightedGraph kruskal = graph.minimumSpanningTreeKruskal();
        System.out.println(kruskal);
        System.out.println("Edge Sum: " + kruskal.edgeSum());
        kruskal.print();

        UnDirectedWeightedGraph prims = graph.minimumSpanningTreePrims();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
        prims.print();
    }
}
