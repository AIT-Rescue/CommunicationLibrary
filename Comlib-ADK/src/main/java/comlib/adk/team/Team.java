package comlib.adk.team;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.config.Config;

public abstract class Team {

    public abstract String getTeamName();

    //public abstract void readConfig(Config config);

    public abstract AmbulanceTeamTactics getAmbulanceTeamTactics();

    public abstract FireBrigadeTactics getFireBrigadeTactics();

    public abstract PoliceForceTactics getPoliceForceTactics();

    /*
    public FireStationTactics getFireStationTactics() {
        return null;
    }
    public AmbulanceCentreTactics getAmbulanceCentreTactics() {
        return null;
    }
   public PoliceOfficeTactics getPoliceOfficeTactics() {
       return null;
   }
   */
}