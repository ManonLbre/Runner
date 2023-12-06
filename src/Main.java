import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.StackPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Runner");
        Group root = new Group();
        //Pane pane = new Pane(root);
        //HomeScene home = new HomeScene(root);
        GameScene theScene = new GameScene(root);//, 600, 400,true);
        primaryStage.setScene(theScene);

        // Specify the minimum, preferred, and maximum dimensions of the window
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.setWidth(600); // favourite width
        primaryStage.setHeight(400); // favourite height
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(600);

        primaryStage.show();

        /*HomeScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                primaryStage.setScene(theScene);
            }
*/


        //theScene.render(theScene.Cam);

        //primaryStage.setScene(theScene);

        /*primaryStage.show();

        theScene.render(theScene.Cam);*/

            AnimationTimer timer = null;
            timer = new AnimationTimer() {
                public void handle(long time) {
                    if (theScene.getStart() == true) {
                        theScene.hero.update(time);
                        //camera.update(time);
                        theScene.update(time);
                        theScene.foe.foeSummoning(time);
                        theScene.checkCollision(theScene.hero, theScene.foe);
                        if (theScene.numberOfLives == 0) {
                            stop();
                            theScene.GameOver(root);
                        }
                    }
                    else {
                        theScene.start();
                    }
                }
            };
            timer.start();

    }

    public static void main(String[] args) {
        launch();
    }

}