import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AuctionProblemInstance {

    public int n; // number of bidders

    public int k; // number of items

    public int[] d; // n element array: d[i]: budget constraint of bidder i

    public int[][] b; // n x k 2D array: b[i][j]: bid of bidder i to item j

    public static class Solution {

        public double epsilon;

        public boolean[][] z; // z[i][j] is true if item j is assigned to bidder i: TODO: fill this variable

        public int value;

        public Solution(int value, double epsilon) {
            this.value = value;
            this.epsilon = epsilon;
        }
    }

    private AuctionProblemInstance(int n, int k, int[] d, int[][] b) {
        this.n = n;
        this.k = k;
        this.d = d;
        this.b = b;
    }

    public int evaluate(int[] assignment) {
        int[] values = new int[this.n];
        for (int i = 0; i < assignment.length; i++) {
            values[assignment[i]] += this.b[assignment[i]][i];
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = Math.min(values[i], this.d[i]);
        }
        return Arrays.stream(values).sum();
    }

    public static Random random = new Random(3003);

    public static AuctionProblemInstance generate(int n, int k, int dMax) {
        int[] d = new int[n];
        d[0] = dMax;
        for (int i = 1; i < d.length; i++) {
            d[i] = random.nextInt(dMax) + 1;
        }
        int[][] b = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                b[i][j] = random.nextInt(d[i]);
            }
        }
        return new AuctionProblemInstance(n, k, d, b);
    }

    public static class IO {

        public static AuctionProblemInstance read(String fileName) throws IOException {
            return read(new File(fileName));
        }

        public static AuctionProblemInstance read(File file) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                int n = Integer.parseInt(br.readLine());
                int k = Integer.parseInt(br.readLine());
                return new AuctionProblemInstance(
                        n,
                        k,
                        IOTools.line2intArray(br.readLine()),
                        IOTools.lines2intArray(IOTools.readLines(br, n), n, k)
                );
            } finally {
                br.close();
            }
        }

        public static String save(AuctionProblemInstance inst) throws IOException {
            String fileName = getFileName(inst);
            save(inst, fileName);
            return fileName;
        }

        static HashMap<String, Integer> nameDict = new HashMap<>(); // do not have files with the same name

        public static String getFileName(AuctionProblemInstance inst) {
            String base_name = String.format("n_%d_k_%d_dmax_%d", inst.n, inst.k,
                    Arrays.stream(inst.d).max().getAsInt());
            int i = 0;
            if (nameDict.containsKey(base_name)) {
                i = nameDict.get(base_name);
            }
            nameDict.put(base_name, i + 1);
            return base_name + "_" + i + ".txt";
        }

        public static void save(AuctionProblemInstance inst, String fileName) throws IOException {
            save(inst, new File(fileName));
        }

        public static void save(AuctionProblemInstance inst, File file) throws IOException {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(Integer.toString(inst.n));
            bw.newLine();
            bw.write(Integer.toString(inst.k));
            bw.newLine();
            bw.write(IOTools.intArray2lines(inst.d));
            bw.newLine();
            for (int[] bs : inst.b) {
                bw.write(IOTools.intArray2lines(bs));
                bw.newLine();
            }
            bw.close();
        }
    }
}
