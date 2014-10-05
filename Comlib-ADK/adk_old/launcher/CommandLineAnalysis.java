package comlib.adk.launcher;

import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;
import rescuecore2.Constants;

import java.util.List;
import java.util.ArrayList;

public final class CommandLineAnalysis {
    /**
     * base dir .
     * <p>
     * -d:path
     * </p>
     */
    public static final String FLAG_DIRECTORY = "-d";
    
    /**
     * connect server information (host & port) .
     * <p>
     * -s:host:port
     * </p>
     */
    public static final String FLAG_SERVER = "-s";
    
    /**
     * connect server information (host) .
     * <p>
     * -h:host
     * </p>
     */
    public static final String FLAG_HOST = "-h";
    
    /**
     * connect server information (port) .
     * <p>
     * -p:port
     * </p>
     */
    public static final String FLAG_PORT = "-p";
    
    /**
     * launch team name (all agent) .
     * <p>
     * -a:name:ambulanceCount:fireCount:policeCount
     * </p>
     */
    public static final String FLAG_TEAM = "-t";
    
    /**
     * launch team name or launch agent name (ambulance team) .
     * <p>
     * -aa:name:count
     * </p>
     */
    public static final String FLAG_AGENT_AMBULANCE = "-at";
    
    /**
     * launch team name or launch agent name (fire brigade) .
     * <p>
     * -af:name:count
     * </p>
     */
    public static final String FLAG_AGENT_FIRE = "-fb";
    
    /**
     * launch team name or launch agent name (police force) .
     * <p>
     * -ap:name:count
     * </p>
     */
    public static final String FLAFG_AGENT_POLICE ="-pf";
    
    // (server)-s:ip:port -s:port -s:ip (ambulance)-a:name:count (fire)-f:name:count (police)-p:name:count
    
    public static String[] analysis(String[] args, Config config) {
        List<String> unknown = new ArrayList<>();
        for(String option : args) {
            String[] strArray = option.split(":");
            switch (sttrArray[0]) {
                case FLAG_DIRECTORY:
                    if (strArray.length == 2) {
                        config.setValue(ConfigKey.KEY_DIRECTORY, strArray[1]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_SERVER:
                    if (strArray.length == 3) {
                        config.setValue(Constants.KERNEL_HOST_NAME_KEY, strArray[1]);
                        config.setValue(Constants.KERNEL_PORT_NUMBBER_KEY, strArray[2]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_HOST:
                    if (strArray.length == 2) {
                        config.setValue(Constants.KERNEL_HOST_NAME_KEY, strArray[1]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_PORT:
                    if (strArray.length == 2) {
                        config.setValue(Constants.KERNEL_PORT_NUMBER_KEY, strArray[1]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_TEAM:
                    if(strArray.length == 5) {
                        config.setValue(ConfigKey.KEY_AMBULANCE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_AMBULANCE_COUNT, strArray[2]);
                        config.setValue(ConfigKey.KEY_FIRE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_FIRE_COUNT, strArray[3]);
                        config.setValue(ConfigKey.KEY_POLICE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_POLICE_COUNT, strArray[4]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_AGENT_AMBULANCE:
                    if(strArray.length == 3) {
                        config.setValue(ConfigKey.KEY_AMBULANCE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_AMBULANCE_COUNT, strArray[2]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_AGENT_FIRE:
                    if(strArray.length == 3) {
                        config.setValue(ConfigKey.KEY_FIRE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_FIRE_COUNT, strArray[2]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                case FLAG_AGENT_POLICE:
                    if(strArray.length == 3) {
                        config.setValue(ConfigKey.KEY_POLICE_NAME, strArray[1]);
                        config.setValue(ConfigKey.KEY_POLICE_COUNT, strArray[2]);
                    }
                    else {
                        unknown.add(option);
                    }
                    break;
                default:
                    unknown.add(option);
            }
        }
        return unknown.toArray(new String[0]);
    }
}
