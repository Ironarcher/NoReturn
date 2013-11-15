package nitrogene.npc;

public abstract class Task {
	protected NPCship ship;
	public boolean isComplete;
	public boolean enabled;
	public Task(NPCship s) {
		ship = s;
		isComplete = false;
		enabled = true;
	}
	public Task(NPCship s,boolean c) {
		ship = s;
		isComplete = c;
	}
	public NPCship getShip() {
		return ship;
	}
	public void setShip(NPCship s) {
		ship = s;
	}
	public void enable() {
		enabled = true;
	}
	public void disable() {
		enabled = false;
	}
	public void run(int delta, float camX, float camY) {
		if (enabled) activate(delta, camX, camY);
	}
	public abstract void activate(int delta, float camX, float camY);
	public abstract void close(int delta); //RUN THIS WHEN TASK IS DELETED OR ELSE BAD THINGS WILL HAPPEN
}