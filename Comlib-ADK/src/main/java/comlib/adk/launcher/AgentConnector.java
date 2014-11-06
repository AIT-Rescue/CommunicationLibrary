package comlib.adk.launcher;

import sample.SampleAmbulanceTeam;
import java.io.*;

import comlib.adk.agent.AmbulanceTeamAgent;
import comlib.adk.agent.FireBrigadeAgent;
import comlib.adk.agent.PoliceForceAgent;
import comlib.adk.team.Team;
import rescuecore2.Constants;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.components.TCPComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

import java.io.File;

public class AgentConnector {

	private Config config;

	private TeamLoader loader;

	public AgentConnector(String[] args) {
		this.init(args);
	}

	private void init(String[] args) {
		this.config = ConfigInitializer.getConfig(args);
		System.out.println("Load Team");
		this.loader = new TeamLoader(new File(config.getValue(ConfigKey.KEY_DIRECTORY, "."), "tactics"), config);
	}

	public void start() {
		String host = config.getValue(Constants.KERNEL_HOST_NAME_KEY, Constants.DEFAULT_KERNEL_HOST_NAME);
		int port = config.getIntValue(Constants.KERNEL_PORT_NUMBER_KEY, Constants.DEFAULT_KERNEL_PORT_NUMBER);
		ComponentLauncher cl = new TCPComponentLauncher(host, port, this.config);
		System.out.println("Start Connect (Server Info : " + host + ":" + port + ")");

		this.connectAmbulance(cl);
		this.connectFire(cl);
		this.connectPolice(cl);
	}

	private void connectAmbulance(ComponentLauncher cl) {
		this.connectAmbulance(cl, this.config.getValue(ConfigKey.KEY_AMBULANCE_NAME), this.config.getIntValue(ConfigKey.KEY_AMBULANCE_COUNT));
	}

	private void connectAmbulance(ComponentLauncher cl, String name, int count) {
		Team team = this.loader.get(name);
		int limit = 0;
		while(team.getAmbulanceTeamTactics() == null) {
			if(limit == 10) {
				team = this.loader.getDummy();
			}
			else {
				team = this.loader.getRandomTeam();
				limit++;
			}
		}
		name = team.getTeamName();
		try {
			for (int i = 0; i != count; ++i) {
				System.out.println("Connect Ambulance Team (Team Name : " + name + ")");
				AmbulanceTeamAgent agent = new AmbulanceTeamAgent(team.getAmbulanceTeamTactics());
				agent.initialise();
				cl.connect(agent);
			}
		} catch (ComponentConnectionException | InterruptedException | ConnectionException ignored) {
		}
	}

	private void connectFire(ComponentLauncher cl) {
		this.connectFire(cl, this.config.getValue(ConfigKey.KEY_FIRE_NAME), this.config.getIntValue(ConfigKey.KEY_FIRE_COUNT));
	}

	private void connectFire(ComponentLauncher cl, String name, int count) {
		Team team = this.loader.get(name);
		int limit = 0;
		while(team.getFireBrigadeTactics() == null) {
			if(limit == 10) {
				team = this.loader.getDummy();
			}
			else {
				team = this.loader.getRandomTeam();
				limit++;
			}
		}
		name = team.getTeamName();
		try {
			for (int i = 0; i != count; ++i) {
				System.out.println("Connect Fire Brigade   (Team Name : " + name + ")");
				cl.connect(new FireBrigadeAgent(team.getFireBrigadeTactics()));
			}
		} catch (ComponentConnectionException | InterruptedException | ConnectionException ignored) {
		}
	}

	private void connectPolice(ComponentLauncher cl) {
		this.connectPolice(cl, this.config.getValue(ConfigKey.KEY_POLICE_NAME), this.config.getIntValue(ConfigKey.KEY_POLICE_COUNT));
	}

	private void connectPolice(ComponentLauncher cl, String name, int count) {
		Team team = this.loader.get(name);
		int limit = 0;
		while(team.getPoliceForceTactics() == null) {
			if(limit == 10) {
				team = this.loader.getDummy();
			}
			else {
				team = this.loader.getRandomTeam();
				limit++;
			}
		}
		name = team.getTeamName();
		try {
			for (int i = 0; i != count; ++i) {
				System.out.println("Connect Police Force   (Team Name : " + name + ")");
				cl.connect(new PoliceForceAgent(team.getPoliceForceTactics()));
			}
		} catch (ComponentConnectionException | InterruptedException | ConnectionException ignored) {
		}

	}
}
