import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

public class BaseActor extends Actor
{
    public TextureRegion region;
    public Rectangle boundary;
    public float velocityX;
    public float velocityY;

    BaseActor()
    {
        super();
        region = new TextureRegion();
        boundary = new Rectangle();
    }
    
    void setTexture(Texture t)
    { 
        int w = t.getWidth();
        int h = t.getHeight();
        setWidth( w );
        setHeight( h );
        region.setRegion( t );
    }
    
    Rectangle getBoundingRectangle()
    {
        boundary.set( getX(), getY(), getWidth(), getHeight() );
        return boundary;
    }    
    
    public void act(float dt)
    {
        super.act( dt );
        moveBy( velocityX * dt, velocityY * dt );
    }
    
    
    public void draw(Batch batch, float parentAlpha) 
    {
        //super.draw( batch, parentAlpha ); // but.... this is empty, so can be deleted here...
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        if ( isVisible() )
            batch.draw( region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
    }
}