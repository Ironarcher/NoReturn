package nitrogene.objecttree;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import nitrogene.collision.Vector;
import nitrogene.util.ImageBase;
import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

public class ImageObject extends PhysicalObject{
	//protected Movement movement;
	//protected ArenaMap map;
	//protected Image boundbox;
	//protected Image mainimg;
	//protected float scalefactor;
	//protected int delta;
	//protected float rotation;
	//protected float width, height;
	private float x;
	private float y;
	private float centerx;
	private float centery;

	public ImageObject(float width, float height, Image img, float scalefactor, ArenaMap map){
		super(width, height, img, scalefactor, map);
		ImageBase.registerImage(img);
	}
	
	public void move(int thrust, int delta){
		movement.Accelerate(new Vector(getCenterX(),getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		//mainimg.
		setX(getX()+((movement.getDx()*gj)*mm));
		setY(getY()+((movement.getDy()*gj)*mm));
	}
	
	public boolean isColliding(PhysicalObject obj){
		
		if(getCenterX() + width + this.movement.getDx() >= obj.getCenterX() ||
				getCenterY() + height + this.movement.getDy() >= obj.getCenterY() ||
				getCenterX() - width - this.movement.getDx() <= obj.getCenterX() ||
				getCenterY() - height - this.movement.getDy() <= obj.getCenterY()
				){
			if(ImageBase.contains(this.mainimg)) {
				ArrayList<int[]> pixels = ImageBase.getPixels(this.mainimg);
				for(int i = 0; i < pixels.size(); i++) {
					if(obj.isContaining(pixels.get(i)[0], pixels.get(i)[1])) {
						return true;
					}
				}
			} 
		}
		
		return false;
	}
	
	public boolean isContaining(float x, float y){
		if(getCenterX() + width + this.movement.getDx() >= x ||
				getCenterY() + height + this.movement.getDy() >= y ||
				getCenterX() - width - this.movement.getDx() <= x ||
				getCenterY() - height - this.movement.getDy() <= y
				){
		return pointOnImage(x, y);
		}
		else return false;
	}
	
	public float getRotation(){
		return rotation;
	}
	
	public Image getImage(){
		return mainimg;
	}
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public float getCenterX(){
		return centerx;
	}
	public float getCenterY(){
		return centery;
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public void setCenterX(float x){
		this.centerx = x;
	}
	public void setCenterY(float y){
		this.centery = y;
	}
	public boolean pointOnImage(float x, float y) {
		if(x-getX() < 0 || x-getX() >= mainimg.getWidth() || y-getY() < 0 || y-getY() >= mainimg.getHeight()) {
			return false;
		}
		Color c = mainimg.getColor((int) (x-getX()), (int)(y-getY()));
		return c.a == 0f;
	}
}
