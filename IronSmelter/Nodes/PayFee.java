package scripts.IronSmelter.Nodes;

import java.util.*;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Skills;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;

import scripts.IronSmelter.AntiBan;
import scripts.IronSmelter.IronSmelter;
import scripts.IronSmelter.Constants.Constants;
import scripts.IronSmelter.HelperFunctions.HelperFunctions;
import scripts.IronSmelter.api.Node;

public class PayFee extends Node{
	
	List<Integer> abc2WaitTimes = new ArrayList<>();

	@Override
	public void execute() {
		if (!Banking.isBankScreenOpen()) {
			Banking.openBank();
			IronSmelter.status = "Opening bank";
			
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
		
		if (Banking.find(Constants.COINS).length > 0) {
			if (Inventory.find(Constants.KEEP).length > 0) {
				Banking.depositAllExcept(Constants.KEEP);
			} else {
				Banking.depositAll();
				
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return !Inventory.isFull();
					}
				}, 4000);
			}
			
			Banking.withdraw(2500, Constants.COINS);
			
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Inventory.getCount(Constants.COINS) > 0;
				}
			}, 1000);
			
			RSNPC[] foreman = NPCs.find("Blast Furnace Foreman");
			if (foreman.length > 0) {
				if (abc2WaitTimes.isEmpty()) {
					AntiBan.generateTrackers(General.random(800, 1200), false);
				}
				if (!abc2WaitTimes.isEmpty()) {
					AntiBan.generateTrackers(HelperFunctions.calculateAverage(abc2WaitTimes), false);
				}
				int reactionTime = AntiBan.getReactionTime();
				abc2WaitTimes.add(reactionTime);
				AntiBan.sleepReactionTime();
				final RSNPC target = AntiBan.selectNextTarget(foreman);
				if (!target.isOnScreen())
					Camera.turnToTile(target);
				if (!PathFinding.canReach(target, true)) {
					if (WebWalking.walkTo(target)) {
						AntiBan.generateTrackers(HelperFunctions.calculateAverage(abc2WaitTimes), false);
						abc2WaitTimes.add(AntiBan.getReactionTime());
						General.println("Sleeping for " + AntiBan.getReactionTime());
						AntiBan.sleepReactionTime();
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return PathFinding.canReach(target, true);
							}
						}, General.random(2500, 3700));
					}
				}
				
				if (Clicking.click("Pay", target)) {
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return Inventory.getCount(Constants.COINS) < 2500;
						}
					}, General.random(2500, 3700));
				}
			}
		}
	}

	@Override
	public boolean validate() {
		return HelperFunctions.isFeeReady() && Banking.isBankScreenOpen() && Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 60
				&& HelperFunctions.isNearObject(Constants.CHEST, 10);
	}

}
