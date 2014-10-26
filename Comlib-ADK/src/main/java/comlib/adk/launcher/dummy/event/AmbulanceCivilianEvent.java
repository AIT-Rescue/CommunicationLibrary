package comlib.adk.launcher.dummy.event;

//import comlib.adk.util.ambulance.CivilianManager;

import comlib.adk.util.target.VictimManager;
import comlib.event.information.CivilianMessageEvent;
import comlib.message.information.CivilianMessage;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.StandardWorldModel;

public class AmbulanceCivilianEvent implements CivilianMessageEvent {

    private StandardWorldModel model;
    private VictimManager victimManager;

    public AmbulanceCivilianEvent(StandardWorldModel swm, VictimManager sm) {
        this.model = swm;
        this.victimManager = sm;
    }

    @Override
    public void receivedRadio(CivilianMessage msg) {
        if(msg.getBuriedness() > 0) {
            Civilian civilian = reflectedMessage(this.model, msg);
            this.victimManager.add(civilian);
        }
    }

    @Override
    public void receivedVoice(CivilianMessage msg) {
        this.receivedRadio(msg);
    }

    public Civilian reflectedMessage(StandardWorldModel swm, CivilianMessage msg) {
        Civilian civilian = (Civilian)swm.getEntity(msg.getHumanID());
        if (civilian == null) {
            swm.addEntity(new Civilian(msg.getHumanID()));
            civilian = (Civilian) swm.getEntity(msg.getHumanID());
        }
        civilian.isHPDefined();
        civilian.isBuriednessDefined();
        civilian.isDamageDefined();
        civilian.isPositionDefined();
        civilian.setHP(msg.getHP());
        civilian.setBuriedness(msg.getBuriedness());
        civilian.setDamage(msg.getDamage());
        civilian.setPosition(msg.getPosition());

        return civilian;
    }
}
