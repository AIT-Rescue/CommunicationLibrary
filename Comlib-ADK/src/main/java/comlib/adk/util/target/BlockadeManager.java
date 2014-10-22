package comlib.adk.util.target;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockadeManager extends TargetManager{

    public List<EntityID> blockadeList;

    public BlockadeManager(Tactics user) {
        super(user);
        this.blockadeList = new ArrayList<>();
    }

    public abstract void add(Blockade blockade);

    public abstract void remove(Blockade blockade);
}
