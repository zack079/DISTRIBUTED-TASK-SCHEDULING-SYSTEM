package Client;

import Server.Task;
import Server.TaskResult;
import Server.TaskSchedulerInterface;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLOutput;
import java.util.*;

public class TaskSchedulerClient {

    public static int i=1;

    public static final int COMPUTATION=1;
    public static final int MATRIX_MULTIPLICATION=2;
    public static final int GREEN_FILTER=3;
    public static final int CONVOLUTION=4;

    public static final int TASK_RESULT=5;

    public static List<TaskModel> taskModels=new ArrayList<>();

    public static TaskSchedulerInterface taskScheduler;
    public static int generateId() {
        int id=i;
        i++;
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
        System.out.println("image has been saved!");

    }


    public static int submitTask(Task task) throws RemoteException {

        int taskId = taskScheduler.submitTask(task);
        System.out.println("The task with ID "+taskId+" has been successfully submitted! ");

        return taskId;
    }

    public static Task matrixMultiplication(){
        int taskId = generateId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows and columns of matrix A:");
        int rowsA = scanner.nextInt();
        int columnsA = scanner.nextInt();

        int[][] matrixA = new int[rowsA][columnsA];
        System.out.println("Enter the elements of matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < columnsA; j++) {
                matrixA[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the number of rows and columns of matrix B:");
        int rowsB = scanner.nextInt();
        int columnsB = scanner.nextInt();

        int[][] matrixB = new int[rowsB][columnsB];
        System.out.println("Enter the elements of matrix B:");
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < columnsB; j++) {
                matrixB[i][j] = scanner.nextInt();
            }
        }
        Task task = new MatrixMultiplier(taskId, matrixA, matrixB);

        return task;
    }


    public static Task computationTask(){
        int taskId = generateId();
        System.out.println("Enter the duration:");
        Scanner scanner = new Scanner(System.in);
        int duration = scanner.nextInt();
        Task task = new ComputationTask(taskId,duration);
        return task;
    }

    public static Task convolution() throws IOException {
        int taskId = generateId();

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the image path: ");
        String path = input.nextLine();
        File file = new File(path);
        if(!file.exists()){
            System.out.println("File doesn't exist!");
            return null;

        }
        byte [] serializedImage=serializeFile(file);
        System.out.println("Enter the kernel");

        int[] kernel = new int[9];
        for (int i = 0; i < 9; i++) {
            System.out.println("kernel["+i+"]=");
            kernel[i] = input.nextInt();
        }
        Task task = new ConvolutionTask(taskId, serializedImage,kernel);

        return task;

    }

    public static Task greenFilter() throws IOException {
        int taskId = generateId();

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the image path: ");
        String path = input.nextLine();
        File file = new File(path);
        if(!file.exists()){
            System.out.println("File doesn't exist!");
            return null;

        }
        byte [] serializedImage=serializeFile(file);

        Task task = new FilterTask(taskId, serializedImage);

        return task;

    }

    public static void showResult(TaskResult result,int taskType) throws IOException {


        if(taskType==CONVOLUTION){
            byte [] ConvolutionImageSerialized=(byte [])result.getResult();
            writeOutImage(ConvolutionImageSerialized,"convolution");
        }else if(taskType==COMPUTATION){
            long ComputationTaskDuration = (long) result.getResult();
            System.out.println("Result of task with id:" + result.getTaskId());
            System.out.println(ComputationTaskDuration);

        }else if(taskType==GREEN_FILTER){
            byte [] filteredImageSerialized=(byte [])result.getResult();
            //System.out.println("Result of task with id:" + result3.getTaskId());

            writeOutImage(filteredImageSerialized,"filter");

        }else if(taskType==MATRIX_MULTIPLICATION){
            int[][] matrixProduct = (int[][]) result.getResult();
            System.out.println("Result of task with id:" + result.getTaskId());
            for (int i = 0; i < matrixProduct.length; i++) {
                for (int j = 0; j < matrixProduct[0].length; j++) {
                    System.out.print(matrixProduct[i][j] + " ");
                }
                System.out.println();
            }
        }


    }

