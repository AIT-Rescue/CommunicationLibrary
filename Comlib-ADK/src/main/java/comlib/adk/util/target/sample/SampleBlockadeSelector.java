package comlib.adk.util.target.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteUtil;
import comlib.adk.util.target.BlockadeSelector;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SampleBlockadeSelector extends BlockadeSelector {

    public Set<EntityID> blockadeList;

    public SampleBlockadeSelector(Tactics user) {
        super(user);
        this.blockadeList = new HashSet<>();
    }

    @Override
    public void add(Blockade blockade) {
        this.blockadeList.add(blockade.getID());
    }

    @Override
    public void remove(Blockade blockade) {
        this.blockadeList.remove(blockade.getID());
    }

    @Override
    public EntityID getTarget(int time) {
        EntityID result = null;
        int minDistance = Integer.MAX_VALUE;
        for (EntityID id : this.blockadeList) {
            int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
            if (minDistance >= d) {
                minDistance = d;
                result = id;
            }
        }
        return result;
    }
}
