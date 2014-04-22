package nitrogene.npc;

import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.core.Zoom;
import nitrogene.weapon.WeaponTimer;

public class TaskFire extends Task {
	public Craft target;
	public int weaponID;
	public int taskID = 0;
	public TaskFire(NPCship s, Craft t, int wid){
		super(s);
		target = t;
		weaponID = wid;
		ship.laserlist.get(weaponID).getTimer().start();
	}

	@Override
	public void activate(int delta, float camX, float camY) {
		ship.laserlist.get(weaponID).update(craftX, craftY, camX, camY,delta);
		ship.laserlist.get(weaponID).setTarget((float) (target.getCenterX()), (float) (target.getCenterY()));
		//WeaponTimer t = ship.laserlist.get(weaponID).getTimer();
		
		//		ship.laserlist.get(weaponID)
	}

	@Override
	public void close(int delta) {
		//ship.laserlist.get(weaponID).getTimer().isLocked = true;
	}

	@Override
	public int getTaskID() {
		// TODO Auto-generated method stub
		return taskID;
	}
};
