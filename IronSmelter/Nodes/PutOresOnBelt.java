package scripts.IronSmelter.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;
import scripts.IronSmelter.HelperFunctions.HelperFunctions;
import scripts.IronSmelter.api.Node;

public class PutOresOnBelt extends Node{

	@Override
	public void execute() {
		final RSObject[] belt = Objects.findNearest(10, Constants.CONVEYOR_BELT);
		DynamicClicking.clickRSObject(belt[0], "Put-ore-on");
		IronSmelter.status = "Smelting ores";
		
		if (Banking.isPinScreenOpen()) {
			General.sleep(1000, 1000);
			Banking.inPin();
		}
		
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return Inventory.getCount(Constants.IRON_ORE) == 0;
			}
		}, 1000);
		
		General.sleep(1000, 1000);
	}
	
	

	@Override
	public boolean validate() {
		return Inventory.getCount(Constants.IRON_ORE) > 0 && HelperFunctions.isNearObject(Constants.CONVEYOR_BELT, 10);
	}
}
