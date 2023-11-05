package Utils;

import java.util.Random;

public class ColorUtil {
    public int getRandomColor(){
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        int rgb = (red << 16) | (green << 8) | blue;

        return rgb;
    }
}
