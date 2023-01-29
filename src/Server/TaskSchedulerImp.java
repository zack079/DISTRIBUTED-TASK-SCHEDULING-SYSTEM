package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TaskSchedulerImp implements TaskSchedulerInterface{
    private final TaskSchedulerServer taskSchedulerServer;
    public static final List<Task> tasks=new ArrayList<Task>();
    public static final List<TaskResult> taskResults=new ArrayList<TaskResult>();

    public TaskSchedulerImp(TaskSchedulerServer taskSchedulerServer) {
        this.taskSchedulerServer = taskSchedulerServer;
    }

    @Override
    public int submitTask(Task task) throws RemoteException {

        tasks.add(task);
        try {
            taskSchedulerServer.getTaskQueue().add(task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return task.getId();
    }

    @Override
    public TaskResult getResult(int taskId) throws RemoteException {

        TaskResult taskResult=null;
        try {

            //TODO: should wait for the task to complete, or return task not finished
            System.out.println("searching for result...");
            for(TaskResult t:taskResults){
                if(t.getTaskId()==taskId){
                    System.out.println("taskResult is finished!");
                    taskResult=t;
                    break;
                }
            }
//                while(true){
//                    for(TaskResult t:taskResults){
//                        if(t.getTaskId()==taskId){
//                            taskResult=t;
//                            break;
//                        }
//                    }
//                    if(taskResult!=null)
//                        break;
//                }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(taskResult==null)
            System.out.println("task hasn't finished yet!");
        return taskResult;
    }
}