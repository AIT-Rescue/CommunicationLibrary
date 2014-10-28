package comlib.adk.team.tactics.straight.event;

import comlib.adk.team.tactics.straight.StraightAmbulance;
import comlib.event.information.FireBrigadeMessageEvent;
import comlib.message.information.FireBrigadeMessage;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardWorldModel;

public class StraightFireEvent implements FireBrigadeMessageEvent{

    private StraightAmbulance tactics;

    public StraightFireEvent(StraightAmbulance straightAmbulance) {
        this.tactics = straightAmbulance;
    }

    @Override
    public void receivedRadio(FireBrigadeMessage msg) {
        FireBrigade fireBrigade = this.reflectedMessage(this.tactics.model, msg);
        this.tactics.victimSelector.add(fireBrigade);
    }

    @Override
    public void receivedVoice(FireBrigadeMessage msg) {
        this.receivedRadio(msg);
    }

    public FireBrigade reflectedMessage(StandardWorldModel swm, FireBrigadeMessage msg) {
        FireBrigade firebrigade = (FireBrigade) swm.getEntity(msg.getHumanID());
        if (firebrigade == null) {
            swm.addEntity(new FireBrigade(msg.getHumanID()));
            firebrigade = (FireBrigade) swm.getEntity(msg.getHumanID());
        }
        firebrigade.isHPDefined();
        firebrigade.isBuriednessDefined();
        firebrigade.isDamageDefined();
        firebrigade.isPositionDefined();
        firebrigade.setHP(msg.getHP());
        firebrigade.setBuriedness(msg.getBuriedness());
        firebrigade.setDamage(msg.getDamage());
        firebrigade.setPosition(msg.getPosition());
        firebrigade.setWater(msg.getWater());
        return firebrigade;
    }
}
