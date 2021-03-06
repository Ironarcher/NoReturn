package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.util.EnumStatus;
import nitrogene.world.World;

import org.newdawn.slick.Image;

public class Capacitor extends ShipSystem{
	private float capacity, stored, maxcapacity;
	
	public Capacitor(Craft c, float x, float y, int maxhp, int durability, int damageradius, float capacity){
		super(c,x,y,maxhp,durability,damageradius,0f);
		this.maxcapacity = capacity;
		this.capacity = capacity;
		this.stored = 0f;
	}
	
	@Override
	public void update(int delta, float camX, float camY){
		
		//Testing whether the health is at or below zero.
		if(hp <= 0){
			this.capacity = this.maxcapacity/2;
			this.setStatus(EnumStatus.DAMAGED);
		} else{
			this.setStatus(EnumStatus.READY);
		}
	}
	
	public void sendPower(float amt, ShipSystem system){
		system.receivePower(amt);
	}
	
	public void receivePower(float amount, Core core){
		this.powerReceived = amount;
		if(amount<=capacity+amount){
			capacity+=amount;
		}
	}
	
	public float getStoredEnergy(){
		return stored;
	}
}
