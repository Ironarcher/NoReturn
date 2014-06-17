package nitrogene.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState{
	String lastLoaded = "";
	/** The music that will be played on load completion */
	/** The sound that will be played on load completion */
	/** The image that will be shown on load completion */
	/** The font that will be rendered on load completion */
	/** The next resource to load */
	private DeferredResource nextResource;
	/** True if we've loaded all the resources and started rendereing */
	private boolean started;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		LoadingList.setDeferredLoading(true);
		
		Image backgroundimg = new Image("res/menubackground4.png");
		AssetManager.get().put("menubackgroundimg", backgroundimg);
		
		Image defaultbuttonnormalimage = new Image("res/button/logomenbutton21.png");
		AssetManager.get().put("defaultbuttonnormal", defaultbuttonnormalimage);
		Image defaultbuttonhoverimage = new Image("res/button/logomenubutton2hover.png");
		AssetManager.get().put("defaultbuttonhover", defaultbuttonhoverimage);
		Image defaultbuttonpressedimage = new Image("res/button/logomenubutton2hover.png");
		AssetManager.get().put("defaultbuttonpressed", defaultbuttonpressedimage);
		
		Image startButtonImage = new Image("res/hangar/startbuttonhangar.png");
		AssetManager.get().put("startbutton", startButtonImage);
		Image menuButtonImage = new Image("res/hangar/menubutton.png");
		AssetManager.get().put("menubutton", menuButtonImage);
		Image minusButtonImage = new Image("res/hangar/minusbutton.png");
		AssetManager.get().put("minusbutton", minusButtonImage);
		Image plusButtonImage = new Image("res/hangar/plusbutton.png");
		AssetManager.get().put("plusbutton", plusButtonImage);
		Image shipButtonImage = new Image("res/hangar/button2.png");
		AssetManager.get().put("shipbutton", shipButtonImage);
		Image shipButtonImageHighlighted = new Image("res/hangar/buttonhighlighted2.png");
		AssetManager.get().put("shipbuttonhighlighted", shipButtonImageHighlighted);
		
		Image normalImageTab = new Image("res/hangar/tab2.png");
		AssetManager.get().put("normaltab", normalImageTab);
		Image pressedImageTab = new Image("res/hangar/tabhighlighted2.png");
		AssetManager.get().put("pressedtab", pressedImageTab);
		Image normalImageBuyButton = new Image("res/hangar/button2.png");
		AssetManager.get().put("normalbuybutton", normalImageBuyButton);
		Image pressedImageBuyButton = new Image("res/hangar/buttonhighlighted2.png");
		AssetManager.get().put("normalbuybutton", pressedImageBuyButton);
		Image coinsImage = new Image("res/hangar/twocoins.png");
		AssetManager.get().put("coins", coinsImage);
		Image observatoryBackground = new Image("res/hangar/observatory1.png");
		AssetManager.get().put("observatory", observatoryBackground);
		Image hangarBackground = new Image("res/hangar/background.png");
		AssetManager.get().put("hangarbackground", hangarBackground);
		
		Image optionsBackground = new Image("res/button/optionsbackground.png");
		AssetManager.get().put("optionsbackground", optionsBackground);
		Image sliderImage = new Image("res/button/sliderbutton1.png");
		AssetManager.get().put("slider", sliderImage);
		
		Image shipSelectionImage = new Image("res/hangar/shipselectionfinal.png");
		AssetManager.get().put("shipselection", shipSelectionImage);
		
		Image craftImage = new Image("res/klaarship6.png");
		AssetManager.get().put("craftimage", craftImage);
		Image sunImage = new Image("res/sun_1.png");
		AssetManager.get().put("sun1", sunImage);
		Image pauseMenu = new Image("res/button/pauseback.png");
		AssetManager.get().put("pausemenu", pauseMenu);
		Image slaserImage = new Image("res/LaserV2ro.png");
		AssetManager.get().put("slaserimage", slaserImage);
		Image GUIimage = new Image("res/GUIportrait.png");
		AssetManager.get().put("GUI", GUIimage);
		
		Image pauseExitDownImage = new Image("res/button/pauseexitdown.png");
		AssetManager.get().put("pauseexitdown", pauseExitDownImage);
		Image pauseExitUpImage = new Image("res/button/pauseexitup.png");
		AssetManager.get().put("pauseexitup", pauseExitUpImage);
		Image pauseHangarDownImage = new Image("res/button/pausehangardown.png");
		AssetManager.get().put("pausehangardown", pauseHangarDownImage);
		Image pauseHangarUpImage = new Image("res/button/pausehangarup.png");
		AssetManager.get().put("pausehangarup", pauseHangarUpImage);
		Image pauseMenuDownImage = new Image("res/button/pausemenudown.png");
		AssetManager.get().put("pausemenudown", pauseMenuDownImage);
		Image pauseMenuUpImage = new Image("res/button/pausemenuup.png");
		AssetManager.get().put("pausemenuup", pauseMenuUpImage);
		Image pauseOptionsDownImage = new Image("res/button/pauseoptionsdown.png");
		AssetManager.get().put("pauseoptionsdown", pauseOptionsDownImage);
		Image pauseOptionsUpImage = new Image("res/button/pauseoptionsup.png");
		AssetManager.get().put("pauseoptionsup", pauseOptionsUpImage);
		Image pauseRestartDownImage = new Image("res/button/pauserestartdown.png");
		AssetManager.get().put("pauserestartdown", pauseRestartDownImage);
		Image pauseRestartUpImage = new Image("res/button/pauserestartup.png");
		AssetManager.get().put("pauserestartup", pauseRestartUpImage);
		Image pauseResumeUpImage = new Image("res/button/pauseresumeup.png");
		AssetManager.get().put("pauseresumeup", pauseResumeUpImage);
		Image pauseResumeDownImage = new Image("res/button/pauseresumedown.png");
		AssetManager.get().put("pauseresumedown", pauseResumeDownImage);
		
		System.out.println(LoadingList.get().getRemainingResources());
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(LoadingList.get().getRemainingResources() > 0){
			nextResource = LoadingList.get().getNext();
			try {
				lastLoaded = nextResource.getDescription();
				nextResource.load();
				System.out.println(nextResource.getDescription());
				Set<Entry<String, Object>> entires = AssetManager.get().entrySet();
				for(Entry<String, Object> ent : entires){
					Image entimage = (Image)ent.getValue();
					if(entimage.getResourceReference() == nextResource.getDescription()){
						entimage.setFilter(Image.FILTER_NEAREST);
						AssetManager.get().put(ent.getKey(), entimage);
						break;
					}
				}
				System.out.println("LOADING!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			game.enterState(1);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if(nextResource!=null){
			g.drawString("Loaded: "+lastLoaded, 100, 100); 
		} else{
		g.drawString("LOADING COMPLETE", 200, 200);
		}
	}
	
	public int getID() {
		return 0;
	}

}
