package scripts.IronSmelter.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSObject;

import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;
import scripts.IronSmelter.HelperFunctions.HelperFunctions;
import scripts.IronSmelter.api.Node;

public class WithdrawBars extends Node{

	@Override
	public void execute() {
		while (!hasDispenserInterface()) {
			final RSObject[] dispenser = Objects.findNearest(10, Constants.BAR_DISPENSER);
			DynamicClicking.clickRSObject(dispenser[0], "Take");
			IronSmelter.status = "Withdrawing bars";
		
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return hasDispenserInterface();
				}
			}, 2000);
		}
		
		if (hasDispenserInterface()) {
			RSInterface dispenserScreen = Interfaces.get(Constants.DISPENSER_MASTER, Constants.DISPENSER_CHILD);
			dispenserScreen.click("Take");
			
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Inventory.getCount(Constants.IRON_BAR) > 0;
				}
			}, 4000);
		}
		
		IronSmelter.bars += Inventory.getCount(Constants.IRON_BAR);
		
	}

	@Override
	public boolean validate() {
		return Inventory.getCount(Constants.IRON_ORE) == 0 && HelperFunctions.isNearObject(Constants.BAR_DISPENSER, 10) && !Inventory.isFull();
	}
	
	private boolean hasDispenserInterface() {
    	RSInterface dispenserScreen = Interfaces.get(Constants.DISPENSER_MASTER); // code screen interface
    	return dispenserScreen != null;
	}
}