    public static void getResult() throws IOException {
        System.out.println("Choose a task ID:");
        for(TaskModel t : taskModels){
            System.out.println(t.getTask().getId()+" - ");//Todo : diplay task type
        }
        Scanner input = new Scanner(System.in);
        int taskId = input.nextInt();

        TaskModel taskModel=null;

        for(TaskModel t : taskModels){
            if(t.getTask().getId()==taskId){
                taskModel=t;
                break;
            }

        }

        if(taskModel==null){
            System.out.println("invalid task id!");
            return;
        }

        taskModels.remove(taskModel);
        System.out.println("getting task from task scheduler");
        TaskResult result = taskScheduler.getResult(taskId);
        int taskType=taskModel.getTaskType();
        System.out.println("showing result");
        showResult(result,taskType);

    }

    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2022);

            taskScheduler = (TaskSchedulerInterface) registry.lookup("taskScheduler");
            int choice;
            Task task;
            TaskModel taskModel;

            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("Choose your task:");
                System.out.println("1- Computation task.");
                System.out.println("2- Matrix multiplication.");
                System.out.println("3- Image green filter");
                System.out.println("4- Image convolution");
                System.out.println("5- Get task result");
                choice = scanner.nextInt();

                if (choice==COMPUTATION){
                    task=computationTask();
                    submitTask(task);
                    taskModel=new TaskModel(task,COMPUTATION);
                    taskModels.add(taskModel);
                }

                else if(choice ==MATRIX_MULTIPLICATION){
                    task=matrixMultiplication();
                    submitTask(task);
                    taskModel=new TaskModel(task,MATRIX_MULTIPLICATION);
                    taskModels.add(taskModel);

                }

                else if(choice ==CONVOLUTION){
                    task=convolution();
                    submitTask(task);
                    taskModel=new TaskModel(task,CONVOLUTION);
                    taskModels.add(taskModel);

                }

                else if(choice ==GREEN_FILTER){
                    task=greenFilter();
                    submitTask(task);
                    taskModel=new TaskModel(task,GREEN_FILTER);
                    taskModels.add(taskModel);

                }
                else if(choice == TASK_RESULT)
                    getResult();
                else
                    System.out.println("Please enter a valid choice!");
                /*
                if(task!=null){
                    taskId=submitTask(task);
                }*/

            }

//            int uniqueID1 = generateId();
//            int uniqueID2 = generateId();
//            int uniqueID3=generateId();
//            int uniqueID4=generateId();
            //
           // File file = new File("./Client/image.png");


           // byte [] serializedImage=serializeFile(file);
            //int[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
            //int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};

            //int [] kernel={0,0,0,0,5,0,0,0,0};

            // Submit a tasks to the server

         //   int taskId1 = taskScheduler.submitTask(new ComputationTask(uniqueID1, 10000));
       //     System.out.println(" Submitted task with ID " + taskId1);

//            int taskId2 = taskScheduler.submitTask(new MatrixMultiplier(uniqueID2, matrix1, matrix2));
//            System.out.println(" Submitted task with ID " + taskId2);


          //  int taskId3 = taskScheduler.submitTask(new FilterTask(uniqueID3,serializedImage));
       //     System.out.println(" Submitted task with ID " + taskId3);
            // Wait for the task to complete

          //  int taskId4 = taskScheduler.submitTask(new ConvolutionTask(uniqueID4, serializedImage,kernel));
          //  System.out.println(" Submitted task with ID " + taskId4);

//            TaskResult result1 = taskScheduler.getResult(taskId1);
//            TaskResult result2 = taskScheduler.getResult(taskId2);
//            TaskResult result3 = taskScheduler.getResult(taskId3);
//            TaskResult result4 = taskScheduler.getResult(taskId4);
//
//           // long ComputationTaskDuration = (long) result1.getResult();
//            int[][] matrixProduct = (int[][]) result2.getResult();
//            byte [] filteredImageSerialized=(byte [])result3.getResult();
//            byte [] ConvolutionImageSerialized=(byte [])result4.getResult();

//            System.out.println("Result of task with id:" + result1.getTaskId());
//            System.out.println(ComputationTaskDuration);


//            System.out.println("Result of task with id:" + result2.getTaskId());
//            for (int i = 0; i < matrixProduct.length; i++) {
//                for (int j = 0; j < matrixProduct[0].length; j++) {
//                    System.out.print(matrixProduct[i][j] + " ");
//                }
//                System.out.println();
//            }

//            System.out.println("Result of task with id:" + result3.getTaskId());
//
//            writeOutImage(filteredImageSerialized,"filter");
//
//            writeOutImage(ConvolutionImageSerialized,"convolution");
//

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
