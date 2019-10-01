import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            //System.out.println(new BruteForceSolver().solve(AuctionProblemInstance.IO.read(args[0])).value); // replace with your solver here
            //DPASolver mySolver = new DPASolver(Integer.valueOf(args[1]));
            System.out.println(new DPASolver().solve(AuctionProblemInstance.IO.read(args[0]), args[1]).value); // replace with your solver here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
