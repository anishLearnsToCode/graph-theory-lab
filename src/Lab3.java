/*
    Implementing Prim's Algorithm to find shortest Spanning Tree
 */
public class Lab3 {
    public static void main(String[] args) {
        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example1();
        System.out.println(graph);
        System.out.println("Edge Sum: " + graph.edgeSum());

        UnDirectedWeightedGraph prims = graph.minimumSpanningTreePrims();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
    }
}
