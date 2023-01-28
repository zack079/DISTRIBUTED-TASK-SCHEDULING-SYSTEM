package Client;

import Server.Task;
import Server.TaskResult;
import Server.TaskSchedulerImp;

import java.util.Random;

public class ComputationTask implements Task {
    private final int id;
    private final long duration;

    public ComputationTask(int id, long duration) {
        this.id = id;
        this.duration = duration;
    }

    @Override
    public TaskResult execute() { // Perform some computation

        Random rand = new Random();
        long result = 0;
        for (int i = 0; i < duration; i++) {
            result += rand.nextInt();
        }
        System.out.println("Server.Task " + id + " completed with result " + result);
        TaskResult taskResult = new TaskResult(id,result);
        return taskResult;

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void run() {

    }
}
