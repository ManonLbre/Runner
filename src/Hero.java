public class Hero extends AnimatedThing{

    private double opacity = 1.0; // Initial opacity of the hero

    public Hero(String fileName, Integer x, Integer y, Integer state){
        super(fileName, x, y,state);
    }
    public static void jump(){
        jumpOk = 20; //Jump parameter
        altitude = 0;
        System.out.println("Jump");
    }
    public static void shoot(){
        shoot = 20; //Shoot parameter
        System.out.println("Shoot");
    }
}