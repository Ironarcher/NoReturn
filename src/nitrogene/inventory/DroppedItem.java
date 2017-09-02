package nitrogene.inventory;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import nitrogene.core.AssetManager;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;
import nitrogene.gui.Sprite;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AngledMovement;
import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

public class DroppedItem extends PhysicalObject{
	private ArrayList<Item> droplist;

	public DroppedItem(ArrayList<Item> droplist, float x, float y) throws SlickException{
		super(x, y);
		this.droplist = droplist;
	}
	

	public void load(float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		//ADD DIFFERENT CLASSES OF RESOURCES HERE FOR DIFFERENT IMAGES LOADING.
		if(droplist.get(0).getType() == EnumDrop.IRON){
			mainimg = new Sprite(((Image) AssetManager.get().get("ironitem")).copy());
		} else if(droplist.get(0).getType() == EnumDrop.TITANIUM){
			mainimg = new Sprite(((Image) AssetManager.get().get("titaniumitem")).copy());
		} else if(droplist.get(0).getType() == EnumDrop.AMMO){
			mainimg = new Sprite(((Image) AssetManager.get().get("ammoitem")).copy());
		} else{
			mainimg = new Sprite(((Image) AssetManager.get().get("ironitem")).copy());
		}
		boundbox = GlobalInformation.getHitbox(getSprite().getResourceReference());
		if(boundbox == null){
			Resources.log(mainimg.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		boundbox = boundbox.transform(Transform.createScaleTransform(scalefactor, scalefactor));
		init(getSprite().getImage().getWidth(), getSprite().getImage().getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		System.out.println(newboundbox.getX() + "   :   " + newboundbox.getY());
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	
	public ArrayList<Item> getItemsInDrop(){
		return droplist;
	}
	
	public void destroy(ArenaMap map){
		this.mainimg = null;
		map.getDroppedItem().remove(this);
	}
}
