
import java.util.Random;
import java.util.Scanner;
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
public class BankersAlgo {

    static int threadCount;
    static int resouceType;
    static int[] resourceInst;
    static int[][] maxNeed;
    static int[][] allocatedResInst;
    static int[] available;
    static int[][] grantedResInst;
    static int[][] remainingNeed;
    static int[][] x, y;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Requesting and Accepting inputs from the user

        System.out.println("Welcome to execution of Bankers Algorithm !!");
        System.out.println("Please enter the following inputs : ");
        System.out.println("Enter the number of Customers(threads) requesting resources ");
        Scanner scan = new Scanner(System.in);
        threadCount = scan.nextInt();
        System.out.println("Enter the number of Resource types ");
        resouceType = scan.nextInt();
        System.out.println("Enter the number of resource instances for each resoucre type starting from the 1st to " + resouceType + "th ");
        resourceInst = new int[resouceType];
        for (int i = 0; i < resouceType; i++) {
            resourceInst[i] = scan.nextInt();
        }

        // Displaying the user inputs for verification
        System.out.println("");
        System.out.println("/--- Input Verification ---/");
        System.out.println("Customers Count (No. of threads requesting resources) : " + threadCount);
        System.out.println("Number of Resource types : " + resouceType);
        System.out.println("Resouce Instances ");
        for (int i = 0; i < resouceType; i++) {
            System.out.println("Resource " + (i + 1) + " - " + resourceInst[i] + " instances ");
        }

        maxNeed = new int[threadCount][resouceType];
        allocatedResInst = new int[threadCount][resouceType];
        remainingNeed = new int[threadCount][resouceType];
        x = new int[threadCount][resouceType];
        y = new int[threadCount][resouceType];

        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < resouceType; j++) {
                try {
                    maxNeed[i][j] = Random.class.newInstance().nextInt(resourceInst[j]);
                    if (maxNeed[i][j] == 0) {
                        maxNeed[i][j] = 1;
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(BankersAlgo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(BankersAlgo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < resouceType; j++) {
                System.out.println("Need by Customer " + (i + 1) + " of resouce " + (j + 1) + " : " + maxNeed[i][j]);
            }
        }

        /*     // Requesting resources for each Customer(thread) starting by the 1st Customer(thread)
         // Generating maxNeed of the 1st Customer(thread) */

        // Generating Allocated Resouces instances of each resource type
        System.out.println("");
        available = new int[resouceType];
        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < resouceType; j++) {
                x[i][j] = 0;
                y[i][j] = 0;
            }
        }

        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < resouceType; j++) {
                try {
                    int m = (resourceInst[j] - maxNeed[i][j]) / 3;
                    if (m < maxNeed[i][j]) {
                        x[i][j] = m;
                    } else {
                        x[i][j] = maxNeed[i][j];
                    }
                    /*       x[i][j] = Random.class.newInstance().nextInt(m); */

                    //   available[j] = resourceInst[j]-x[i][j];
                  /*     if(available[j]>=0){*/
                    y[i][j] += x[i][j];

                    if (y[i][j] < resourceInst[j]) {
                        allocatedResInst[i][j] = x[i][j];
                    } else {
                        allocatedResInst[i][j] = resourceInst[j] - x[i][j];
                    }
                    remainingNeed[i][j] = maxNeed[i][j] - allocatedResInst[i][j];
                    System.out.println("No. of resource instances currently hold by/or allocated to " + (i + 1) + "th Customer of resource type - " + (j + 1) + " : " + allocatedResInst[i][j]);
                } catch (Exception ex) {
                    Logger.getLogger(BankersAlgo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        int[] arbitaryX = new int[resouceType];
        for (int j = 0; j < resouceType; j++) {
            for (int i = 0; i < threadCount; i++) {
                arbitaryX[j] += allocatedResInst[i][j];
            }
        }

        System.out.println();
        for (int j = 0; j < resouceType; j++) {
            available[j] = resourceInst[j] - arbitaryX[j];
            System.out.println("Availablilty of " + (j + 1) + " th resource type : " + available[j]);
        }

        // Starting thread execution
        for (int i = 0; i < threadCount; i++) {
            Thread customerThread = new Thread(new ThreadImpl(i));
            customerThread.start();
        }

    }
}
