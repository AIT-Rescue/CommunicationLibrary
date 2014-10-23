package comlib.creator.information;

import comlib.creator.HumanMessageCreator;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.message.information.VictimMessage;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.worldmodel.EntityID;

public class VictimMessageCreator extends HumanMessageCreator<VictimMessage>
{
	//protected MessageEvent event;
	
	public VictimMessageCreator(){}
	
	/*public VcitimMessageCreator(MessageEvent messageEvent)
	{
		//this.event = messageEvent;
	}*/
	
	@Override
	public CommunicationMessage createMessage(RadioConfig config, int time, BitStreamReader bsr)
	{
		EntityID e = new EntityID(bsr.readBits(config.getSizeOfEntityID(Civilian.class)));
		int h = bsr.readBits(config.getSizeOfHumanHP());
		int b = bsr.readBits(config.getSizeOfHumanBuriedness());
		int d = bsr.readBits(config.getSizeOfHumanDamage());
		EntityID p = new EntityID(bsr.readBits(config.getSizeOfPosition()));
		
		VictimMessage msg = new VictimMessage(time, e, h, b, d, p);
		//this.event.receive(msg);
		return msg;
	}
	
	@Override
	public CommunicationMessage createMessage(VoiceConfig config, int time, int ttl, String[] datas)
	{
		EntityID e = new EntityID(Integer.parseInt(datas[2]));
		int      h = Integer.parseInt(datas[3]);
		int      b = Integer.parseInt(datas[4]);
		int      d = Integer.parseInt(datas[5]);
		EntityID p = new EntityID(Integer.parseInt(datas[6]));
		
		VictimMessage msg = new VictimMessage(time, ttl, e, h, b, d, p);
		//this.event.receive(msg);
		return msg;
	}
	
	@Override
	public void addData(RadioConfig config, BitOutputStream bos, VictimMessage msg)
	{
		this.addHumanData(config, bos, msg);
	}
	
	@Override
	public void addData(VoiceConfig config, StringBuilder builder, VictimMessage msg)
	{
		this.addHumanData(config, builder, msg);
	}
	
	/*public VicitmMessage copy(VictimMessage msg)
	{
		return msg;
	}*/
}