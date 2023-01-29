package Server;

import Server.TaskQueue;

import java.util.concurrent.BlockingQueue;

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
                TaskResult taskResult=task.execute();
                System.out.println("thread number "+Thread.currentThread().getId() +" has finished his task with id: "+taskResult.getTaskId());
                TaskSchedulerImp.taskResults.add(taskResult);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}