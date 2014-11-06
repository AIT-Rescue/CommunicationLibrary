package comlib.adk.team.tactics.straight.event;

import comlib.adk.team.tactics.straight.StraightAmbulance;
import comlib.event.information.AmbulanceTeamMessageEvent;
import comlib.message.information.AmbulanceTeamMessage;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.StandardWorldModel;

public class StraightAmbulanceEvent implements AmbulanceTeamMessageEvent {

    private StraightAmbulance tactics;

    public StraightAmbulanceEvent(StraightAmbulance straightAmbulance) {
        this.tactics = straightAmbulance;
    }

    @Override
    public void receivedRadio(AmbulanceTeamMessage msg) {
        AmbulanceTeam ambulanceTeam = reflectedMessage(this.tactics.model, msg);
        this.tactics.victimSelector.add(ambulanceTeam);
    }

    @Override
    public void receivedVoice(AmbulanceTeamMessage msg) {
        this.receivedRadio(msg);
    }

    public AmbulanceTeam reflectedMessage(StandardWorldModel swm, AmbulanceTeamMessage msg) {
        AmbulanceTeam ambulanceteam = (AmbulanceTeam) swm.getEntity(msg.getHumanID());
        if (ambulanceteam == null) {
            swm.addEntity(new AmbulanceTeam(msg.getHumanID()));
            ambulanceteam = (AmbulanceTeam) swm.getEntity(msg.getHumanID());
        }
        ambulanceteam.isHPDefined();
        ambulanceteam.isBuriednessDefined();
        ambulanceteam.isDamageDefined();
        ambulanceteam.isPositionDefined();
        ambulanceteam.setHP(msg.getHP());
        ambulanceteam.setBuriedness(msg.getBuriedness());
        ambulanceteam.setDamage(msg.getDamage());
        ambulanceteam.setPosition(msg.getPosition());

        return ambulanceteam;
    }
}
