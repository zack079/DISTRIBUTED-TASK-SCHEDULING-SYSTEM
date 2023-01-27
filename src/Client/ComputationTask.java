package Client;

import Server.Task;

import java.util.Random;

public class ComputationTask implements Task {
    private final int id;
    private final long duration;

    public ComputationTask(int id, long duration) {
        this.id = id;
        this.duration = duration;
    }

    @Override
    public void execute() { // Perform some computation

        Random rand = new Random();
        long result = 0;
        for (int i = 0; i < duration; i++) {
            result += rand.nextInt();
        }
        System.out.println("Server.Task " + id + " completed with result " + result);
    }

    @Override
    public void run() {

    }
}
