import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCars = scanner.nextInt();
        int date = scanner.nextInt();
        int[] plates = new int[numberOfCars];
        for (int i = 0 ; i < numberOfCars ; i++) {
            plates[i] = scanner.nextInt();
        }
        Set<Integer> committedOffense = new HashSet<>();
        int fine = 0;
        for (int plate : plates){
            if (plate % 2 != date % 2) {
                fine += committedOffense.contains(plate) ? 200 : 100;
                committedOffense.add(plate);
            }
        }
        System.out.println(fine);
    }
}
