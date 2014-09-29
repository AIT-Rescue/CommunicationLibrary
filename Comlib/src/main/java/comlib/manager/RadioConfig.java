package comlib.manager;

import rescuecore2.config.Config;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.GasStation;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.Road;

import comlib.util.IntegerDataHelper;

public final class RadioConfig
{
	private int channel;
	
	private int sizeOfMessageID = 4;
	
	private int sizeOfTime;
	
	private RadioConfig() {}
	
	public RadioConfig(Config config)
	{
		this.channel    = config.getIntValue("comlib.default.channel", 1);
		this.sizeOfTime = config.getIntValue("comlib.size.time", 9);
		this.updateSize(config.getIntValue("comlib.default.messageID", 16) - 1);
	}
	
	public int getChannel()
	{
		return this.channel;
	}
	
	public int getSizeOfMessageID()
	{
		return this.sizeOfMessageID;
	}

	public void updateSize(int id)
	{
		int size = IntegerDataHelper.getBitSize(id);
		if(size > this.sizeOfMessageID)
		{
			this.sizeOfMessageID = size;
		}
	}
	
	public int getSizeOfTime()
	{
		return this.sizeOfTime;
	}
	
	public int getSizeOfEntityID(Class<E extends AmbulanceTeam> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends Blockade> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends Building> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends Civilian> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends FireBrigade> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends GasStation> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends PoliceForce> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends Refuge> c)
	{
		return 32;
	}
	
	public int getSizeOfEntityID(Class<E extends Road> c)
	{
		return 32;
	}
}