package comlib.adk.team.tactics.straight.event;

import comlib.adk.team.tactics.straight.StraightAmbulance;
import comlib.event.information.PoliceForceMessageEvent;
import comlib.message.information.PoliceForceMessage;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardWorldModel;

public class StraightPoliceEvent implements PoliceForceMessageEvent{

    private StraightAmbulance tactics;

    public StraightPoliceEvent(StraightAmbulance straightAmbulance) {
        this.tactics = straightAmbulance;
    }

    @Override
    public void receivedRadio(PoliceForceMessage msg) {
        PoliceForce policeForce = this.reflectedMessage(this.tactics.model, msg);
        this.tactics.victimSelector.add(policeForce);
    }

    @Override
    public void receivedVoice(PoliceForceMessage msg) {
        this.receivedRadio(msg);
    }

    public PoliceForce reflectedMessage(StandardWorldModel swm, PoliceForceMessage msg) {
        PoliceForce policeforce = (PoliceForce) swm.getEntity(msg.getHumanID());
        if (policeforce == null) {
            swm.addEntity(new PoliceForce(msg.getHumanID()));
            policeforce = (PoliceForce) swm.getEntity(msg.getHumanID());
        }
        policeforce.isHPDefined();
        policeforce.isBuriednessDefined();
        policeforce.isDamageDefined();
        policeforce.isPositionDefined();
        policeforce.setHP(msg.getHP());
        policeforce.setBuriedness(msg.getBuriedness());
        policeforce.setDamage(msg.getDamage());
        policeforce.setPosition(msg.getPosition());

        return policeforce;
    }
}
