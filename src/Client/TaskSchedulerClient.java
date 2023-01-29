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

            // Submit a task to the server
            int taskId = taskScheduler.submitTask( new MatrixProduct (uniqueID , 5,8));
            System . out . println (" Submitted task with ID " + taskId );
            // Wait for the task to complete
            //Thread.sleep(2000);
            TaskResult result = taskScheduler . getResult ( taskId ) ;

            System . out . println (" Server.Task result : " + result . getResult () );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
