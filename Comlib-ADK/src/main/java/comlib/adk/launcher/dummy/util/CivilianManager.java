package comlib.adk.launcher.dummy.util;

import comlib.manager.MessageManager;
import comlib.message.information.CivilianMessage;
import rescuecore2.standard.entities.Civilian;
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

    public void update(Civilian civilian, MessageManager manager) {
        if (!this.containsKey(civilian.getID()) && civilian.getBuriedness() > 0) {
            this.add(civilian);
            manager.addSendMessage(new CivilianMessage(civilian));
        }
    }
}
