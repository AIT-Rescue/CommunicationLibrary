package comlib.agent;

import comlib.manager.MessageManager;
import comlib.message.CommunicationMessage;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.components.StandardAgent;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

import java.util.Collection;
import java.util.List;

public abstract class CommunicationAgent<E extends StandardEntity> extends StandardAgent<E>
{
	protected MessageManager manager;
	
	public CommunicationAgent()
	{
		super();
	}
	
	public abstract MessageEvent getMessageEvent();
	
	public abstract void think(int time, ChangeSet changed);
	
	public void sendSpeak(CommunicationMessage msg)
	{
		this.manager.addMessage(msg);
	}
	
	@Override
	public void postConnect()
	{
		super.postConnect();
		this.manager = new MessageManager(this.config, this.getMessageEvent());
	}
	
	@Override
	protected final void think(int time, ChangeSet changed, Collection<Command> heard)
	{
		this.manager.receiveMessage(time, heard);
		
		this.think(time, changed);
		
		//this.sendMessage(time);
		this.send(this.manager.createSendMessage());
	}
	
	public void send(Message[] msgs)
	{
		for(Message msg : msgs)
			this.send(msg);
	}
	
	public void send(List<Message> msgs)
	{
		for(Message msg : msgs)
			this.send(msg);
	}
	
	@Override
    protected void sendSpeak(int time, int channel, byte[] data)
	{
		//super.sendSpeak(time, channel, data);
	}
	
	@Override
    protected void sendSay(int time, byte[] data)
	{
		//super.sendSay(time, data);
	}
}