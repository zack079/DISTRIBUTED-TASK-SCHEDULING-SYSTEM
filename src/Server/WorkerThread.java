package Server;

import Server.TaskQueue;

public class WorkerThread implements Runnable {
    private final TaskQueue taskQueue;

    public WorkerThread(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueue.take();
                System.out.println("thread number "+Thread.currentThread().getId() +" has taken a task!");
                TaskResult taskResult=task.execute();
                System.out.println("thread number "+Thread.currentThread().getId() +" has finished his task!");
                TaskSchedulerImp.taskResults.add(taskResult);
                System.out.println("(WorkerThread)task Result id is:"+taskResult.getTaskId());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}