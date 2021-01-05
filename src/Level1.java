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

/*Parking Perfection is a top down car parking game that will have multiple levels. The challenge is to get the highest score. This score is based on the time it takes to park correctly and the amount of collisions that have occurred. Players will be able to unlock different cars based on their level and high score.*/

public class Level1 implements Screen
{
	//Setup camera and tile map
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;	
	private TiledMap map;

    private Stage mainStage;
    private Stage uiStage;
    
    private BaseActor suv;
    private Car car; 
    private BaseActor pBrake;   
    public boolean win;
    
    //Time
    private float timeElapsed;
    private Label timeLabel;
    
    //Score
	int score;
    private Label scoreLabel;
    
    private BaseActor parkingSpot;
   
	private int[] background = new int[] {0};

    public Game game;
    
    public Level1(Game g)
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
        
        suv = new BaseActor();
        suv.setTexture( new Texture(Gdx.files.internal("assets/cars/suv.png")) );
        suv.setRotation(90);
        suv.setPosition(140, 180);
        uiStage.addActor(suv);
       
        parkingSpot = new BaseActor();
        parkingSpot.setTexture(new Texture(Gdx.files.internal("assets/cars/temp_park.png")));
        parkingSpot.setPosition(400,300);
        mainStage.addActor(parkingSpot);    
       
       //I am currently working on a better way to implement parking and will be adding a feature allowing the player to get bonus points
       //I am also working on adding parked cars which will be built into the tiled map, allowing for more efficiency
        
       //Animated people may be added later on using the AnimatedActor class        
        
        //Time
        BitmapFont font = new BitmapFont();
        String text = "Time: 0";
        LabelStyle style = new LabelStyle( font, Color.NAVY );
        timeLabel = new Label( text, style );
        timeLabel.setFontScale(2);
        timeLabel.setPosition(510,440); 
        uiStage.addActor( timeLabel );
        
        //Score
        String sc = "Score: 0";
        scoreLabel = new Label( sc, style );
        scoreLabel.setFontScale(2);
        scoreLabel.setPosition(40,440); 
        uiStage.addActor( scoreLabel );
        
        //Electronic parking brake message
        pBrake = new BaseActor();
        pBrake.setTexture( new Texture(Gdx.files.internal("assets/cars/EPBrake.png")) );
        pBrake.setPosition( 20, 20 );
        pBrake.setVisible( false );
        uiStage.addActor( pBrake );
       
        win = false;
    }
    
    //This method renders the game
    public void render(float dt) 
    {  
    	
     suv.moveBy(0, -2);
     if (suv.getY() < -30) {
    	 suv.remove();
    	 suv.clear();
     }
     
      //This does all the behind the scenes of the car. For more information, go to the Car class file
      car.update(pBrake, map, scoreLabel, score);
              
      //Update
      mainStage.act(dt);
      uiStage.act(dt);

      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      //Start level
      if (car.getBoundingRectangle().overlaps(parkingSpot.getBoundingRectangle())){
    	  //I am currently implementing a level 2 menu where users will be able to choose a new car
    	  game.setScreen( new Level2(game) );

      }
       
       //Camera
       camera.position.set(car.getX() + car.getWidth() / 2, car.getY() + car.getHeight() / 2, 0);
       camera.update();
    
       renderer.setView(camera);
       renderer.render(background);

       //This is used to increment time and will be used to calculate the final score
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
    	map = loader.load("assets/maps/level1.tmx");

    	renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
    }
    
    public void hide() {
    	dispose(); 
    }

    public void pause()   {  }

    public void resume()  {  }
}
