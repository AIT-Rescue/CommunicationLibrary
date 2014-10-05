package comlib.adk.launcher;

import java.io.File;

import comlib.adk.launcher.TeamLoader;
import comlib.adk.launcher.

public class Launcher
{
	private static Launcher instance;
	//no args -> readConfig("Launcher.cfg")
	//"-h "ip" -p "port" -> readConfig("Launcher.cfg")
	//-a "Ambulance" "number" -f "Fire" "number" -p "Police" "number" 
	public static void main(String... args)
	{
		instance = new Launcher(args);
		instance.start();
	}
	
	private String host;
	private int port;
	
	private  File baseDir;
	
	private TeamLoader loader;
	
	private String teamNameAmbulance;
	private int connectAmbulance;
	
	private String teamNameFire;
	private int connectFire;
	
	private String teamNamePolice;
	private int connectPolice;
	
	public Launcher(String... dir)
	{
		
	}
	
	private static void connect(ComponentLauncher launcher, String argLine, boolean gui) throws InterruptedException, ConnectionException
	{
		
	}
}