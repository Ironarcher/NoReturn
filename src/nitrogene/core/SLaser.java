package nitrogene.core;

import nitrogene.collision.AABB;
import nitrogene.objecttree.RectangleObject;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import java.util.Random;


public class SLaser extends RectangleObject{
	
	private float startX = 0, startY = 0, desX = 0, desY = 0, speed = 0, dx, dy;
	public Point location = new Point(0,0);
	double mangle = 0;
	private float mmangle, scalesize, sspeed;
	private int sdamage;
	public AABB boundbox;
	private boolean isRotated = false, isPlaying = false;
	
	public SLaser(float startX, float startY, float destinX, float destinY, int accuracy, float speed, int damage, float size, Image img, ArenaMap map) throws SlickException{
		super(startX, startY, img.getWidth(), img.getHeight(), img, size, map);
		this.startX =startX;
		this.startY =startY;
		this.desX = destinX;
		this.desY = destinY;
		this.sdamage = damage;
		this.sspeed = speed;
		this.scalesize = size;
		this.mmangle = 0;
		location.setLocation(startX,startY);
		if(accuracy!=0){
		desX += randomize(accuracy);
		desY += randomize(accuracy);
		}
		recalculateVector(desX, desY);
		recalculateAngle(desX, desY);
	}
	/*
	public SLaser(float startX, float startY, float destinX, float destinY, int accuracy, float speed, int damage, float size, Image im) throws SlickException{
		super(startX, startY, img.getWidth(), img.getHeight(), img, size, map);
		theimage = im;
		this.startX =startX;
		this.startY =startY;
		this.desX = destinX;
		this.desY = destinY;
		this.sdamage = damage;
		this.sspeed = speed;
		this.scalesize = size;
		this.mmangle = 0;
		location.setLocation(startX,startY);
		if(accuracy!=0){
		desX += randomize(accuracy);
		desY += randomize(accuracy);
		}
		boundbox = new AABB(theimage.getTextureWidth() * size, theimage.getTextureHeight() * size);
		recalculateVector(desX, desY);
		recalculateAngle(desX, desY);
	}
	*/
	
	private void recalculateVector(float desX2, float desY2) {
		float vec = (float)(Math.atan2(desX2 - startX, desY2 - startY));
		//set speed
		speed = sspeed;

		this.dx = (float) Math.sin(vec) * speed;
        this.dy = (float) Math.cos(vec) * speed;
	}
	
	public void recalculateVector(){
		recalculateVector(desX, desY);
	}
	
	public void recalculateAngle(float desX2, float desY2){
		double mecX = (desX2 - startX);
		double mecY = (desY2 - startY);
		mangle = Math.toDegrees(Math.atan2(mecY,mecX));
		mmangle = (float) mangle;
	}
	
	public float getAngle(){
		return mmangle;
	}
	
	@Override
	public void move(int delta){
		float x = location.getX();
		float y = location.getY();
		
		x+=((dx*30f)*(delta/1000f));
		y+=((dy*30f)*(delta/1000f));
		
		location.setLocation(x,y);
		
		
		//update boundbox
		updateBounds(x,y);
	}
	
	
	private int randomize(int distance) {
		Random rand = new Random();
		int  n = rand.nextInt(distance * 2) + 1;
		return (int) (n* Zoom.getZoom().scale - distance);
		}
	
	public boolean isRotated(){
		return isRotated;
	}
	public int getDamage(){
		return sdamage;
	}
	public float getSize(){
		return scalesize;
	}
	public void setRotated(boolean rotate){
		isRotated = rotate;
	}
}
