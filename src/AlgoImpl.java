/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rakesh
 */
public class AlgoImpl {

    public AlgoImpl(int trCount) {
        tCount = trCount;
    }
    private int tCount;
    int rType = BankersAlgo.resouceType;
    int[][] max = BankersAlgo.maxNeed;
    int[][] allocatedInst = BankersAlgo.allocatedResInst;
    int[] avail = BankersAlgo.available;
    int[][] grantedInst = BankersAlgo.grantedResInst;
    int[][] remaining = BankersAlgo.remainingNeed;
    int[] request = new int[rType];

    static class objectLock extends Object {
    }
    static public objectLock lockObject = new objectLock();

    public void implementation(int[] req, int trN) {

        // ----- ACTUAL CODE STARTS HERE -----

        synchronized (lockObject) {

            request = req;
            tCount = trN;
            int trNumber = tCount;
            int count = 0;
            for (int i = 0; i < rType; i++) {


                

                if (BankersAlgo.remainingNeed[trNumber][i] == 0 || BankersAlgo.allocatedResInst[trNumber][i] == BankersAlgo.maxNeed[trNumber][i]) {
                    count++;
                } else if (BankersAlgo.allocatedResInst[trNumber][i] <= BankersAlgo.maxNeed[trNumber][i]) {
                    BankersAlgo.available[i] = BankersAlgo.available[i] - request[i];
                    BankersAlgo.allocatedResInst[tCount][i] = BankersAlgo.allocatedResInst[tCount][i] + request[i];
                    BankersAlgo.remainingNeed[tCount][i] -= request[i];
                    System.out.println(" In Allocation of Customer " + (tCount + 1) + "- Availablilty of " + (i + 1) + "th resource " + avail[i] + " and allocted " + BankersAlgo.allocatedResInst[tCount][i]);
                    Thread.currentThread().run();
                }
            }
            if (count == rType) {
                for (int i = 0; i < rType; i++) {
                    int x = BankersAlgo.allocatedResInst[trNumber][i];
                    BankersAlgo.available[i] += BankersAlgo.allocatedResInst[trNumber][i];
                    BankersAlgo.allocatedResInst[trNumber][i] = 0;
                    BankersAlgo.remainingNeed[trNumber][i] = 0;
                    BankersAlgo.maxNeed[trNumber][i] = 0;    
                    System.out.println(" In Allocation of Customer " + (tCount + 1) + " and released " + x + " instances of resouce type " +(i+1)+" - Availablilty of " + (i + 1) + "th resource " + avail[i]);
                }
            }
        }
    }
}
