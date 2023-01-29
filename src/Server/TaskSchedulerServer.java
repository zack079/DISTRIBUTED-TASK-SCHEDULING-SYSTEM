package Server;



import java.util.concurrent.*;

public class TaskSchedulerServer {
    private static final int NUM_WORKER_THREADS = 8;
    private final ExecutorService threadPool; //
    private final TaskQueue taskQueue; // task queue

    public TaskSchedulerServer() {
        //add number of threads in the thread pool
        threadPool = Executors.newFixedThreadPool(NUM_WORKER_THREADS);
        //start the task queue
        taskQueue = new TaskQueue();
    }

    public void start() throws InterruptedException {
        // Start worker threads to process tasks from the queue
        for (int i = 0; i < NUM_WORKER_THREADS; i++) {
            // add threads to the thread pool
            threadPool.submit(new WorkerThread(taskQueue));//worker threads
        }
        /*
        // Wait for tasks to be submitted and processed
        while (true) {
            Task task = taskQueue.take();//wait for an element to become available then take it
            System.out.println("(TaskSchedulerServer:29)size of taskQueue: "+taskQueue.getQueue().size());
            threadPool.submit(task); //add it to the threadPool,
            System.out.println("task has been submited to threadpool!");
        }*/
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
