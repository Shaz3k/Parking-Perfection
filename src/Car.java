import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class Car extends BaseActor
{
	float friction;
    float speed;
	
	float previousX;
	float previousY;
    final int mapWidth = 800;
    final int mapHeight = 600;
    
    //Add car driving sound effect
    Sound drive;    
	
    //This method creates the car and sets its starting position
    void create() 
    {            
    	friction = 0.01f;
    	this.setTexture( new Texture(Gdx.files.internal("assets/cars/car.png")));
   
        //Set start position
        this.setOrigin( this.getWidth()/2, this.getHeight()/2 );
        this.setPosition( 200, 80 );
    	this.setRotation(90);
    	
    	//Driving sound effect
    	drive = Gdx.audio.newSound(Gdx.files.internal("assets/cars/drive.mp3"));
    }

    //This method is used to control the car
    void update(BaseActor pBrake,  TiledMap map, Label scoreLabel, int score) {

    	previousX = this.getX();
    	previousY = this.getY();
    	
    	if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	drive.play();
    		
        	speed+=0.05f;
            if(speed > 4){
            	speed = 4;
            }
        }
          
         if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
          	drive.play();

            speed-=0.05f;
            if (speed<-3) {
             speed=-3;

            }
         }
          
      	//To make the movement more realistic, the rotation is based on the cars speed
          if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

        	  if (Math.abs(speed)>1) {
             	 this.setRotation(this.getRotation()+1.5f);
              }
        	  
              if (Math.abs(speed)<1 && Math.abs(speed)>0.5) {
              	 this.setRotation(this.getRotation()+1f);
               }
        	  
              if (Math.abs(speed)<0.5 && Math.abs(speed)>0.1) {
             	 this.setRotation(this.getRotation()+0.5f);
              }
              
              if (Math.abs(speed)<0.1 && Math.abs(speed)>0.01) {
              	 this.setRotation(this.getRotation()+0.1f);
              }
          }
          
          if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	  if (Math.abs(speed)>1) {
        		  this.setRotation(this.getRotation()-1.5f);      
              }
              
              if (Math.abs(speed)<1 && Math.abs(speed)>0.5) {
               	 this.setRotation(this.getRotation()-1f);
                }
        	  
              if (Math.abs(speed)<0.5 && Math.abs(speed)>0.1) {
             	 this.setRotation(this.getRotation()-0.5f);
              }
              if (Math.abs(speed)<0.1 && Math.abs(speed)>0.01) {
              	 this.setRotation(this.getRotation()-0.1f);
              }
          }
    	  
          //Indicate if player can use the electronic parking brake
          if (Math.abs(speed)<0.12 && Math.abs(speed)>0) {
    		  pBrake.setVisible( true );

    		  if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
        		  pBrake.setVisible( false );
    			  speed=0;
        	  }
          }
          else {
    		  pBrake.setVisible( false );
          }
          
         
          //Account for friction in the speed
          speed *= (1 - friction);
          
          //velocity is a vector -> scalar & magnitude
          Vector2 velocity = getvelocity(speed,this.getRotation());
      
          //Set the car up based on its x and y components
          this.setX(this.getX() + velocity.x);
          this.setY(this.getY() + velocity.y);
         
          collision(map, scoreLabel, score); 
   }
    
   //This method is used to create a vector for velocity
   private Vector2 getvelocity(float speed, float rotation){
       Vector2 velocity = new Vector2();
       float velocityX = (float) Math.cos(Math.toRadians(rotation)) * speed;
       float velocityY = (float) Math.sin(Math.toRadians(rotation)) * speed;
       velocity.x = velocityX;
       velocity.y = velocityY;
       return velocity;
    }

   //This method is used for collisions and is currently being worked on
   private void collision(TiledMap map, Label scoreLabel, int score) {
	
       Rectangle carRectangle = this.getBoundingRectangle();
      
	   //This is used for collisions with other vehicles and sidewalks
	   
	   /* MapLayer collisionObjectLayer = map.getLayers().get(1);
        MapObjects objects = collisionObjectLayer.getObjects();
        
        
        boolean collision = false;

        //This code is commented out as it is still being worked on using the debug mode. Feel free to uncomment to see how the collisions are being used with the tiled map.
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
                     
            if (carRectangle.overlaps(rectangle)) {
            collision = true;
            
                
                //This adds a bounce effect to the this based on how it crashes
                if (collision == true) {
                
                if (speed>0 && speed <2) {
                speed=-0.1f;
                }
                                else if (speed>3) {
               	speed=-0.4f;

                }
               else if (speed<-2) {
              	speed =0.4f;
                }
               else if (speed<0 && speed>-1) {
                	speed = 0.5f;
               }

                //This prevents the car from going through the rectangles
                this.setX(previousX);
                this.setY(previousY);
                
                score-=5
                scoreLabel.setText( "Score: " + score);

                collision = false; 
               }

           }
       } */
        
        //This is used for road mark detection and deducts points if crossing a solid line
        MapLayer lineLayer = map.getLayers().get(2);
        MapObjects lines = lineLayer.getObjects();
                      
        for (RectangleMapObject rectangleObject : lines.getByType(RectangleMapObject.class)) {
      	  Rectangle roadrectangle = rectangleObject.getRectangle();
                      
            if (carRectangle.overlaps(roadrectangle)) {
            	//The frequency of the score deduction is still being worked on and will most likely be that every 3 seconds points will be deducted if continuosly on the line
            	score-=3;
            	scoreLabel.setText( "Score: " + score );
           
            }  
       
        }
   	}
}