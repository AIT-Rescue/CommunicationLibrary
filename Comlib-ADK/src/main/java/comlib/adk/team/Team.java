package comlib.adk.team;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.config.Config;

public interface Team {
    public String getTeamName();
    public void readConfig(Config config);
    public AmbulanceTeamTactics getAmbulanceTeamTactics();
    public FireBrigadeTactics getFireBrigadeTactics();
    public PoliceForceTactics getPoliceForceTactics();
}