package scripts.IronSmelter.Constants;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {
	// Tiles
	public final static RSTile BANK_STAND_TILE = new RSTile(1948, 4957, 0);
	public final static RSTile SMELT_STAND_TILE = new RSTile(1942, 4967, 0);
	public final static RSTile WITHDRAW_STAND_TILE = new RSTile(1940, 4962, 0);
	public final static RSTile PAY_FOREMAN_TILE = new RSTile(1946, 4959, 0);
	
	// Areas
	public final static RSArea BANK_STAND_AREA = new RSArea(BANK_STAND_TILE, 0);
	public final static RSArea SMELT_STAND_AREA = new RSArea(SMELT_STAND_TILE, 0);
	public final static RSArea WITHDRAW_STAND_AREA = new RSArea(WITHDRAW_STAND_TILE, 0);
	public final static RSArea PAY_FOREMAN_AREA = new RSArea(PAY_FOREMAN_TILE, 3);
	
	// IDs		
	public final static int STAMINA = 12625;
	public final static int IRON_ORE = 440;
	public final static int IRON_BAR = 2351;
	public final static int COINS = 995;
	
	// Items
	public final static int[] KEEP = {12625, 12627, 12629, 12631, IRON_ORE};
	public final static int[] STAMINAS = {12625, 12627, 12629, 12631};
	
	// RSObjects
	public final static int CHEST = 26707; 
	public final static int CONVEYOR_BELT = 9100; 
	public final static int BAR_DISPENSER = 9092; 
	
	// RSInterface Masters
	public final static int DISPENSER_MASTER = 28; 
	
	// RSInterface Children
	public final static int DISPENSER_CHILD = 109; 	
	
	// Settings
	public final static int STAMINA_ACTIVE = 638;
}


