import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;

public abstract class AnimatedThing {
    private double x_hero, y_hero;
    private double x_foe, y_foe;
    protected static ImageView sprite_hero;
    protected static ImageView sprite_foe;
    protected static int altitude;
    protected static int index;
    protected static int maxIndex;
    protected static int offset;
    static int countFrame;
    static int maxFrame;
    protected static int jumpOk = 0;
    private double verticalVelocity = 0;
    private double gravity = 2;
    protected static int shoot = 0;
    private double status;
    private Random rand = new Random();

    public AnimatedThing(String fileName, int x, int y, int state){
        countFrame = 0;
        maxFrame = 4;
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
            if (jumpOk > 1) {

                if (jumpOk > 15) {
                    Hero.sprite_hero.setViewport(new Rectangle2D(0, 165, 85, 100));
                    verticalVelocity = -11; // Initial upward velocity
                } else {
                    Hero.sprite_hero.setViewport(new Rectangle2D(0, 165, 85, 100));
                    verticalVelocity += gravity; // Apply gravity to simulate the fall
                }

                // Update the vertical position based on the vertical velocity
                sprite_hero.setY(sprite_hero.getY() + verticalVelocity);
                setYHero(sprite_hero.getY());

                jumpOk = jumpOk - 1;

                if (sprite_hero.getY() > 200) { // 200 is the ground position
                    sprite_hero.setY(200);
                    setYHero(200);
                    verticalVelocity = 0;
                }
            }
            else if (jumpOk == 1){
                // Reset the hero's position to the ground when the jump is completed
                if (sprite_hero.getY() < 200) {
                    sprite_hero.setY(200);
                    setYHero(200);
                    verticalVelocity = 0;
                }
                jumpOk = 0;
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
    }

    int foe_count=0; //Enemy spawn counter
    int foe_countMax=10; // Enemy spawn frequency
    boolean enemy= false;
    int enemy_index=0; //Point out the rank of the enemy
    int speed=15;

    public void foeSummoning (long time){
        //Animation of the foe
        if (countFrame == maxFrame) {
            if(enemy) {
                double position = getXFoe();
                if (position>-105){
                    Foe.sprite_foe.setX(position - speed); //Adjusting the x-coordinate associated with the image in the left background
                    setXFoe(position - speed);
                    Foe.sprite_foe.setViewport(new Rectangle2D( 150*enemy_index+65, 200, 150, 200));
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
                    setXFoe(520+rand.nextInt(10)*10);
                }
            }
        }
    }

    //Setter
    public void setXHero(double x) {this.x_hero = x;}
    public void setYHero(double y) {this.y_hero = y;}
    public void setXFoe(double x) {this.x_foe = x;}
    public void setYFoe(double y) {this.y_foe = y;}

    //Getter
    public double getXHero() {return x_hero;}
    public double getYHero() {return y_hero;}
    public double getXFoe() {return x_foe;}
    public double getYFoe() {return y_foe;}
    public int getJumpOk() {return jumpOk;}
}