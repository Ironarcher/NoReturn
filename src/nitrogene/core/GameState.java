package nitrogene.core;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import nitrogene.collision.AABB;
import nitrogene.collision.CollisionLibrary;
import nitrogene.collision.Vector;
import nitrogene.gui.Minimap;
import nitrogene.npc.NPCship;
import nitrogene.npc.Relation;
import nitrogene.npc.TaskFire;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AnimationManager;
import nitrogene.util.Direction;
import nitrogene.util.Explosion;
import nitrogene.util.PauseButton;
import nitrogene.util.Stars;
import nitrogene.util.Target;
import nitrogene.util.TickSystem;
import nitrogene.util.ZoomEnum;
import nitrogene.weapon.LaserLauncher;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class GameState extends BasicGameState{
	Craft craft;
	private ParticleSystem shockwave;
	NPCship enemy;
	PauseButton resume, restart, hangar, menu, options, exit;
	Image craftImage, statis, mapbackground, slaserimage, sun, backing, shockimage, GUI, pausemenu, img1, enemyImage;
	Image pauseexitdown, pauseexitup, pausehangardown, pausehangarup, pausemenudown, pausemenuup, pauseoptionsdown, pauseoptionsup,
	pauserestartdown,pauserestartup,pauseresumeup,pauseresumedown;
	Particle part;
	ArenaMap map;
	Stars stars;
	SpriteSheet spriteex;
	private Animation animation;
	private int mapwidth, mapheight;
	//private Minimap minimap;
	private int offsetX, offsetY;
	private final int SCR_width, SCR_height;
	private int zoomwidth, zoomheight;
	private float pausemenux, pausemenuy;
	private float camX, camY;
	private ArrayList<PhysicalObject> objlist;

	private boolean PAUSED = false;
	Sound basicTestLaser;

	public GameState(int width, int height) {
		this.SCR_width = width;
		this.SCR_height = height;
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		CursorSystem.init();
		Zoom.setZoom(ZoomEnum.MAP);
		Zoom.setZoomWindow(SCR_width, SCR_height);
		objlist = new ArrayList<PhysicalObject>();
		
		//other variables
				mapwidth = 5000;
				mapheight = 2000;
				offsetY = SCR_height/2;
		    	offsetX = SCR_width/2;
		    	camX = 0;
		    	camY = 0;

		//timercontrol.addTimer(new WeaponTimer(Craft.laserlist.get(0)));
		//load sounds here
		basicTestLaser = new Sound("res/sound/laser1final.ogg");
		
		map = new ArenaMap(5,offsetX,offsetY,mapwidth,mapheight,craft);
		for(Planet pl : map.getPlanets()){
			objlist.add(pl);
		}
		
		//load images and objects here
		craftImage = new Image("res/klaarship4.png");
		craft = new Craft(SCR_width/2-175, (float) (SCR_height/2-88.5), craftImage, 1, map);
		objlist.add(craft);
		enemyImage = new Image("res/klaarship4.png");
		enemy = new NPCship(1200, 1200, enemyImage, 1, map, Relation.HOSTILE);
		enemy.addCraftTarget(craft);
		enemy.addTask(new TaskFire(enemy, craft, 0));
		enemy.getImage().rotate(180);
		objlist.add(enemy);
		
		sun = new Image("res/sun_1.png");
		pausemenu = new Image("res/button/pauseback.png");
		shockimage = new Image("res/shockwave_particle.png");
		statis = new Image("res/klaarship4.png");
		slaserimage = new Image("res/LaserV2ro.png");
		GUI = new Image("res/GUIportrait.png");
    	
    	//minimap = new Minimap(300, 121, SCR_width, SCR_height, mapwidth, mapheight, map.getPlanets(), map.getCrafts());
    	stars = new Stars(2,mapwidth,mapheight);
    	//ADDRESS PROBLEM
    	
    	
    	shockwave = new ParticleSystem(shockimage,1500);
    	File shockfile = new File("res/test_emitter.xml");

    	try {
			ConfigurableEmitter emitter = ParticleIO.loadEmitter(shockfile);
			emitter.setPosition(500,500);

			shockwave.addEmitter(emitter);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	shockwave.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		part = shockwave.getNewParticle(shockwave.getEmitter(0), 3000f);
		
		//buttons
		pausemenux = (SCR_width/2) - 52;
		pausemenuy = (SCR_height/2) - 102.5f;
		pauseexitdown = new Image("res/button/pauseexitdown.png");
		pauseexitup = new Image("res/button/pauseexitup.png");
		pausehangardown = new Image("res/button/pausehangardown.png");
		pausehangarup = new Image("res/button/pausehangarup.png");
		pausemenudown = new Image("res/button/pausemenudown.png");
		pausemenuup = new Image("res/button/pausemenuup.png");
		pauseoptionsdown = new Image("res/button/pauseoptionsdown.png");
		pauseoptionsup = new Image("res/button/pauseoptionsup.png");
		pauserestartdown = new Image("res/button/pauserestartdown.png");
		pauserestartup = new Image("res/button/pauserestartup.png");
		pauseresumedown = new Image("res/button/pauseresumedown.png");
		pauseresumeup = new Image("res/button/pauseresumeup.png");
		try {
			resume = new PauseButton(pausemenux + 6, pausemenuy + 6, pauseresumeup, pauseresumedown);
			menu = new PauseButton(pausemenux + 6, pausemenuy + 105, pausemenuup, pausemenudown);
			restart = new PauseButton(pausemenux + 6, pausemenuy + 39, pauserestartup, pauserestartdown);
			hangar = new PauseButton(pausemenux + 6, pausemenuy + 72, pausehangarup, pausehangardown);
			options = new PauseButton(pausemenux + 6, pausemenuy + 138, pauseoptionsup, pauseoptionsdown);
			exit = new PauseButton(pausemenux + 6, pausemenuy + 171, pauseexitup, pauseexitdown);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(!container.hasFocus()){
			PAUSED = true;
		}

		Zoom.setZoomWindow(SCR_width, SCR_height);
		
		Input input = container.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			PAUSED = !PAUSED;
		}
		if(input.isKeyPressed(Input.KEY_X)){
			container.exit();
		}
		if(!PAUSED){
		if(TickSystem.isPaused()) {
			TickSystem.gameResume();
		}
		AnimationManager.updateAnimation(delta);
		CursorSystem.update(container);
    	shockwave.update(delta);
    	//minimap.update(camX, camY);
    	for(PhysicalObject obj : objlist){
    		obj.update(delta);
    	}
    	part.update(delta);
    	
    	
    	//Input Controllers
    	
		if(input.isKeyPressed(Input.KEY_W)){
			craft.getMovement().Toggle(Direction.UP);
		}
		if(input.isKeyPressed(Input.KEY_S)){
				craft.getMovement().Toggle(Direction.DOWN);
		}
		if(input.isKeyPressed(Input.KEY_A)){
				craft.getMovement().Toggle(Direction.LEFT);
		}  
		if(input.isKeyPressed(Input.KEY_D)){  
				craft.getMovement().Toggle(Direction.RIGHT);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)){
			craft.getMovement().Break(delta);
		}
		
		//projectile control
		enemy.update(delta);
		for(int m = 0; m<enemy.laserlist.size(); m++) {
			LaserLauncher laserlauncher = enemy.laserlist.get(m);
			 laserlauncher.update(enemy.getX(), enemy.getY());
			
			for(int i = 0;i<enemy.laserlist.get(m).slaserlist.size();i++){
				SLaser laser = enemy.laserlist.get(m).slaserlist.get(i);
				
				if (!laser.isRotated()){
					laser.getImage().setRotation(0);
					laser.getImage().setCenterOfRotation(laserlauncher.getImage().getWidth()/2,laserlauncher.getImage().getHeight()/2);
					laser.getImage().rotate(laser.getAngle());
					basicTestLaser.play(1f, 0.5f);
					laser.setRotated(true);
				}
				laser.move(delta);
				if (laser.getX() > mapwidth - 20 || laser.getY() > mapheight - 30 || laser.getX() < 0 || laser.getY() < 0){
					enemy.laserlist.get(m).slaserlist.remove(i);

					System.gc();
				}
			}
		}
		for(int m = 0; m<craft.laserlist.size();m++){
			LaserLauncher laserlauncher = craft.laserlist.get(m);
			 laserlauncher.update(craft.getX(), craft.getY());
			
			for(int i = 0;i<craft.laserlist.get(m).slaserlist.size();i++){
				SLaser laser = craft.laserlist.get(m).slaserlist.get(i);
				
				if (!laser.isRotated()){
					laser.getImage().setRotation(0);
					laser.getImage().setCenterOfRotation(laserlauncher.getImage().getWidth()/2,laserlauncher.getImage().getHeight()/2);
					laser.getImage().rotate(laser.getAngle());
					basicTestLaser.play(1f, 0.5f);
					laser.setRotated(true);
				}
				laser.move(delta);
				if (laser.getX() > mapwidth - 20 || laser.getY() > mapheight - 30 || laser.getX() < 0 || laser.getY() < 0){
					craft.laserlist.get(m).slaserlist.remove(i);

					System.gc();
				}
				for(int p = 0;p<map.getPlanets().size();p++){
					Planet mesh = map.getPlanets().get(p);
					mesh.getShake().update(delta);
					if(mesh.isColliding(laser)){
						mesh.damage(laser.getDamage(), map.getPlanets(), p);
						AnimationManager.addAnimation(new Explosion(laser.getCenterX(), laser.getCenterY(), 2.5f, 100));
						mesh.getShake().shakeObject(3, 1000);
						craft.laserlist.get(m).slaserlist.remove(i);
					}
					}
				laser = null;
			}
		}
		//collision control
		
		//for: craft vs. circles
		for(int e = 0;e<map.getPlanets().size();e++){
			Planet mesh = map.getPlanets().get(e);
			
			if(mesh.isColliding(craft)){
				craft.setHull(0d);
			}
			
			}
		
		camX = (float) ((craft.getX()+(craft.getImage().getWidth()/2))*Zoom.getZoom().scale) - (SCR_width/2);	 
		camY = (float) ((craft.getY()+(craft.getImage().getHeight()/2))*Zoom.getZoom().scale) - (SCR_height/2);
		
		}
		else{
		//pause menu here
			//Button Controllers
			if(!TickSystem.isPaused()) {
				TickSystem.gamePause();
			}
			resume.update(container);
	    	restart.update(container);
	    	menu.update(container);
	    	exit.update(container);
	    	options.update(container);
	    	hangar.update(container);
	    	
	    	if(resume.isClicked()){
	    		PAUSED = false;
	    	}
	    	if(menu.isClicked()) game.enterState(1);
	    	if(exit.isClicked()) container.exit();
		}
		}
		
		
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.translate(-camX, -camY);
		g.setBackground(Color.black);
		g.scale((float)Zoom.getZoom().scale,(float)Zoom.getZoom().scale);
		
		g.setColor(Color.red);
		g.drawRect(0, 0, mapwidth, mapheight);
		g.setColor(Color.yellow);
		g.drawRect(0,0, mapwidth-5, mapheight-5);
		stars.render((float) (camX*Zoom.getZoom().inverse),(float) (camY*Zoom.getZoom().inverse));
		
		enemy.getImage().draw(enemy.getX(), enemy.getY());
		
		craft.getImage().draw(craft.getX(), craft.getY());
		craft.shield.getImage().draw(craft.getShieldX(),craft.getShieldY(),1.2f);
		//systems
		craft.core.getImage().drawCentered(craft.core.getX()+craft.getX(),craft.core.getY()+craft.getY());
		craft.shield.getImage().drawCentered(craft.shield.getX()+craft.getX(),craft.shield.getY()+craft.getY());
		craft.engine.getImage().drawCentered(craft.engine.getX()+craft.getX(),craft.engine.getY()+craft.getY());
		craft.lifesupport.getImage().drawCentered(craft.lifesupport.getX()+craft.getX(),craft.lifesupport.getY()+craft.getY());
		
		for(int i = 0; i < map.getPlanets().size(); i ++){
			Planet mesh = map.getPlanets().get(i);
			//image culling
			if(mesh.getX()-(mesh.getImage().getWidth()*mesh.getScale())>Zoom.getZoomWidth()+camX||
					mesh.getX()+(mesh.getImage().getWidth()*mesh.getScale())<camX||
					mesh.getY()-(mesh.getImage().getHeight()*mesh.getScale())>Zoom.getZoomHeight()+camY||
					mesh.getY()+(mesh.getImage().getHeight()*mesh.getScale())<camY){
				mesh = null;
				continue;
			}
			//label at top with health
			if(mesh.getHp() < mesh.getMaxHp()){
				if(mesh.getHp() <= 200) g.setColor(Color.red);
				else if(mesh.getHp() <= 500) g.setColor(Color.orange);
				else g.setColor(Color.green);
				float gg = mesh.getHp();
				float ff = mesh.getMaxHp();
				g.fillRect(mesh.getX(), mesh.getY() - 20, (gg/ff) * (mesh.getShape().getWidth()), 20);
			}
			//drawing planet
			mesh.getImage().draw(mesh.getX()+mesh.getShake().getDx(),mesh.getY()+mesh.getShake().getDy(),mesh.getScale());
			mesh = null;
		}
		
		AnimationManager.renderAnimation();
		
		for(LaserLauncher cannon : craft.laserlist){
			cannon.render(g,camX,camY);
		}
		for(LaserLauncher cannon : enemy.laserlist){
			cannon.render(g,camX,camY);
		}
		
		part.render();
		shockwave.render();
		 
		g.scale((float)Zoom.getZoom().inverse,(float)Zoom.getZoom().inverse);
		GUI.draw(camX,camY);
		//minimap.render(g);
		if (PAUSED) {
	        Color trans = new Color(0f,0f,0f,0.5f);
	        g.setColor(trans);
	        g.fillRect(camX,camY, SCR_width, SCR_height);
	        
	        pausemenu.draw(pausemenux+camX,pausemenuy+camY);
	        resume.render(g,camX,camY);
	        restart.render(g,camX,camY);
	        options.render(g,camX,camY);
	        hangar.render(g,camX,camY);
	        menu.render(g,camX,camY);
	        exit.render(g,camX,camY);
	   }
	}

	public void mousePressed(int button, int x, int y){
		x *= Zoom.getZoom().inverse;
		y *= Zoom.getZoom().inverse;
		if(!PAUSED) {
			if(button == 1) {
				TickSystem.pause();
			} else if (button == 0){
				for(LaserLauncher cannon : craft.laserlist){
					cannon.toggleFire(x,y,(float) (camX*Zoom.getZoom().inverse),(float) (camY*Zoom.getZoom().inverse));
				}
			}
		}
	}

	public Object getTargetObject(float f, float g) {
		/*
		for(int e = 0;e<boxmeshlist.size();e++){
			AABB box = new AABB(1f,1f);
			box.update(new Vector(f,g));
			if(CollisionLibrary.testBoxBox(boxmeshlist.get(e).boundbox, box)){
				return this.boxmeshlist.get(e);
			}
		}
		*/
		for(Planet pl : map.getPlanets()){
			if(pl.isContaining(f,g)){
				return pl;
			}
		}
		return null;
		
	}
	


	@Override
	public int getID() {
		return 2;
	}

}
