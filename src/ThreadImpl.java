
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rakesh
 */
public class ThreadImpl implements Runnable {

    private int trNumber;
    AlgoImpl imple = new AlgoImpl(trNumber);

    public ThreadImpl(int tCount) {
        trNumber = tCount;
    }

    @Override
    public void run() {

        int rType = BankersAlgo.resouceType;

        int[] request = new int[rType];
        int count = 0;

        for (int j = 0; j < rType; j++) {
            if (BankersAlgo.remainingNeed[trNumber][j] <= BankersAlgo.available[j]) {
                request[j] = BankersAlgo.remainingNeed[trNumber][j];
                if (request[j] == 0) {
                    count++;
                } 
                    System.out.println("Customer " + (trNumber + 1) + " requesting " + request[j] + " instances of Resource type " + (j + 1));
                
            } else {
                request[j] = 0;
                try {
                    //  System.out.println("Customer "+ (trNumber + 1) + " waiting");
                    Thread.sleep(10000);
                    //   System.out.println("Customer "+ (trNumber + 1) + " awake");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (count <= rType) {
            imple.implementation(request, trNumber);
        }
    }
}
