import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public abstract class AnimatedThing {
    private double X,Y;

    private static Image spriteSheet;
    protected static ImageView sprite;
    protected static int altitude;
    protected static int index;
    //private long duration;
    protected static int maxIndex;
    //private int sizeWindow;
    protected static int offset;
    static int countFrame;
    static int maxFrame;
    private int jumpIndex=0;
    protected static int jumpOk = 0;
    protected static int shoot = 0;
    public AnimatedThing(String fileName, int x, int y, int state){
        countFrame =0;
        maxFrame = 4;
        //duration = 41666666;
        offset = 85;
        state = 0;
        maxIndex = 5;
        index = 0;

        File fichierImage = new File(fileName);

        // Créez une image à partir du fichier
        spriteSheet = new Image(fichierImage.toURI().toString());
        sprite = new ImageView(spriteSheet);
        sprite.setViewport(new Rectangle2D(0,0,85,100));
        sprite.setX(x);
        sprite.setY(y);

        altitude = 0;
    }

    public ImageView getSprite() {
        return sprite;
    }

    //Image du coureur en fonction de ce qu'il fait
    public void update(long time) {
        if (countFrame == maxFrame) {
            //Saut
            if (jumpOk != 0) {
                if (jumpOk > 15 ){
                    Hero.sprite.setViewport(new Rectangle2D(0, 165, 85, 100));
                    sprite.setY(200-altitude*5);
                    altitude= altitude +1;
                }
                else {
                    Hero.sprite.setViewport(new Rectangle2D(Hero.offset, 165, 85, 100));
                    sprite.setY(200-altitude*5);
                    altitude= altitude-1;
                }
                jumpOk=jumpOk - 1;

            }
            //Course
            else {
                if (shoot != 0){
                    Hero.sprite.setViewport(new Rectangle2D((Hero.index) * Hero.offset - 8, 328, 85, 100));
                    if (Hero.index == Hero.maxIndex) {
                        Hero.index = 0;
                    }
                    else {
                        Hero.index += 1;
                    }
                    shoot = shoot-1;
                }
                else {
                    Hero.sprite.setViewport(new Rectangle2D((Hero.index) * Hero.offset, 0, 80, 100));
                    if (Hero.index == Hero.maxIndex) {
                        Hero.index = 0;
                    } else {
                        Hero.index += 1;
                    }
                }
                countFrame = 0;
            }
        }

        else {
            countFrame += 1;
        }

    }
}