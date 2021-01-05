import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
public class Level2 implements Screen
{
	//Setup camera and tile map
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;	
	private TiledMap map;

    private Stage mainStage;
    private Stage uiStage;

    private Car car; 
    private BaseActor pBrake;   
    public boolean win;

    private float timeElapsed;
    private Label timeLabel;
    
    //Score
	int score;
    private Label scoreLabel;
    
	private int[] background = new int[] {0};

    public Game game;
    public Level2(Game g)
    {
        game = g;
        create();
    }
    
    //This method establishes the stage, labels, and actors
    public void create() 
    {    
    	
    
        mainStage = new Stage();
        uiStage = new Stage();
        timeElapsed = 0;
        
        //Setup car
        car = new Car();
        mainStage.addActor(car);
        car.create();

        
        //Time
        BitmapFont font = new BitmapFont();
        String text = "Time: ";
        LabelStyle style = new LabelStyle( font, Color.NAVY );
        timeLabel = new Label( text, style );
        timeLabel.setFontScale(2);
        timeLabel.setPosition(510,440); 
        uiStage.addActor( timeLabel );
        
        
        //Electronic parking brake message
        pBrake = new BaseActor();
        pBrake.setTexture( new Texture(Gdx.files.internal("assets/cars/EPBrake.png")) );
        pBrake.setPosition( 20, 20 );
        pBrake.setVisible( false );
        uiStage.addActor( pBrake );

        //Score
        String sc = "Score: ";
        scoreLabel = new Label( sc, style );
        scoreLabel.setFontScale(2);
        scoreLabel.setPosition(40,440); 
        uiStage.addActor( scoreLabel );
        
    	win = false;
    }
    
    //This method renders the game
    public void render(float dt) 
    {  
    	
      //This does all the behind the scenes of the car. For more information, go to the Car class file
      car.update(pBrake, map, scoreLabel, score);
              
      //Update
      mainStage.act(dt);
      uiStage.act(dt);

      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       
       //Camera
       camera.position.set(car.getX() + car.getWidth() / 2, car.getY() + car.getHeight() / 2, 0);
       camera.update();
       
       renderer.setView(camera);
       renderer.render(background);

       //This is used to increment time and will be used to calculate the score
        if (!win)
        {
            timeElapsed += dt;
            timeLabel.setText( "Time: " + (int)timeElapsed );
        }
         
        uiStage.draw();  
        mainStage.draw();
        
        //Debug mode
        Debug.drawLines(car, map, camera);
        }
    
        
    public void resize(int width, int height) {  
    	camera.viewportWidth = width / 2.5f;
		camera.viewportHeight = height / 2.5f;
    }

    public void dispose() { 
    	map.dispose();
    	renderer.dispose();
    }
    
    public void show()    { 
    	
    	//This is used to load the tiled map
    	//Note: The map is currently being tweaked to create more accurate collisions
    	TmxMapLoader loader = new TmxMapLoader();
    	map = loader.load("assets/maps/level2.tmx");
    	renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
    }
    
    public void hide() {
    	dispose(); 
    }

    public void pause()   {  }

    public void resume()  {  }
}
