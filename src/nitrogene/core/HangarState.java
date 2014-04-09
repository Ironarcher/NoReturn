package nitrogene.core;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.Button;
import nitrogene.util.BuyButton;
import nitrogene.util.Tab;
import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HangarState extends BasicGameState{

	private int width, height, repx, repy;
	private Image backgroundimg, observatory;
	private int scalefactor;
	private int obserx, obsery;
	
	//tabs
	private ArrayList<Tab> tablist;
	private ArrayList<BuyButton> buttonlist;
	private ArrayList<EnumWeapon> weapons;
	private Tab weapontab;
	
	//buttons for purchasing systems
	private BuyButton basiclaserbutton, splitlaserbutton, splitlaser2button, pulsarbutton, pulsar2button;
	private Button startbutton;
	
	public HangarState(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		tablist = new ArrayList<Tab>();
		buttonlist = new ArrayList<BuyButton>();
		weapons = new ArrayList<EnumWeapon>();
		
		backgroundimg = new Image("res/hangar/background.png");
		observatory = new Image("res/hangar/observatory1.png");
		observatory.setFilter(Image.FILTER_NEAREST);
		
		this.repx = (int) Math.ceil(width/100.0);
		this.repy = (int) Math.ceil(height/100.0);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(observatory.getWidth()*scalefactor/2);
		obsery = (height/2)-(observatory.getHeight()*scalefactor/2);
		
		Image normalimgtab = new Image("res/hangar/tab2.png");
		Image pressedimgtab = new Image("res/hangar/tabhighlighted2.png");
		Image normalimgbuybutton = new Image("res/hangar/button2.png");
		Image pressedimgbuybutton = new Image("res/hangar/buttonhighlighted2.png");
		Image standardbutton = new Image("res/button/logomenubutton2.png");
		
		//Image buybuttonnormal = new Image("res/hangar/")
		
		try {
			weapontab = new Tab("", obserx+(11*scalefactor), obsery+(48*scalefactor), 15*scalefactor, 8*scalefactor, normalimgtab, null, pressedimgtab, null);
			tablist.add(weapontab);
			
			//Add +7+2 (+11) to y coordinate every button down
			basiclaserbutton = new BuyButton("Basic Laser", 20, obserx+(14*scalefactor), obsery+(59*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			splitlaserbutton = new BuyButton("Split Laser", 50, obserx+(14*scalefactor), obsery+(68*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			splitlaser2button = new BuyButton("Split Laser Mk.2", 80, obserx+(14*scalefactor), obsery+(79*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			pulsarbutton = new BuyButton("Pulsar", 80, obserx+(14*scalefactor), obsery+(90*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			pulsar2button = new BuyButton("Pulsar Mk.2", 150, obserx+(14*scalefactor), obsery+(101*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);

			buttonlist.add(basiclaserbutton);
			buttonlist.add(splitlaserbutton);
			buttonlist.add(splitlaser2button);
			buttonlist.add(pulsarbutton);
			buttonlist.add(pulsar2button);
			
			startbutton = new Button("Start", 500, 500, 100, 50, standardbutton, null, null, null);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		weapontab.update(container);
		startbutton.update(container);
		
		if(startbutton.isClicked()){
			GlobalInformation.setStartingWeapons(weapons);
			if(weapons.isEmpty()){
				System.out.println("CHECKPOINT 1 FAILURE");
			}
			game.enterState(2);
		}
		
		if(weapontab.getButtonDown()){
			basiclaserbutton.update(container, weapons, EnumWeapon.BASIC);
			splitlaserbutton.update(container, weapons, EnumWeapon.SPLIT);
			splitlaser2button.update(container, weapons, EnumWeapon.SPLIT2);
			pulsarbutton.update(container, weapons, EnumWeapon.PULSAR);
			pulsar2button.update(container, weapons, EnumWeapon.PULSAR2);
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//Render repeating background
		for(int x = 0; x < repx; x++){
			for (int y = 0; y < repy; y++){
				backgroundimg.draw(x*100,y*100);
			}
		}
		
		observatory.draw(obserx, obsery, scalefactor);
		
		weapontab.render(g);
		startbutton.render(g);
		
		if(weapontab.getButtonDown()){
			for(Button b : buttonlist){
				b.render(g, scalefactor);
			}
		}
	}
	
	@Override
	public int getID() {
		return 3;
	}

}
