/*
    Implementing Prim's Algorithm to find shortest Spanning Tree
 */
public class Lab3 {
    public static void main(String[] args) {
        UnDirectedWeightedGraph graph = new UnDirectedWeightedGraph();
        graph.addVertices(6);

        graph.addEdge(0, 1, 7);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 4, 3);
        graph.addEdge(0, 2, 8);
        graph.addEdge(2, 3, 4);
        graph.addEdge(1, 3, 6);
        graph.addEdge(1, 3, 8);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(4, 5, 2);
        graph.addEdge(5, 5, 1);

        System.out.println(graph);
        System.out.println("Edge Sum: " + graph.edgeSum());

        UnDirectedWeightedGraph prims = graph.minimumSpanningTreePrims();
        System.out.println(prims);
        System.out.println("Edge Sum: " + prims.edgeSum());
    }
}
