/*
    Implementing Prim's Algorithm to find minimum Spanning Tree
 */
public class Lab3 {
    public static void main(String[] args) {
//        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example1();
//        System.out.println(graph);
//        System.out.println("Edge Sum: " + graph.edgeSum());

        UnDirectedWeightedGraph g = new UnDirectedWeightedGraph();
        g.addVertices(3);
        g.addEdge(0, 0, 10);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 1, 10);
        g.addEdge(1, 2, 5);

        System.out.println(g);
        System.out.println(g.edgeSum());
        g.print();

        UnDirectedWeightedGraph prims = g.minimumSpanningTreePrims();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
        prims.print();
    }
}
