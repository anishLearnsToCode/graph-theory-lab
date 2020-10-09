import java.util.Map;
import java.util.Scanner;

public class Lab5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UnDirectedWeightedGraph graph = UnDirectedWeightedGraph.example9();

        System.out.println(graph);
        int sourceVertex = scanner.nextInt();
        int destinationVertex = scanner.nextInt();

        Map<Integer, Long> shortestDistance = graph.dijkstra(sourceVertex);
        System.out.println(shortestDistance);
        System.out.println("Distance between " + sourceVertex + " & " + destinationVertex + " : " +
                shortestDistance.getOrDefault(destinationVertex, Long.MAX_VALUE)
        );
    }
}
