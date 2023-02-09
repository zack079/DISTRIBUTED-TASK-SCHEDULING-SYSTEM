package Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.nio.ByteBuffer;

public class test {


    public static float[] byteToFloat(byte[] input) {
        float[] ret = new float[input.length/4];
        for (int x = 0; x < input.length; x+=4) {
            ret[x/4] = ByteBuffer.wrap(input, x, 4).getFloat();
        }
        return ret;
    }

    public static byte[] floatToByte(float[] input) {
        byte[] ret = new byte[input.length*4];
        for (int x = 0; x < input.length; x++) {
            ByteBuffer.wrap(ret, x*4, 4).putFloat(input[x]);
        }
        return ret;
    }
    public static void showArr(float [] arr){
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }

    }

    public static void main(String[] args) throws IOException {
            float [] floatArr={0f,1f,2f,3f};
            byte [] byteArr=floatToByte(floatArr);
            floatArr=byteToFloat(byteArr);
            showArr(floatArr);



    }
}
