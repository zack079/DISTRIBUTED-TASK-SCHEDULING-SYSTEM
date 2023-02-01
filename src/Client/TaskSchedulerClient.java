package Client;

import Server.TaskResult;
import Server.TaskSchedulerInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskSchedulerClient {

    //private static List<byte[]> serializedFiles=new ArrayList<>();

    public static int generateId() {
        Random rand = new Random(System.currentTimeMillis());
        int id = rand.nextInt(1000);
        return id;
    }
    public static byte [] serializeFile(File file) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(file);
        byte [] contentSerialized=new byte[(int)file.length()];
        fileInputStream.read(contentSerialized,0,(int)file.length());

        return contentSerialized;
    }

    public static void writeOutImage(byte [] serializedFile) throws IOException {
        OutputStream out = new FileOutputStream( "output.png");
        out.write(serializedFile);
        out.close();
//
//        ByteArrayInputStream inStreambj = new ByteArrayInputStream(serializedFile);
//
//        // read image from byte array
//        BufferedImage newImage = ImageIO.read(inStreambj);
//
//        // write output image
//        ImageIO.write(newImage, "jpg", new File("outputImage.jpg"));

    }

    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2022);

            TaskSchedulerInterface taskScheduler = (TaskSchedulerInterface) registry.lookup("taskScheduler");
            int uniqueID1 = 1;
            int uniqueID2 = 2;
            int uniqueID3=3;
            //
            File file = new File("./Client/image.png");

            if(file.exists())
                System.out.println("file is null");
            else
                System.out.println("file isn't null");

            byte [] serializedImage=serializeFile(file);
            int[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
            int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};

            // Submit a tasks to the server

            //TODO: uncomment
            int taskId1 = taskScheduler.submitTask(new ComputationTask(uniqueID1, 10000));
            System.out.println(" Submitted task with ID " + taskId1);

            int taskId2 = taskScheduler.submitTask(new MatrixMultiplier(uniqueID2, matrix1, matrix2));
            System.out.println(" Submitted task with ID " + taskId2);


            int taskId3 = taskScheduler.submitTask(new FilterTask(uniqueID3,serializedImage));
            System.out.println(" Submitted task with ID " + taskId3);
            // Wait for the task to complete

//TODO: uncomment
            TaskResult result1 = taskScheduler.getResult(taskId1);
            TaskResult result2 = taskScheduler.getResult(taskId2);
            TaskResult result3 = taskScheduler.getResult(taskId3);

            //TODO: uncomment
            long ComputationTaskDuration = (long) result1.getResult();
            int[][] matrixProduct = (int[][]) result2.getResult();
            byte [] filteredImageSerialized=(byte [])result3.getResult();

//TODO: uncomment
            System.out.println("Result of task with id:" + result1.getTaskId());
            System.out.println(ComputationTaskDuration);


            System.out.println("Result of task with id:" + result2.getTaskId());
            for (int i = 0; i < matrixProduct.length; i++) {
                for (int j = 0; j < matrixProduct[0].length; j++) {
                    System.out.print(matrixProduct[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Result of task with id:" + result3.getTaskId());

            writeOutImage(filteredImageSerialized);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
