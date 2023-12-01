import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;
public class StaticThing {
    protected double x;
    protected double y;
    protected double sizeX;
    protected double sizeY;
    protected ImageView imageView;
    public StaticThing (double x, double y,double sizeX, double sizeY, String fileName, double value) {
        this.x = x;
        this.y = y;

        File fichierImage = new File(fileName);

        // Créez une image à partir du fichier
        Image image = new Image(fichierImage.toURI().toString());

        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}
