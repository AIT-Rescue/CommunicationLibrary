package comlib.adk.launcher.dummy.event;

import comlib.adk.launcher.dummy.util.CivilianManager;
import comlib.event.information.CivilianMessageEvent;
import comlib.message.information.CivilianMessage;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.StandardWorldModel;

public class AmbulanceCivilianEvent extends CivilianMessageEvent {

    private StandardWorldModel model;
    private CivilianManager civilianManager;

    public AmbulanceCivilianEvent(StandardWorldModel swm, CivilianManager sm) {
        this.model = swm;
        this.civilianManager = sm;
    }

    @Override
    public void receivedRadio(CivilianMessage msg) {
        if(msg.getBuriedness() > 0) {
            Civilian civilian = (Civilian)this.model.getEntity(msg.getHumanID());
            if (civilian == null) {
                model.addEntity(new Civilian(msg.getHumanID()));
                civilian = (Civilian) model.getEntity(msg.getHumanID());
            }
            civilian.isHPDefined();
            civilian.isBuriednessDefined();
            civilian.isDamageDefined();
            civilian.isPositionDefined();
            civilian.setHP(msg.getHP());
            civilian.setBuriedness(msg.getBuriedness());
            civilian.setDamage(msg.getDamage());
            civilian.setPosition(msg.getPosition());

            this.civilianManager.add(civilian);
        }
    }
}
