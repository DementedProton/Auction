import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IOTools {

    public static int[] line2intArray(String line) {
        return Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String[] readLines(BufferedReader br, int n) throws IOException {
        String[] arr = new String[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = br.readLine();
        }
        return arr;
    }

    public static int[][] lines2intArray(String[] lines, int n, int k) {
        int[][] arr = new int[n][];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = line2intArray(lines[i]);
        }
        return arr;
    }

    public static String intArray2lines(int[] input) {
        return String.join(" ", Arrays.stream(input).mapToObj(Integer::toString).collect(Collectors.toList()));
    }

}
