package comlib.creator;

import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.message.HumanMessage;



public abstract class HumanMessageCreator<M extends HumanMessage> extends MessageCreator<M>
{
	public HumanMessageCreator()
	{
		super();
	}
	
	public void addHumanData(RadioConfig config, BitOutputStream bos, M msg)
	{
		bos.writeBits(msg.getID(), config.getSizeOfEntityID(msg.getHumanClass()));
		bos.writeBits(msg.getHP(), config.getSizeOfHumanHP());
		bos.writeBits(msg.getBuriedness(), config.getSizeOfHumanBuriedness());
		bos.writeBits(msg.getDamage(), config.getSizeOfHumanDamage());
		bos.writeBits(msg.getPosition(), config.getSizeOfPosition());
	}
	
	public void addHumanData(VoiceConfig config, StringBuilder builder, M msg)
	{
		config.appendData(builder, msg.getID().getValue());
		config.appendData(builder, msg.getHP());
		config.appendData(builder, msg.getBuriedness());
		config.appendData(builder, msg.getDamage());
		config.appendData(builder, msg.getPosition().getValue());
	}
}