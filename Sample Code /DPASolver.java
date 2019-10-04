import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;;

public class DPASolver {

    public double interval; //formulast from the psedocode
    public double interval_lenght; //formulast from the psedocode
    public int gamma;
    public double epsilon;
    HashMap<String, ArrayList<Integer>> segments = new HashMap<String, ArrayList<Integer>>();
    public long timeElapsed[] = new long[3];

    /**
     * 
     * @param d the budget for all the  bidders
     * @return the maximum budget
     */
    public int findMaxBudget(int[] d) {
        int max = 0;
        for(int i = 0; i < d.length; i++) {
            if(max < d[i]) {
                max = d[i];
            }
        }
        return max;
    }

    /**
     * 
     * @param s is S_i used to calculate the new list S_i+1
     * @param iteration which item we are currently working on
     * @param a the currenct action instance
     * @return s_i+1, the new list based on the values and s_i removing the tuples based on the segments
     */
    public ArrayList<ArrayList<Integer>> performTupleCreation(ArrayList<ArrayList<Integer>> s, int iteration, AuctionProblemInstance a) {
        long startTime = System.nanoTime();

        ArrayList<ArrayList<Integer>> returnVal = new ArrayList<ArrayList<Integer>>();
        for (int  k = 0; k < s.size(); k ++) {
            int iterator = 0;
            for(int i = 0; i < a.n; i++) {
                ArrayList<Integer> allocation = new ArrayList<Integer>();
                int totalGain = s.get(k).get(s.get(k).size() - 1);
                for(int j = 0; j < a.n; j++) {
                    if(iterator == j) {
                        Integer newVal =s.get(k).get(j) + a.b[j][iteration];
                        if(newVal > a.d[j]) {
                            totalGain += (a.d[j] - s.get(k).get(j));
                            newVal = a.d[j];
                        } else {
                            totalGain += a.b[j][iteration];
                        }
                        allocation.add(newVal);
                    } else {
                        allocation.add(s.get(k).get(j));
                    }
                } 
                allocation.add(totalGain);
                updateHashMap(allocation);  
                iterator++;
            } 
        }
        returnVal = getListFromHash();

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        timeElapsed[2] = totalTime + timeElapsed[2];

        return returnVal;
    }

    /**
     * 
     * @param newVals new tuple to be added to hashmap
     * Adds new tuple to hashmaps and removes duplicates if they exist
     */
    public void updateHashMap(ArrayList<Integer> newVals) {
        long startTime = System.nanoTime();
        int temp;

        StringBuilder stringBuild = new StringBuilder();

        for (int i = 0; i < newVals.size()-1; i++) {
            temp = (int)(Math.floor(newVals.get(i)/interval_lenght));
            stringBuild.append(temp);
        }
        String sequence = stringBuild.toString();
        if(segments.containsKey(sequence)) {
            ArrayList<Integer> sameSeq = segments.get(sequence);
            if(newVals.get(newVals.size()-1) > sameSeq.get(sameSeq.size()-1)) {
                segments.replace(sequence, sameSeq, newVals);
            }
        } else {
            segments.put(sequence, newVals);
        }

        long endTime = System.nanoTime();
        timeElapsed[0] = timeElapsed[0] + (endTime - startTime);
         
    }

    /**
     * 
     * @return returns and  arraylist based on the hashmap
     * Clears the hashmap for the next run
     */
    public ArrayList<ArrayList<Integer>> getListFromHash() {
        long startTime = System.nanoTime();
        ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>(segments.values());
        segments.clear();

        long endTime = System.nanoTime();
        timeElapsed[1] = timeElapsed[1] + (endTime - startTime);

        return values;
    }

    /**
     * 
     * @param instance current arraylist to be printed
     * Prints out all the tuples in the arraylist
     */
    public void debugger(ArrayList<ArrayList<Integer>> instance) {
        for (ArrayList<Integer> var : instance) {
            for(int i = 0; i  < var.size(); i++) {
                System.out.print(var.get(i) + "  ");
            }
            System.out.println();
            System.out.println("=========================================================");
        }
    }

    /**
     * 
     * @param a current auction we are considering, command line argument
     * @param eps current epsilon value, command line argument
     * @return the solution to the AuctionProblemInstance
     * Perform the action of getting in an array and calculating the maximum benefit allocated to the auctioneer.
     */
    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, String eps) {

        long startTime = System.nanoTime();

        epsilon = Double.valueOf(eps);

        gamma = findMaxBudget(a.d); //max budget of the bidders
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

        //Should be commented out before turning in, otherwise the algorithm won't return the correct value
        //debugger(s_first);
        
        int max = 0;
        for (ArrayList<Integer> val: s_first) {
            if(val.get(val.size()-1) > max) {
                max = val.get(val.size()-1);
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Total running time " + String.valueOf((endTime - startTime)));

        System.out.println("Time of performTupleCreation in nanosecond: " +  (timeElapsed[2] - timeElapsed[1] -  timeElapsed[0]));
        System.out.println("Time of hashCreation in nanosecond: " +  timeElapsed[0]);
        System.out.println("Time of hash to list conversion in nanosecond " + timeElapsed[1]);
        return new AuctionProblemInstance.Solution(max,epsilon);
    }
}