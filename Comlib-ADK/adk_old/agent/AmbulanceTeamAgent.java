package comlib.adk.agent;

public class AmbulanceTeamAgent extends TacticsAgent<AmbulanceTeam> {
    
    @Override
    public String toString() {
        return "AmbulanceTeam" + this.tactics.getTacticsName();
    }
    
    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.AMBULANCE_TEAM);
    }
}