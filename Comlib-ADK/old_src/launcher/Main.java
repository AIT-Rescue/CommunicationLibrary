package comlib.adk.launcher;

public class Main {
    public static void main(String... args) {
        AgentConnector connector = new AgentConnector(args);
        connector.start();
    }
}