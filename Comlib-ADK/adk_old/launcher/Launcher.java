package comlib.adk.launcher;

import java.io.File;

import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;

import comlib.adk.launcher.TeamLoader;
import comlib.adk.launcher.Tactics;

public class Launcher
{
	
	public static void main(String... args)
	{
		Launcher launcher = new Launcher();
		launcher.start();
	}
	
	private File launchDir;
	
	private String host;
	private int port;
	
	private TeamLoader loader;
	
	private String ambulanceName;
	private int ambulanceCount;
	
	private String fireName;
	private int fireCount;
	
	private String policeName;
	private int pliceCount;
	
	//[ambulance, fire, police]
	//private String agentNames = new String[3];
	//private int agentCounts   = new int[3];
	
	public Launcher(String[] args)
	{
		Config config = new Config();
	    args = CommandLineAnalysis.analysis(args, config);
	    
		
	}
	
	private void connect()
	{
		
		for(int i = 0; i != ambulanceCount; i++)
		{
			Team teamAmbulance = "random".equals(ambulanceName) ? this.loader.getTeam(ambulanceName) : this.loader.getRandomTeam();
		if(teamAmbulance != null)
			launcher.connect(c)
		}
		Team teamFire = "random".equals(fireName) ? this.loader.getTeam(fireName) : this.loader.getRandomTeam();
		Team teamPolice = "random".equals(policeName) ? this.loader.getTeam(policeName) : this.loader.getRandomTeam();
	}
}