package model;
import rasterData.RasterBI;

public class Point {
    public int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void Draw(RasterBI img){
        img.setColor(this.x, this.y, 0xff00ff);
    }
}
