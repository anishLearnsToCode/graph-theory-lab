import java.util.Arrays;

public class Printer {
    public static void print(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void print(long[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void print(int[][] matrix) {
        for (int[] row : matrix) {
            print(row);
        }
        System.out.println();
    }

    public static void print(long[][] matrix) {
        for (long[] row : matrix) {
            print(row);
        }
        System.out.println();
    }
}
