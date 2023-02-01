package Client;

import Server.Task;
import Server.TaskResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilterTask implements Task {
    private final int id;
    private final byte [] serializedImage;

    public FilterTask(int id, byte[] serializedImage) {
        this.id = id;
        this.serializedImage = serializedImage;
    }


    @Override
    public TaskResult execute() {
        InputStream is = new ByteArrayInputStream(serializedImage);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TO DELETE
        if(img==null)
            System.out.println("img is null");

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        //write image

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //TODO: to delete

        try {
            ImageIO.write(img, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte [] filteredImageSerialized = baos.toByteArray();

        return new TaskResult(id,filteredImageSerialized);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void run() {

    }
}
