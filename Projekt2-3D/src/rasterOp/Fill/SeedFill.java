package rasterOp.Fill;

import Utils.ColorUtil;
import model.Point;
import rasterData.RasterBI;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class SeedFill {
    public void fill(RasterBI img, JPanel panel, Point point, int colorFill, boolean patternFill){

        int x = point.x;
        int y = point.y;
        Optional<Integer> color = img.getColor(x, y);

        if(color.isPresent()) {
            String colorString = Integer.toHexString(color.get());
            if(putNextPointToQueue(colorString, img, point)){
                if(patternFill){
                    point.Draw(img, getPatterFillColor(new Point(x, y)));
                }else{
                    point.Draw(img, colorFill);
                }

                panel.repaint();

                fill(img, panel, new Point(x+1, y), colorFill, patternFill);
                fill(img, panel, new Point(x-1, y), colorFill, patternFill);
                fill(img, panel, new Point(x, y+1), colorFill, patternFill);
                fill(img, panel, new Point(x, y-1), colorFill, patternFill);

            }
        }
    }

    private int getPatterFillColor(Point point){
        int green = 0x00ff00;
        int blue = 0x0000ff;

        int[][] pattern =   {
                            {green,green,green,green,green},
                            {green,green,blue,green,green},
                            {green,blue,blue,blue,green},
                            {green,blue,green,blue,green},
                            {green,green,green,green,green},
        };

        int j = point.x % 5;
        int i = point.y % 5;

        return pattern[i][j];
    }

    public void fillStack(RasterBI img, JPanel panel, Point point, int colorFill, boolean patternFill){
        ArrayList<Point> queue = new ArrayList<>();
        queue.add(point);

        while(!queue.isEmpty()){
            Point p = queue.get(0);
            queue.remove(0);

            Optional<Integer> color = img.getColor(p.x, p.y);

            if(color.isPresent()) {
                String colorString = Integer.toHexString(color.get());
                if (putNextPointToQueue(colorString, img, p)) {
                    if(patternFill){
                        img.setColor(p.x, p.y, getPatterFillColor(p));
                    }else{
                        img.setColor(p.x, p.y, colorFill);
                    }

                    panel.repaint();

                    queue.add(new Point(p.x, p.y - 1));
                    queue.add(new Point(p.x, p.y + 1));
                    queue.add(new Point(p.x - 1, p.y));
                    queue.add(new Point(p.x + 1, p.y));
                }
            }
        }
    }

    private boolean putNextPointToQueue(String colorString, RasterBI img, Point p){
        if(!colorString.equals("ff000000")) return false;
        if(colorString.equals("ff00ff00") || colorString.equals("ffff0000")) return false;

        if(p.x < 0 || p.y < 0) return false;
        if(p.x > img.getWidth() || p.y > img.getHeight()) return false;

        return true;
    }
}

