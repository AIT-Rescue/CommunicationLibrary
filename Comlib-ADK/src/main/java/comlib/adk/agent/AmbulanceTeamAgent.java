package comlib.adk.agent;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class AmbulanceTeamAgent extends TacticsAgent<AmbulanceTeamTactics, AmbulanceTeam> {
    
    public AmbulanceTeamTactics tactics;
    
    public AmbulanceTeamAgent(AmbulanceTeamTactics ambulanceTactics) {
        super(ambulanceTactics);
    }
    
    @Override
    public String toString() {
        return "Comlib Ambulance Team";
    }
    
    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.AMBULANCE_TEAM);
    }
    
    public void setAgentEntity(AmbulanceTeamTactics ambulanceTeamTactics) {
        ambulanceTeamTactics.me = this.me();
    }
    
    public void setAgentUniqueValue(AmbulanceTeamTactics ambulanceTeamTactics) {
        ambulanceTeamTactics.model.indexClass(
                StandardEntityURN.CIVILIAN,
                StandardEntityURN.FIRE_BRIGADE,
                StandardEntityURN.POLICE_FORCE,
                StandardEntityURN.AMBULANCE_TEAM,
                StandardEntityURN.REFUGE,
                StandardEntityURN.HYDRANT,
                StandardEntityURN.GAS_STATION,
                StandardEntityURN.BUILDING
        );
        ambulanceTeamTactics.refugeList = this.getRefuges();
    }
}