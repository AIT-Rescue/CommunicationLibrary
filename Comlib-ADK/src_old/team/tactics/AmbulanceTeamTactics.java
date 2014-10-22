package comlib.adk.team.tactics;

import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Refuge;

import java.util.List;

public abstract class AmbulanceTeamTactics extends Tactics<AmbulanceTeam>{

    public AmbulanceTeam me;

    public List<Refuge> refugeList;
}
