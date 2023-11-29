import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class GameScene extends Scene {
    protected Camera Cam;
    private StaticThing left;
    private StaticThing right;
    protected Group fond;
    protected Hero hero;


        public GameScene(Group root){
        super(root);
        fond = root;
        Cam = new Camera(0,50,600,350);
        left = new StaticThing(0,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        right = new StaticThing(800,0,800,400,"D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\desert.png",0);
        hero = new Hero("D:\\Manon\\Documents\\Ecole\\ENSEA\\2A\\S7\\Mineure Info\\Java\\Projet\\Runner\\img\\heros.png",100,200,0);
        root.getChildren().add(left.getImageView()); // ajout de l'objet au root
        root.getChildren().add(right.getImageView());
        root.getChildren().add(hero.getSprite());
        }
    //méthode pour faire défiler le fond
    public void render(Camera Cam){
        ((ImageView) fond.getChildren().get(0)).setViewport(new Rectangle2D(Cam.getX(),Cam.getY(),Cam.getWidthX(),Cam.getWidthY())); //place l'image left à gauche
        ((ImageView) fond.getChildren().get(1)).setX(right.sizeX - Cam.getX()); //place l'image right à droite
        ((ImageView) fond.getChildren().get(1)).setVisible(false);

        //((ImageView) fond.getChildren().get(3)).setViewport(new Rectangle2D(0,0,33,10));// image de la vie
        //((ImageView) fond.getChildren().get(3)).setVisible(true);

        //faire apparaitre l'image de droite lorsque la caméra arrive en bout d'image
        if (Cam.getX()+ Cam.getWidthX() > left.sizeX ){
            ((ImageView) fond.getChildren().get(1)).setViewport(new Rectangle2D(0,Cam.getY(),Cam.getWidthX()-(right.sizeX- Cam.getX()),Cam.getWidthY()));
            ((ImageView) fond.getChildren().get(1)).setVisible(true);
        }
        //faire disparaitre l'image de gauche lorsqu'elle n'est plus dans le champ de la caméra
        if (Cam.getX()>= left.sizeX){
            Cam.setX(Cam.getX()-left.sizeX);
        }
    }
    public void update(long time){
        if (AnimatedThing.countFrame == AnimatedThing.maxFrame){
            Cam.setX(Cam.getX()+15);
            this.render(Cam);
            this.setOnMouseClicked( (event)->{
                Hero.jump();
                //Cam.jump();
            });
            AnimatedThing.countFrame = 0;
        }
        else {
            AnimatedThing.countFrame+=1;
        }

    }

}