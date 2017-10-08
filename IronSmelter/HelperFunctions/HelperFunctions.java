package scripts.IronSmelter.HelperFunctions;

import java.util.List;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;

public class HelperFunctions {
	// Check if stamina potion is active
	public static boolean isStaminaActive() {
		return Game.getSetting(Constants.STAMINA_ACTIVE) > 0;
	} 
		
	// Drink stamina if in inventory and potion not currently active
	public static void drinkStamina() {
		if (Inventory.getCount(Constants.STAMINAS) > 0 && !isStaminaActive() && Game.getRunEnergy() < 60) {
			final RSItem[] POTS = Inventory.find(Constants.STAMINAS);
	        if (POTS.length > 0) {
	        	if (Clicking.click("Drink", POTS[0])) {
	        		IronSmelter.status = "Drinking stamina";
	        		Timing.waitCondition(new Condition() {
	        			@Override
	                    public boolean active() {
	        				General.sleep(100, 200);
	                        return isStaminaActive();
	                    }
	                }, General.random(4800, 6000));
	            }
	        }
	    }
	}
	
	public static boolean isNearObject(int objectID, int distance) {
		final RSObject[] object = Objects.findNearest(distance, objectID);
		if (object.length > 0) {
			if (object[0].isOnScreen()) {
				return true;
			} 
			return false;
		}
		return false;
	}
	
	public static boolean isFeeReady() {
		long feeTime = System.currentTimeMillis() - IronSmelter.feeTimer;
		if (feeTime >= 570000) {
			IronSmelter.feeTimer = System.currentTimeMillis();
			return true;
		} 
		return false;
	}
	
	public static int calculateAverage(List<Integer> times) {
		Integer sum = 0;
		if (!times.isEmpty()) {
			for (Integer holder : times) {
				sum += holder;
			}
			return sum.intValue() / times.size();
		}
		return sum;
	}
}
