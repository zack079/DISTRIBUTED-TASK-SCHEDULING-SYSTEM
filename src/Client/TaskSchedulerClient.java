package Client;

import Client.ComputationTask;
import Server.TaskResult;
import Server.TaskSchedulerInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class TaskSchedulerClient {
    public static int generateId(){
        Random rand = new Random(System.currentTimeMillis());
        int id = rand.nextInt(1000) ;
        return id;
    }
    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",2022);

            TaskSchedulerInterface taskScheduler = (TaskSchedulerInterface) registry.lookup("taskScheduler");
            int uniqueID = generateId();
            int[][] matrix1 = {{1, 2}, {4, 5}};
            int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};
            // Submit a task to the server
            int taskId = taskScheduler.submitTask( new MatrixMultiplier (uniqueID , matrix1,matrix2));
            System . out . println (" Submitted task with ID " + taskId );
            // Wait for the task to complete

            TaskResult result = taskScheduler . getResult ( taskId ) ;
            int[][] matrixProduct=(int[][])result . getResult ();
            System.out.println("Result of matrix multiplication:");
            for (int i = 0; i < matrixProduct.length; i++) {
                for (int j = 0; j < matrixProduct[0].length; j++) {
                    System.out.print(matrixProduct[i][j] + " ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
