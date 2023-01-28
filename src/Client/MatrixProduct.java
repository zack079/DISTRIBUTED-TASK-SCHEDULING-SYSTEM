package Client;

import Server.Task;
import Server.TaskResult;
import Server.TaskSchedulerImp;

public class MatrixProduct implements Task {
    private int id;
    private int x,y;

    public MatrixProduct(int id,int x, int y) {
        this.id=id;
        this.x = x;
        this.y = y;
    }

    @Override
    public TaskResult execute() {
        System.out.println(x+"*"+y+"="+x*y);
        TaskResult taskResult = new TaskResult(id,x*y);
////        TaskSchedulerImp.taskResults.add(taskResult);
        return taskResult;
    }


    @Override
    public void run() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
