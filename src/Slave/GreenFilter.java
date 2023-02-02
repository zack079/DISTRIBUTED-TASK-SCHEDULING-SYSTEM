package Slave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GreenFilter {
    public static byte[] serializedFile;
    public static void setGreenFilter() throws IOException {

        InputStream is = new ByteArrayInputStream(serializedFile);
        BufferedImage img = ImageIO.read(is);


        for(int i=0 ; i < img.getHeight() ; i++){
            for(int j=0 ; j < img.getWidth() ; j++){

                Color c = new Color(img.getRGB(j,i));

                int green = 255 - c.getGreen();

                Color newColor = new Color(0,green,0);
                img.setRGB(j,i,newColor.getRGB());
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        serializedFile = baos.toByteArray();
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverScoket =new ServerSocket(3030);
        System.out.println("slave 3 is ready ...");
        Socket socket=serverScoket.accept();
        InputStream inputStream=socket.getInputStream();
        DataInputStream dataInputStream=new DataInputStream(inputStream);
        int fileLength = dataInputStream.readInt();
        if(fileLength>0){
            serializedFile=new byte[fileLength];
            dataInputStream.readFully(serializedFile,0,fileLength);
        }else{
            System.out.println("file's empty!");
        }
        //output();
        setGreenFilter();
        //sending back the file

        OutputStream outputStream=socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(serializedFile.length);

        dataOutputStream.write(serializedFile);

        serverScoket.close();

    }
}
