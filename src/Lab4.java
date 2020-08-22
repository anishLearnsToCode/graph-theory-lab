/*
    Implementing Kruskal's Algorithm for finding Minimum Spanning Tree
 */
public class Lab4 {
    public static void main(String[] args) {
        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example1();
        System.out.println(graph);
        System.out.println("Edge Sum: " + graph.edgeSum());

        UnDirectedWeightedGraph prims = graph.minimumSpanningTreeKruskal();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
    }
}
