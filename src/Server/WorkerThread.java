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
                task.execute();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}