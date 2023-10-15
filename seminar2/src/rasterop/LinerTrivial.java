package rasterop;

import rasterData.Raster;
import rasterData.RasterBI;

public class LinerTrivial implements liner{

    @Override
    public void drawline(Raster img, double x1, double x2, double y1, double y2, int color) {

        if(x2 < x1){
            double tmp = x1;
            x1 = x2;
            x2 = tmp;

            double tmpY = y1;
            y1 = y2;
            y2 = tmpY;

            drawline(img, x2, x1, y2, y1, color);
        }else{
            double k = (y2-y1) / (x2-x1);
            double q = y1 - k * x1;

            double x = x1;
            double y;
            while(x < x2){
                y = k * x + q;

                img.setColor((int) x, (int) y, color);

                x++;
            }
        }
    }
}
