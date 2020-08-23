import java.util.Map;
import java.util.Scanner;

/*
    Implement Bellman Fords Algorithm
 */
public class Lab7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DirectedGraph graph = DirectedGraph.example2();
        System.out.println(graph);
        System.out.print("Enter source vertex: \t");
        int source = scanner.nextInt();
        Map<Integer, Long> distances = graph.bellmanFord(source);

        // {0=0, 1=1, 2=3, 3=5, 4=0, 5=4, 6=3}
        System.out.println(distances);
    }
}
