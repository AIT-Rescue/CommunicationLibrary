package comlib.adk.agent;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class AmbulanceTeamAgent extends TacticsAgent<AmbulanceTeamTactics, AmbulanceTeam> {

    //public AmbulanceTeamTactics tactics;

    public AmbulanceTeamAgent(AmbulanceTeamTactics ambulanceTactics) {
        super(ambulanceTactics);
        //this.tactics = ambulanceTactics;
    }

    @Override
    public String toString() {
        return "Comlib Ambulance Team";
    }

    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.AMBULANCE_TEAM);
    }

    @Override
    public void initAgentValue(AmbulanceTeamTactics ambulanceTeamTactics) {
        ambulanceTeamTactics.refugeList = this.getRefuges();
    }
}
