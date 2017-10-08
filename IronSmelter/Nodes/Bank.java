package scripts.IronSmelter.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;
import scripts.IronSmelter.HelperFunctions.HelperFunctions;
import scripts.IronSmelter.api.Node;

public class Bank extends Node{

	@Override
	public void execute() {
		while (!Banking.isBankScreenOpen()) {
			Banking.openBank();
			IronSmelter.status = "Banking";
			
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Banking.isBankScreenOpen();
				}
			}, 4000);
		}
		
		if (Banking.isPinScreenOpen()) {
			General.sleep(1000, 1000);
			Banking.inPin();
		}
				
		if (Inventory.find(Constants.KEEP).length > 0) {
			Banking.depositAllExcept(Constants.KEEP);			 
		} 
		
		if (Inventory.find(Constants.KEEP).length == 0) {
			Banking.depositAll();
			
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return !Inventory.isFull();
				}
			}, 4000);
			
			if (Banking.find(Constants.STAMINA).length > 0) {
				Banking.withdraw(1, Constants.STAMINA);
				
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Inventory.getCount(Constants.STAMINA) > 0;
					}
				},1000);
			}
		}
		
		General.sleep(500, 500);
		
		while (Inventory.getCount(Constants.IRON_ORE) == 0)
			if (Banking.find(Constants.IRON_ORE).length > 0) {
				Banking.withdraw(0, Constants.IRON_ORE);
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Inventory.getCount(Constants.IRON_BAR) > 0;
					}
				}, 1000);
			} else {
				IronSmelter.run = false;
				break;
		}
		
		Banking.close();
	}

	@Override
	public boolean validate() {
		return (Inventory.getCount(Constants.IRON_BAR) > 0 || Inventory.getCount(Constants.IRON_ORE) == 0)
			&& HelperFunctions.isNearObject(Constants.CHEST, 10);
	}
}
