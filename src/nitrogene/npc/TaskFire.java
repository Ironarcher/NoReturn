package nitrogene.npc;

import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.core.Zoom;
import nitrogene.weapon.WeaponTimer;

public class TaskFire extends Task {
	public Craft target;
	public int weaponID;
	private boolean once = false;
	public TaskFire(NPCship s, Craft t, int wid){
		super(s);
		target = t;
		weaponID = wid;
		ship.laserlist.get(weaponID).getTimer().start();
	}

	@Override
	public void activate(int delta, float camX, float camY) {
		ship.laserlist.get(weaponID).setTarget((float) (target.getX()), (float) (target.getY()));
		ship.laserlist.get(weaponID).update(camX, camY);
		//WeaponTimer t = ship.laserlist.get(weaponID).getTimer();
		
		//		ship.laserlist.get(weaponID)

	}

	@Override
	public void close(int delta) {
		//ship.laserlist.get(weaponID).getTimer().isLocked = true;
	}
};
