import com.badlogic.gdx.Game;
public class ParkingPerfection extends Game
{
    public void create() 
    {  
        GameMenu cm = new GameMenu(this);
        setScreen( cm );
    }
    
    public void dispose() {
    	super.dispose();
    }
    
    public void render() {
    	super.render();
    }
    
    public void resize(int width, int height) {
    	super.resize(width, height);
    }
    
    public void pause() {
    	super.pause();
    }
    
    public void resume() {
    	super.resume();
    }
    
}
    
