package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TaskSchedulerImp implements TaskSchedulerInterface{
    private final TaskSchedulerServer taskSchedulerServer;
    public static final List<Task> tasks=new ArrayList<Task>();
    public static final List<TaskResult> taskResults= new ArrayList<>();

    //public static volatile boolean newTaskResultAdded =false;
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
        //TODO: wait for taskResult to be ready
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(TaskResult t:taskResults){
            if(t.getTaskId()==taskId){
                taskResult=t;
                break;
            }
        }

        System.out.println("*********");
        return taskResult;

    }

}
