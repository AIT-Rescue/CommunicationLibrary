package comlib.adk.dummy.util;

import comlib.manager.MessageManager;
import comlib.message.information.CivilianMessage;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CivilianManager {

    private Set<Civilian> civilianList;
    private Map<EntityID, Civilian> civilianMap;

    public CivilianManager() {
        this.civilianList = new HashSet<>();
        this.civilianMap = new HashMap<>();
    }

    public void add(Civilian civilian) {
        this.civilianList.add(civilian);
        this.civilianMap.put(civilian.getID(), civilian);
    }

    public Civilian get(EntityID id) {
        return this.civilianMap.get(id);
    }

    public void remove(Civilian civilian) {
        this.civilianList.remove(civilian);
        this.civilianMap.remove(civilian.getID());
    }

    public Set<Civilian> getCivilianList() {
        return civilianList;
    }

    public boolean containsKey(EntityID id) {
        return this.civilianMap.containsKey(id);
    }

    public void update(StandardWorldModel model, ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                Civilian c = (Civilian)entity;
                if (!this.containsKey(next) && c.getBuriedness() > 0) {
                    this.add(c);
                    manager.addSendMessage(new CivilianMessage(c));
                }
            }
        }
    }
}
