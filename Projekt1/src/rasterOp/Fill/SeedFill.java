package rasterOp.Fill;

import Utils.ColorUtil;
import model.Point;
import rasterData.RasterBI;
import javax.swing.*;
import java.util.Optional;

public class SeedFill {

    //TODO
    // 1. implement convertor for colors
    public void fill(RasterBI img, JPanel panel, Point point, int colorFill){

        int x = point.x;
        int y = point.y;
        Optional<Integer> color = img.getColor(x, y);

        if(color.isPresent()) {
            String colorString = Integer.toHexString(color.get());
            if(colorString.equals("ff000000") && !colorString.equals("ff00ff00") && !colorString.equals("ffff0000")){
                point.Draw(img, colorFill);
                panel.repaint();

                fill(img, panel, new Point(x+1, y), colorFill);
                fill(img, panel, new Point(x-1, y), colorFill);
                fill(img, panel, new Point(x, y+1), colorFill);
                fill(img, panel, new Point(x, y-1), colorFill);

            }
        }
    }
}

