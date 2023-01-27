package Server;

public class TaskResult {
    private int taskId;
    private final Object result;

    public TaskResult(int taskld, Object result) {
        this.taskId = taskId;
        this.result = result;
    }

    public int getTaskId() {
        return taskId;
    }

    public Object getResult() {
        return result;

    }
}
