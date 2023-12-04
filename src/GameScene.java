import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.awt.*;


public class GameScene extends Scene {
    protected Camera Cam;
    private StaticThing left;
    private StaticThing right;
    protected Group fond;
    protected Hero hero;
    protected Foe foe;
    protected static StaticThing life;
    protected static Hero ball;
    protected double numberOfLives;
    protected static int offset;
    protected int rogner=10;

    public GameScene(Group root){
        super(root);
        fond = root;
        Cam = new Camera(0,50,600,350);
        left = new StaticThing(0,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        right = new StaticThing(800,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        hero = new Hero("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\heros.png",100,200,0);
        life = new StaticThing(10,10,33,77,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\life.png",3);
        foe = new Foe("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\foe.png",500,165,1);
        //ball = new Hero("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\heros.png",100,200,0);
        root.getChildren().add(left.getImageView()); // add the object to the root
        root.getChildren().add(right.getImageView());
        root.getChildren().add(hero.getSprite_hero());
        root.getChildren().add(life.getImageView());
        root.getChildren().add(foe.getSprite_foe());
        root.getChildren().get(4).setScaleX(0.45); //redimensionne l'image
        root.getChildren().get(4).setScaleY(0.45);
        numberOfLives = 3;
        offset = 11;
    }

    //Method for scrolling the background
    public void render(Camera Cam){
        ((ImageView) fond.getChildren().get(0)).setViewport(new Rectangle2D(Cam.getX(),Cam.getY(),Cam.getWidthX(),Cam.getWidthY())); //Position the left image to the left
        ((ImageView) fond.getChildren().get(1)).setX(right.sizeX - Cam.getX()); //Position the right image to the right
        ((ImageView) fond.getChildren().get(1)).setVisible(false);

        ((ImageView) fond.getChildren().get(3)).setViewport(new Rectangle2D(0,66-(2*numberOfLives*offset),33,10));//Show life
        ((ImageView) fond.getChildren().get(3)).setVisible(true);

        //Make the right image appear when the camera reaches the end of the left image
        if (Cam.getX()+ Cam.getWidthX() > left.sizeX ){
            ((ImageView) fond.getChildren().get(1)).setViewport(new Rectangle2D(0,Cam.getY(),Cam.getWidthX()-(right.sizeX- Cam.getX()),Cam.getWidthY()));
            ((ImageView) fond.getChildren().get(1)).setVisible(true);
        }
        //Make the left image disappear when it is no longer in the camera's field of view
        if (Cam.getX()>= left.sizeX){
            Cam.setX(Cam.getX()-left.sizeX);
        }
    }

    public void update(long time){

        if (AnimatedThing.countFrame == AnimatedThing.maxFrame){
            Cam.setX(Cam.getX()+15);
            this.render(Cam);
            this.setOnKeyPressed( (event)->{

                //Jump by pressing the Space bar
                if (event.getCode() == KeyCode.SPACE) {
                    Hero.jump();
                    //Cam.jump();
                }

                //Shoot by pressing the Enter key
                if (event.getCode() == KeyCode.ENTER) {
                    Hero.shoot();
                }
            });
            AnimatedThing.countFrame = 0;
        }
        else {
            AnimatedThing.countFrame+=1;
        }
    }

    boolean collision_test_ancien=false;
    public void checkCollision(Hero hero, Foe foe) {

        Rectangle rect1 = new Rectangle((int) hero.getXHero()+rogner, (int) hero.getYHero()+rogner, 85-rogner, 100-rogner);
        Rectangle rect2 = new Rectangle((int) foe.getXFoe()+rogner+42, (int) 220+rogner, (int) 67.5-rogner, 90-rogner);
        //System.out.println("X hero "+hero.getXHero()+", Y hero "+hero.getYHero());
        //System.out.println("X foe "+foe.getXFoe()+rogner+42+", Y foe "+foe.getYFoe());

        if (rect1.intersects(rect2)) {
            System.out.println("Collision détectée !");
            if (collision_test_ancien==false){
                numberOfLives-=0.5;
                collision_test_ancien = true;
            }

        }
        else{
            collision_test_ancien = false;
        }
    }
}