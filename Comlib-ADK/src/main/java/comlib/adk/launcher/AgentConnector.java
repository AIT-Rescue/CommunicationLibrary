package comlib.adk.launcher;

import comlib.adk.team.Team;
import rescuecore2.Constants;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.components.TCPComponentLauncher;
import rescuecore2.config.Config;

import java.io.File;

public class AgentConnector {
    
    public static void main(String... args) {
        AgentConnector connector = new AgentConnector(args);
        connector.start();
    }
    
    private File launchDir;
    
    private String host;
    private int port;
    
    private TeamLoader loader;
    
    private Config config;
    
    private String ambulanceName;
    private int ambulanceCount;
    
    private String fireName;
    private int fireCount;
    
    private String policeName;
    private int policeCount;
    
    public AgentConnector(String[] args) {
        this.init(args);
    }
    
    private void init(String[] args) {
        this.config = new Config();
        args = CommandLineAnalysis.analysis(args, config); //setValueの状況による
        this.launchDir = new File(config.getValue("ConfigKey.KEY_DIRECTORY", "."));
        File configDir = new File(this.launchDir, "config");
        configDir.mkdirs();
        //config read(configDir);
        this.host = config.getValue(Constants.KERNEL_HOST_NAME_KEY, Constants.DEFAULT_KERNEL_HOST_NAME);
        this.port = config.getIntValue(Constants.KERNEL_PORT_NUMBER_KEY, Constants.DEFAULT_KERNEL_PORT_NUMBER);
        File tacticsDir = new File(this.launchDir, "tactics");
        this.loader = new TeamLoader(tacticsDir);
        this.ambulanceName = this.config.getValue(ConfigKey.KEY_AMBULANCE_NAME);
        this.ambulanceCount = this.config.getIntValue(ConfigKey.KEY_AMBULANCE_COUNT);
        this.fireName = this.config.getValue(ConfigKey.KEY_FIRE_NAME);
        this.fireCount = this.config.getIntValue(ConfigKey.KEY_FIRE_COUNT);
        this.policeName = this.config.getValue(ConfigKey.KEY_POLICE_NAME);
        this.policeCount = this.config.getIntValue(ConfigKey.KEY_POLICE_COUNT);
    }
    
    public void start() {
        ComponentLauncher cl = new TCPComponentLauncher(this.host, this.port, this.config);
        this.connectAmbulance(cl);
        this.connectFire(cl);
        this.connectPolice(cl);
    }
    
    private void connectAmbulance(ComponentLauncher cl) { //throws InterruptedException, ConnectionException
        this.connectAmbulance(cl, this.ambulanceName, this.ambulanceCount);
    }
    
    private void connectAmbulance(ComponentLauncher cl, String name, int count) {
        Team team = this.loader.get(name);
        try {
            for (int i = 0; i != count; ++i) {
                cl.connect(new AmbulanceTeamAgent(team.getAmbulanceTactics()));
            }
        }
        catch (ComponentConnectionException e) {
        }
    }
    
    private void connectFire(ComponentLauncher cl) { //throws InterruptedException, ConnectionException
        this.connectAmbulance(cl, this.fireName, this.fireCount);
    }
    
    private void connectFire(ComponentLauncher cl, String name, int count) {
        Team team = this.loader.get(name);
        try {
            for (int i = 0; i != count; ++i) {
                cl.connect(new FireBrigadeAgent(team.getFireTactics()));
            }
        }
        catch (ComponentConnectionException e) {
        }
    }
    
    private void connectPolice(ComponentLauncher cl) { //throws InterruptedException, ConnectionException
        this.connectAmbulance(cl, this.policeName, this.policeCount);
    }
    
    private void connectPolice(ComponentLauncher cl, String name, int count) {
        Team team = this.loader.get(name);
        try {
            for (int i = 0; i != count; ++i) {
                cl.connect(new PoliceForceAgent(team.getPoliceTactics()));
            }
        }
        catch (ComponentConnectionException e) {
        }
    }
}