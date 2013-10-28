package nitrogene.npc;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.collision.Vector;
import nitrogene.core.Craft;
import nitrogene.core.Planet;
import nitrogene.world.ArenaMap;

public class NPCship extends Craft{
	private Relation relation;
	private ArrayList<Craft> crafttarget;
	private ArrayList<Planet> planettarget;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	public NPCship(float xpos, float ypos, Image img, float scale, ArenaMap map, Relation relation) throws SlickException{
		super(xpos, ypos, img, scale, map);
		this.relation = relation;
		crafttarget = new ArrayList<Craft>();
		planettarget = new ArrayList<Planet>();
	}
	public void addTask(Task t) {
		tasks.add(t);
	}
	@Override
	public void update(int delta){
		this.delta = delta;
		cumulative += delta;
		
		//Targetting
		if(crafttarget.get(0)!=null)
			this.laserlist.get(0).setTarget(crafttarget.get(0).getCenterX(), crafttarget.get(0).getCenterY());
		
		//Clock
		if(cumulative >= 1000){
			//1 second cumulative
			lifesupport.tick();
			cumulative -= 1000;
		}
		//movement.Accelerate(new Vector(boundbox.getCenterX(),boundbox.getCenterY()), delta);
		//move(20);
	}
	
	public void addCraftTarget(Craft craft){
		crafttarget.add(craft);
	}
	public void addPlanetTarget(Planet planet){
		planettarget.add(planet);
	}
	public void runTasks(int delta, float camX, float camY) {
		for (Task t: tasks) t.run(delta, camX, camY);		
	}
}
