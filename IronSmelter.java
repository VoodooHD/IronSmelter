package scripts.IronSmelter;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.IronSmelter.api.Node;
import scripts.IronSmelter.Nodes.Bank;
import scripts.IronSmelter.Nodes.PayFee;
import scripts.IronSmelter.Nodes.PutOresOnBelt;
import scripts.IronSmelter.Nodes.WalkToBank;
import scripts.IronSmelter.Nodes.WalkToBelt;
import scripts.IronSmelter.Nodes.WalkToDispenser;
import scripts.IronSmelter.Nodes.WithdrawBars;

@ScriptManifest(authors = { "Voodoo" }, category = "Smithing", name = "Voodoo's IronSmelter", version = 1.01, description = "Smelts iron bars at blast furance.")
public class IronSmelter extends Script implements Painting {
	public static ArrayList<Node> nodes = new ArrayList<>();
	
	public static boolean run = true;
	public static String status = "Starting...";
	public static int bars = 0;
	public static long feeTimer = System.currentTimeMillis();
	public static final long startTime = System.currentTimeMillis();

	@Override
	public void run() {		
		Collections.addAll(nodes, new WalkToBank(), new PayFee(), new Bank(), new WalkToBelt(), 
				new PutOresOnBelt(), new WalkToDispenser(), new WithdrawBars());
		
		loop(50,50);
	}
	
	private void loop(int min, int max) {
		Mouse.setSpeed(General.random(88, 108));
		AntiBan.setPrintDebug(true);
		General.useAntiBanCompliance(true);
		ThreadSettings.get().setClickingAPIUseDynamic(true);
		
		while (run) {
			if (Login.getLoginState() != STATE.LOGINSCREEN) {
				for (final Node node : nodes) {
					if (node.validate()) {
						node.execute();
					} else {
			            AntiBan.timedActions();
					}
					sleep(General.random(min, max));
				}
			}
			sleep(General.random(min, max));
		}
	}

	@Override
	public void onPaint(Graphics g) {
		long runTime = System.currentTimeMillis() - startTime;
		int barsPerHour = (int) (bars * 3600000d / runTime);	
		
		Font font = new Font("Verdana", Font.BOLD, 14);
		
		g.setFont(font);
		g.drawString("Script: Voodoo's IronSmelter", 5, 260);
		g.drawString("Status: " + status, 5, 280);
		g.drawString("Running for: " + Timing.msToString(runTime), 5, 300);
		g.drawString("Ores smelted : " + bars + " (" + barsPerHour + "/hr)", 5, 320);
	}
}
