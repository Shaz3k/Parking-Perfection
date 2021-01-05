import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
public class GameLauncher
{
    public static void main (String[] args)
    {
        ParkingPerfection myProgram = new ParkingPerfection();
        LwjglApplication launcher = new LwjglApplication( myProgram );
    }
}