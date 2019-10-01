import java.util.ArrayList;

public class DPASolver {

    public double interval;
    public double interval_lenght;
    public int gamma;
    public double epsilon;

    public int findMaxBudget(int[] d) {
        int max = 0;
        for(int i = 0; i < d.length; i++) {
            if(max < d[i]) {
                max = d[i];
            }
        }

        return max;
    }

    public ArrayList<ArrayList<Integer>> performTupleCreation(ArrayList<ArrayList<Integer>> s, int iteration, AuctionProblemInstance a) {
        ArrayList<ArrayList<Integer>> returnVal = new ArrayList<ArrayList<Integer>>();
        for (int  k = 0; k < s.size(); k ++) {
            int iterator = 0;
            for(int i = 0; i < a.n; i++) {
                ArrayList<Integer> allocation = new ArrayList<Integer>();
                int totalGain = s.get(k).get(s.get(k).size() - 1);
                for(int j = 0; j < a.n; j++) {
                    if(iterator == j) {
                        Integer newVal =s.get(k).get(j) + a.b[j][iteration];
                        allocation.add(newVal);
                        totalGain += a.b[j][iteration];
                    } else {
                        allocation.add(s.get(k).get(j));
                    }
                } 
                allocation.add(totalGain);
                returnVal.add(allocation);   
                iterator++;
            } 
        }
        return returnVal;
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, String eps) {
        epsilon = Double.valueOf(eps);
        System.out.println("epsilon: " +  epsilon);

        gamma = findMaxBudget(a.d);
        interval = a.n*a.k/epsilon;
        interval_lenght = gamma*epsilon/a.k;

        ArrayList<Integer> allocation = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> s_first = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> s_second = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i < a.n; i++) {
            allocation.add(0);
            allocation.add(0);
        }
        allocation.add(0);
        s_first.add(allocation);

        for(int j = 0; j < a.k; j++) {
            s_second = performTupleCreation(s_first, j, a);
            s_first = s_second;
        }
        //Array debugger;
        for (ArrayList<Integer> var : s_first) {
            for(int i = 0; i  < var.size(); i++) {
                System.out.print(var.get(i) + "  ");
            }
            System.out.println();
            System.out.println("=============================");
        }
        return new AuctionProblemInstance.Solution(0,0);
    }
}