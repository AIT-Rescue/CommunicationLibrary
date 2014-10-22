package comlib.adk.util.target;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.List;

public abstract class VictimManager extends TargetManager{
    //survivor in need of help

    public List<EntityID> civilianList;
    public List<EntityID> agentList;

    public VictimManager(Tactics user) {
        super(user);
        this.civilianList = new ArrayList<>();
        this.agentList = new ArrayList<>();
    }

    public abstract void add(Civilian civilian);
    public abstract void add(AmbulanceTeam agent);
    public abstract void add(FireBrigade agent);
    public abstract void add(PoliceForce agent);

    public abstract void remove(Civilian civilian);
    public abstract void remove(AmbulanceTeam agent);
    public abstract void remove(FireBrigade agent);
    public abstract void remove(PoliceForce agent);
}
