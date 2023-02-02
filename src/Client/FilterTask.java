package Client;

import Server.Task;
import Server.TaskResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class FilterTask implements Task {
    private final int id;
    private final byte [] serializedImage;

    public FilterTask(int id, byte[] serializedImage) {
        this.id = id;
        this.serializedImage = serializedImage;
    }


    @Override
    public TaskResult execute() {
        try {
            Socket socket = new Socket("localhost",3030);

            OutputStream outputStream=socket.getOutputStream();

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(serializedImage.length);
            dataOutputStream.write(serializedImage);


            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);
            int fileLength = dataInputStream.readInt();
            byte [] filteredImageSerialized=new byte[fileLength];
            dataInputStream.readFully(filteredImageSerialized,0,fileLength);

            return new TaskResult(id,filteredImageSerialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void run() {

    }
}
