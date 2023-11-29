import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Runner");
        Group root = new Group();
        //Pane pane = new Pane(root);
        GameScene theScene = new GameScene(root);//, 600, 400,true);
        primaryStage.setScene(theScene);

        // Définir les dimensions minimales, préférées et maximales de la fenêtre
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.setWidth(600); // largeur préférée
        primaryStage.setHeight(350); // hauteur préférée
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(600);


        primaryStage.show();

        theScene.render(theScene.Cam);

        primaryStage.setScene(theScene);

        primaryStage.show();
        //System.out.println((scene.Cam.toString()));
        theScene.render(theScene.Cam);

        //scene.hero.getSprite().setViewport(new Rectangle2D(75,0,85,100));
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long time) {
                theScene.hero.update(time);
                //camera.update(time);
                theScene.update(time);
            }
        };
        timer.start();


    }

    public static void main(String[] args) {
        launch();
    }
}