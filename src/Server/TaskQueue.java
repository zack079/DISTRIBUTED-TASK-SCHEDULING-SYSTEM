package Server;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    private final BlockingQueue<Task> queue;

    public TaskQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public void add(Task task) throws InterruptedException {
        queue.put(task);
    }

    public Task take() throws InterruptedException {
        return queue.take();
    }
}