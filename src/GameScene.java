import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

import java.awt.*;


public class GameScene extends Scene {
    protected Camera Cam;
    private StaticThing left;
    private StaticThing right;
    protected Group fond;
    protected Hero hero;
    protected Foe foe;
    protected static StaticThing life;
    protected static StaticThing gameOver;
    //protected static Hero ball;
    protected double numberOfLives;
    protected static int offset;
    protected int cut=20;
    private StaticThing home;
    protected static double score=0;
    private Text scoreText;
    protected boolean start = false;

    public GameScene(Group root){
        super(root);
        fond = root;
        Cam = new Camera(0,50,600,350);
        left = new StaticThing(0,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        right = new StaticThing(800,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        hero = new Hero("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\heros.png",100,200,0);
        life = new StaticThing(20,10,33,77,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\life.png",3);
        foe = new Foe("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\foe.png",500,165,1);
        //ball = new Hero("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\heros.png",100,200,0);
        home = new StaticThing(-55,-75,600,350,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\home.png",0);

        scoreText = new Text("Score : "+score);
        scoreText.setX(450);
        scoreText.setY(20);

        root.getChildren().add(left.getImageView()); // Add the object to the root
        root.getChildren().add(right.getImageView());
        root.getChildren().add(hero.getSprite_hero());
        root.getChildren().add(life.getImageView());
        root.getChildren().get(3).setScaleX(2); // Resize the image
        root.getChildren().get(3).setScaleY(2);
        root.getChildren().add(foe.getSprite_foe());
        root.getChildren().get(4).setScaleX(0.45);
        root.getChildren().get(4).setScaleY(0.45);
        root.getChildren().add(home.getImageView());
        root.getChildren().get(5).setScaleX(0.85);
        root.getChildren().get(5).setScaleY(0.85);
        root.getChildren().add(scoreText);
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
            Cam.setX(Cam.getX()+15+score/50);
            this.render(Cam);
            this.setOnKeyPressed( (event)->{

                //Jump by pressing the up arrow key
                if (event.getCode() == KeyCode.UP) {
                    if (hero.getJumpOk() ==0) {
                        Hero.jump();
                        //Cam.jump();
                    }
                }
                //Shoot by pressing the right arrow key
                if (event.getCode() == KeyCode.RIGHT) {
                    Hero.shoot();
                }
            });
            AnimatedThing.countFrame = 0;
            score += 1;
            scoreText.setText("Score : " + String.format("%.0f", score));
        }
        else {
            AnimatedThing.countFrame+=1;
        }
    }

    boolean collision_test_ancien=false;
    public void checkCollision(Hero hero, Foe foe) {

        Rectangle rect1 = new Rectangle((int) hero.getXHero()+cut, (int) hero.getYHero()+cut, 85-cut, 100-cut);
        Rectangle rect2 = new Rectangle((int) foe.getXFoe()+cut+42, (int) 230+cut, (int) 67.5-cut, 90-cut);

        if (rect1.intersects(rect2)) {

            if (!collision_test_ancien){
                numberOfLives-=0.5;
                collision_test_ancien = true;
                heroBlink(); // Trigger the hero's blinking
                System.out.println("Collision detected !");
            }
        }
        else{
            collision_test_ancien = false;
        }
    }

    public void heroBlink() {
        AnimationTimer blinkTimer = new AnimationTimer() {
            private long startTime = 0;
            private long lastBlinkTime = 0;
            private boolean heroVisible = true;

            @Override
            public void start() {
                super.start();
                startTime = System.nanoTime();
            }

            @Override
            public void handle(long now) {
                if (now - startTime >= 1.5 * 1e9) { // Stop after 1.5 s
                    hero.getSprite_hero().setOpacity(1.0);
                    this.stop(); // Stop AnimationTimer
                } else if (now - lastBlinkTime >= 0.2 * 1e9) {
                    hero.getSprite_hero().setOpacity(heroVisible ? 0.5 : 1.0);
                    heroVisible = !heroVisible;
                    lastBlinkTime = now;
                }
            }
        };

        blinkTimer.start();
    }

    public void GameOver(Group root){
        gameOver = new StaticThing(50,-75,500,500,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\gameover.png",0);
        root.getChildren().add(gameOver.getImageView());
    }
    public boolean getStart() {
        return start;
    }
    public void start(){
        this.setOnKeyPressed( (event)->{

            //Jump by pressing the up arrow key
            if (event.getCode() == KeyCode.ENTER) {
                this.start=true;
                fond.getChildren().get(5).setVisible(false);
            }
        });
    }
    public static double getScore() {return score;}
}

