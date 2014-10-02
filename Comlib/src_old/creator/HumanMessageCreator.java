package comlib.creator;

import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.HumanMessage;
import comlib.util.BitOutputStream;


public abstract class HumanMessageCreator<M extends HumanMessage> extends MessageCreator<M>
{
	public HumanMessageCreator()
	{
		super();
	}
	
	public void addHumanData(RadioConfig config, BitOutputStream bos, M msg)
	{
		bos.writeBits(msg.getID().getValue(), config.getSizeOfEntityID(msg.getHumanClass()));
		bos.writeBits(msg.getHP(), config.getSizeOfHumanHP());
		bos.writeBits(msg.getBuriedness(), config.getSizeOfHumanBuriedness());
		bos.writeBits(msg.getDamage(), config.getSizeOfHumanDamage());
		bos.writeBits(msg.getPosition().getValue(), config.getSizeOfPosition());
	}
	
	public void addHumanData(VoiceConfig config, StringBuilder builder, M msg)
	{
		config.appendData(builder, String.valueOf(msg.getID().getValue()));
		config.appendData(builder, String.valueOf(msg.getHP()));
		config.appendData(builder, String.valueOf(msg.getBuriedness()));
		config.appendData(builder, String.valueOf(msg.getDamage()));
		config.appendData(builder, String.valueOf(msg.getPosition().getValue()));
	}
}