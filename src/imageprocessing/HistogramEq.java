/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.round;

/**
 * @author Mohamed Oun
 */
public class ImageProcessing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //import image
        File file = new File("C:\\Users\\Mohamed\\Desktop\\image.jpg");
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int imsize = width * height;
        int[][] imgArr = new int[height][width];
        int[][] newImgArr = new int[height][width];
        Raster raster = img.getData();
        //frequency of value
        int[] freq = new int[256];
        //cumulative distribution
        float[] cdf = new float[256];
        //new values
        int[] output = new int[256];
        //value we're operating on, sum of previous frequencies, number of bits in the image
        int value, sum = 0, bits = 255;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imgArr[i][j] = raster.getSample(j, i, 0);
                value = imgArr[i][j];
                //increment the frequency of the value that's in this pixel
                freq[value]++;

            }
        }
        for (int i = 0; i < freq.length; i++) {
            sum += freq[i];
            //cdf of a pixel is the sum of its frequency plus all previous frequencies
            cdf[i] = sum;
            output[i] = round((cdf[i] / imsize) * bits);
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                value = imgArr[i][j];
                newImgArr[i][j] = output[value];
            }
        }
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster2 = bi.getRaster();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(newImgArr[i][j] + " ");
                raster2.setSample(j, i, 0, newImgArr[i][j]);

            }
            System.out.println();
        }

        ImageIO.write(bi, "jpg", new File("C:\\Users\\mohamed\\Desktop\\test.jpg"));
    }
}
