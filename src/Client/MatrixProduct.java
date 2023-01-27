package Client;

import Server.Task;

public class MatrixProduct implements Task {
    private int x,y;

    public MatrixProduct(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        System.out.println(x+"*"+y+"="+x*y);
    }


    @Override
    public void run() {

    }
}
