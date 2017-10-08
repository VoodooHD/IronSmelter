package scripts.IronSmelter.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.IronSmelter.AntiBan;
import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;
import scripts.IronSmelter.HelperFunctions.HelperFunctions;
import scripts.IronSmelter.api.Node;

public class WalkToDispenser extends Node{

	@Override
	public void execute() {
		AntiBan.activateRun();
		if (!HelperFunctions.isStaminaActive()) {
			HelperFunctions.drinkStamina();
		}
		Walking.walkTo(Constants.WITHDRAW_STAND_TILE);	
		IronSmelter.status = "Walking to bar dispenser";
		
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(500, 500);
				return Constants.WITHDRAW_STAND_AREA.contains(Player.getPosition()) && !Player.isMoving();
			}
		}, 6000);
		
		General.sleep(500, 500);
	}

	@Override
	public boolean validate() {
		return Inventory.getCount(Constants.IRON_ORE) == 0 && HelperFunctions.isNearObject(Constants.BAR_DISPENSER, 20) && !Inventory.isFull();
	}
}
