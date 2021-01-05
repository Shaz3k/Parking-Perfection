import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class Debug {
	
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    
	//This renders the rectangles made for the collisions
    private static void RectangleLines(float x, float y, float sizeX, float sizeY, Matrix4 Matrix) {
        shapeRenderer.begin(ShapeType.Line);
        
        //Matrix allows for rotation and translating shapes as the camera moves
        shapeRenderer.setProjectionMatrix(Matrix);
        shapeRenderer.setColor(1,1,0,1);
        shapeRenderer.rect(x, y, sizeX, sizeY);
        shapeRenderer.end();
    }
    
    public static void drawLines(Car car, TiledMap map, OrthographicCamera camera) {
    
	    Rectangle carRect = car.getBoundingRectangle();
	    
	    MapLayer collisionObjectLayer = map.getLayers().get(1);
	    MapObjects objects = collisionObjectLayer.getObjects();
	     
	    for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
	  	  Rectangle rectangle = rectangleObject.getRectangle();
  	  
	  	  Debug.RectangleLines(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), camera.combined);
	  	  Debug.RectangleLines(carRect.getX(), carRect.getY(), carRect.getWidth(), carRect.getHeight(), camera.combined);
	    }
    
	    MapLayer lineLayer = map.getLayers().get(2);
	    MapObjects lines = lineLayer.getObjects();
	    
	    for (RectangleMapObject rectangleObject : lines.getByType(RectangleMapObject.class)) {
	  	  Rectangle roadrectangle = rectangleObject.getRectangle();
	
	  	  Debug.RectangleLines(roadrectangle.getX(), roadrectangle.getY(), roadrectangle.getWidth(), roadrectangle.getHeight(), camera.combined);
	  	  
	    }   
    }
}