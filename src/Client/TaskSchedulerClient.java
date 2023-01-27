package Client;

import Client.ComputationTask;
import Server.TaskResult;
import Server.TaskSchedulerInterface;

import java.rmi.Naming;

public class TaskSchedulerClient {
    public static void main(String[] args) {
        try {
            // Look up the remote task scheduler
            TaskSchedulerInterface taskScheduler = (TaskSchedulerInterface) Naming.lookup ("// server_hostname / task_scheduler ") ;
            // Submit a task to the server
            int taskId = taskScheduler.submitTask(new ComputationTask(1 , 10000) );
            System . out . println (" Submitted task with ID " + taskId );
            // Wait for the task to complete
            TaskResult result = taskScheduler . getResult ( taskId ) ;
            System . out . println (" Server.Task result : " + result . getResult () );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
