package Client;

import Server.TaskResult;
import Server.TaskSchedulerInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void writeOutImage(byte [] serializedFile,String name) throws IOException {
        OutputStream out = new FileOutputStream( name+".png");
        out.write(serializedFile);
        out.close();

    }


    public static float[] toFloatArray(byte[] bytes) {
        float[] floats = new float[bytes.length/4];
        ByteBuffer.wrap(bytes).asFloatBuffer().get(floats).array();
        return floats;
    }

    public static byte[] floatArrayToByteArray(float[] floatArray) {
        int len = floatArray.length;
        byte[] byteArray = new byte[len * 4];
        for (int i = 0; i < len; i++) {
            int floatBits = Float.floatToIntBits(floatArray[i]);
            byte[] bytes = ByteBuffer.allocate(4).putInt(floatBits).array();
            System.arraycopy(bytes, 0, byteArray, i * 4, 4);
        }
        return byteArray;
    }


    public static byte[] floatToByte(float[] input) {
        byte[] ret = new byte[input.length*4];
        for (int x = 0; x < input.length; x++) {
            ByteBuffer.wrap(ret, x*4, 4).putFloat(input[x]);
        }
        return ret;
    }
    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2022);

            TaskSchedulerInterface taskScheduler = (TaskSchedulerInterface) registry.lookup("taskScheduler");
            int uniqueID1 = 1;
            int uniqueID2 = 2;
            int uniqueID3=3;
            int uniqueID4=4;
            //
            File file = new File("./Client/image.png");


            byte [] serializedImage=serializeFile(file);
            int[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
            int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};
           // float [] kernelArray = {0f,0f,0f,0f,5f,0f,0f,0f,0f};
           // byte [] kernelInBytes = floatToByte(kernelArray);
            int [] kernel={0,0,0,0,5,0,0,0,0};
           // Kernel kernel=new Kernel(kernelArray);

            // Submit a tasks to the server

            int taskId1 = taskScheduler.submitTask(new ComputationTask(uniqueID1, 10000));
            System.out.println(" Submitted task with ID " + taskId1);

            int taskId2 = taskScheduler.submitTask(new MatrixMultiplier(uniqueID2, matrix1, matrix2));
            System.out.println(" Submitted task with ID " + taskId2);


            int taskId3 = taskScheduler.submitTask(new FilterTask(uniqueID3,serializedImage));
            System.out.println(" Submitted task with ID " + taskId3);
            // Wait for the task to complete

            int taskId4 = taskScheduler.submitTask(new ConvolutionTask(uniqueID4, serializedImage,kernel));
            System.out.println(" Submitted task with ID " + taskId4);

            TaskResult result1 = taskScheduler.getResult(taskId1);
            TaskResult result2 = taskScheduler.getResult(taskId2);
            TaskResult result3 = taskScheduler.getResult(taskId3);
            TaskResult result4 = taskScheduler.getResult(taskId4);

            long ComputationTaskDuration = (long) result1.getResult();
            int[][] matrixProduct = (int[][]) result2.getResult();
            byte [] filteredImageSerialized=(byte [])result3.getResult();
            byte [] ConvolutionImageSerialized=(byte [])result4.getResult();

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

            writeOutImage(filteredImageSerialized,"filter");

            writeOutImage(ConvolutionImageSerialized,"convolution");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
