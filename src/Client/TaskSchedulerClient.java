package Client;

import Server.TaskResult;
import Server.TaskSchedulerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class TaskSchedulerClient {
    public static int generateId() {
        Random rand = new Random(System.currentTimeMillis());
        int id = rand.nextInt(1000);
        return id;
    }

    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2022);

            TaskSchedulerInterface taskScheduler = (TaskSchedulerInterface) registry.lookup("taskScheduler");
            int uniqueID1 = generateId();
            int uniqueID2 = uniqueID1+1;
            int[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
            int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};
            // Submit a tasks to the server
            int taskId1 = taskScheduler.submitTask(new ComputationTask(uniqueID1, 10000));
            System.out.println(" Submitted task with ID " + taskId1);

            int taskId2 = taskScheduler.submitTask(new MatrixMultiplier(uniqueID2, matrix1, matrix2));
            System.out.println(" Submitted task with ID " + taskId2);

            // Wait for the task to complete

            TaskResult result1 = taskScheduler.getResult(taskId1);
            TaskResult result2 = taskScheduler.getResult(taskId2);


            long ComputationTaskDuration = (long) result1.getResult();
            int[][] matrixProduct = (int[][]) result2.getResult();


            System.out.println("Result of task with id:" + result1.getTaskId());
            System.out.println(ComputationTaskDuration);


            System.out.println("Result of task with id:" + result2.getTaskId());
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
