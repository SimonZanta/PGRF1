package model;

public class Line {

    Point first, second;

    public Line(Point point1, Point point2){
        first =  new Point(point1.x, point1.y);
        second =  new Point(point2.x, point2.y);
    }

    public Line(int x1, int y1, int x2, int y2){
        first =  new Point(x1, y1);
        second =  new Point(x2, y2);
    }

    public Point getFirst() {
        return first;
    }
    public Point getSecond() {
        return second;
    }


    public Point returnAngledPoint() {
        int x1 = this.getFirst().x;
        int x2 = this.getSecond().x;
        int y1 = this.getFirst().y;
        int y2 = this.getSecond().y;

        int dx = x2 - x1;
        int dy = y2 - y1;


        double angle = Math.toDegrees(Math.atan2(dy, dx));

        if(angle < 0){
            angle += 360;
        }

        int angleDegRounded = (int) Math.round((angle / 45)) * 45;

        int length = Math.max(y1, y2) - Math.min(y1, y2);

        double c = (length * (Math.sqrt(2)/2));
        double b = (c / (Math.sqrt(2)/2));
        int x, y;
        switch (angleDegRounded){
            case 90:
                return new Point(x1, y2);
            case 270:
                return new Point(x1, y2);
            case 0:
                return new Point(x2, y1);
            case 180:
                return new Point(x2, y1);
            case 315:
                x = (int) (x1 + b);
                y = y1 - length;

                return new Point(x, y);
            case 225:
                x = (int) (x1 - b);
                y = y1 - length;

                return new Point(x, y);
            case 135:
                x = (int) (x1 - b);
                y = y1 + length;

                return new Point(x, y);
            case 45:
                x = (int) (x1 + b);
                y = y1 + length;

                return new Point(x, y);
        }

        return new Point(x2, y2);
    }
}
