import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AnimatedThing {
    private double x_hero, y_hero;
    private double x_foe, y_foe;

    //private static Image spriteSheet;
    protected static ImageView sprite_hero;
    protected static ImageView sprite_foe;
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
    protected static int indexShoot;
    protected static int maxIndexShoot;
    private double status;
    private Random rand = new Random();

    public AnimatedThing(String fileName, int x, int y, int state){
        countFrame = 0;
        maxFrame = 4;
        //duration = 41666666;
        offset = 85;
        status = state;
        maxIndex = 5;
        index = 0;
        altitude = 0;

        // Create an image from a file
        if (status == 0) {
            File fileImage = new File(fileName);
            Image spriteSheet_hero = new Image(fileImage.toURI().toString());
            sprite_hero = new ImageView(spriteSheet_hero);
            sprite_hero.setViewport(new Rectangle2D(0, 0, 85, 100));
            sprite_hero.setX(x);
            sprite_hero.setY(y);
            setXHero(x);
            setYHero(y);
        }
        else {
            File fileImage = new File(fileName);
            Image spriteSheet_foe = new Image(fileImage.toURI().toString());
            sprite_foe = new ImageView(spriteSheet_foe);
            sprite_foe.setViewport(new Rectangle2D(0, 0, 85, 100));
            sprite_foe.setX(x);
            sprite_foe.setY(y);
            setXFoe(x);
            setYFoe(y);
        }
    }

    public ImageView getSprite_hero(){
        return sprite_hero;
    }
    public ImageView getSprite_foe(){
        return sprite_foe;
    }

    public void update(long time) {
        //Animation of the runner
        if (countFrame == maxFrame) {
            //Jump
            if (jumpOk != 0) {
                if (jumpOk > 20) {
                    Hero.sprite_hero.setViewport(new Rectangle2D(0, 165, 85, 100));
                    sprite_hero.setY(200 - altitude * 5);
                    setYHero(200 - altitude * 5);
                    altitude = altitude + 1;
                }
                else {
                    Hero.sprite_hero.setViewport(new Rectangle2D(Hero.offset, 165, 85, 100));
                    sprite_hero.setY(200 - altitude * 5);
                    setYHero(200 - altitude * 5);
                    altitude = altitude - 1;
                }
                jumpOk = jumpOk - 1;
            }

            //Run and shoot
            else {
                if (shoot != 0) {
                    Hero.sprite_hero.setViewport(new Rectangle2D((Hero.index) * Hero.offset - 8, 328, 85, 100));
                    if (Hero.index == Hero.maxIndex) {
                        Hero.index = 0;
                    } else {
                        Hero.index += 1;
                    }
                    shoot = shoot - 1;
                }
                else {
                    Hero.sprite_hero.setViewport(new Rectangle2D((Hero.index) * Hero.offset, 0, 80, 100));
                    if (Hero.index == Hero.maxIndex) {
                        Hero.index = 0;
                    }
                    else {
                        Hero.index += 1;
                    }
                }
                countFrame = 0;
            }
        }
        else {
            countFrame += 1;
        }
        x_hero = getXHero();
        y_hero = getYHero();
    }

    int foe_count=0; //compteur d'apparition d'un ennemi
    int foe_countMax=10; // Fréquence d'apparation d'un ennemi
    boolean enemy= false; //Présence d'un ennemi
    int enemy_index=0; //pointe le rang de l'ennemi
    int speed=15;

    public void foeSummoning (long time){
        //Animation of the foe
        if (countFrame == maxFrame) {
            if(enemy) {
                double position = getXFoe();
                if (position>-150){
                    Foe.sprite_foe.setX(position - speed); //On modifie le x associé à l'image dans le background de gauche
                    setXFoe(position - speed);
                    Foe.sprite_foe.setViewport(new Rectangle2D( 150*enemy_index+65, 200, 150, 200)); // On affiche la nouvelle position du Hero à chaque appel de update
                }
                else{
                    enemy=false;
                    foe_count=0;
                }
            }
            else {
                if (foe_count < foe_countMax) {
                    foe_count += 1;
                }
                else {
                    enemy = true;
                    enemy_index = rand.nextInt(3);
                    setXFoe(550);

                }
            }
        }
        x_foe = getXFoe();
        y_foe = getYFoe();
    }

    //Setter
    public void setXHero(double x) {this.x_hero = x;}
    public void setYHero(double y) {this.y_hero = y;}
    public void setXFoe(double x) {this.x_foe = x;}
    public void setYFoe(double y) {this.y_foe = y;}
    //public void setRunIndex(int runIndex) {this.runIndex = runIndex;}
    public void setJumpIndex(int jumpIndex) {this.jumpIndex = jumpIndex;}

    //Getter
    public double getXHero() {return x_hero;}
    public double getYHero() {return y_hero;}
    public double getXFoe() {return x_foe;}
    public double getYFoe() {return y_foe;}
    //public int getRunIndex() {return runIndex;}
    public int getJumpIndex() {return jumpIndex;}
}