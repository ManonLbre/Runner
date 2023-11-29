public class Camera {

    protected double x,widthX;

    protected double y,widthY;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidthX() {
        return widthX;
    }

    public double getWidthY() {
        return widthY;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Camera(double x, double y, double widthX, double widthY){
        this.x = x;
        this.y = y;
        this.widthX = widthX;
        this.widthY = widthY;
    }

    @Override
    public String toString() {
        return "x : "+x+" y : "+y;
    }

    public void jump() {
        for (int i=0;i<10;i++){
            this.y=y+i;
        }
    }
}