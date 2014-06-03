package nitrogene.core;

import java.io.File;

import nitrogene.util.AppData;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceGame extends StateBasedGame{

	private static int SCRwidth;
	private static int SCRheight;
	public SpaceGame(String title) {
		super("No Return");
		SCRwidth = 1366;
		SCRheight = 768;
		AppData.runInit();
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new SpaceGame("Space Game"));
		app.setDisplayMode(SCRwidth, SCRheight, false);
		app.start();
	}
 
 
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		GlobalInformation.initHitboxData();
		this.addState(new LoadingState()); //0
		this.addState(new MenuState(SCRwidth,SCRheight)); //1
		this.addState(new GameState(SCRwidth,SCRheight)); //2
		this.addState(new HangarState(SCRwidth,SCRheight)); //3
		this.addState(new OptionState(SCRwidth,SCRheight)); //4
		this.addState(new ShipState(SCRwidth,SCRheight)); //5
		this.enterState(0);
	}

}