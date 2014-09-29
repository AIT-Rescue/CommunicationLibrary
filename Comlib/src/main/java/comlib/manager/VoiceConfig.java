package comlib.manager;

import rescuecore2.config.Config;

public class VoiceConfig
{
	private int ttl;
	
	private String voiceSeparator;
	
	private String dataSeparator;

    public VoiceConfig(Config config)
	{
		this.ttl            = config.getIntValue("comlib.default.ttl", 10);
		this.voiceSeparator = config.getValue("comlib.default.voiceSeparator", ">");
		this.dataSeparator  = config.getValue("comlib.default.dataSeparator", ":");
	}
	
	public int getLimit()
	{
		return this.ttl;
	}
	
	public String getVoiceSeparator()
	{
		return this.voiceSeparator;
	}
	
	public String getDataSeparator()
	{
		return this.dataSeparator;
	}
	
	public void appendVoiceSeparator(StringBuilder builder)
	{
		builder.append(this.getVoiceSeparator());
	}

	public void appendDataSeparator(StringBuilder builder)
	{
		builder.append(this.getDataSeparator());
	}

	public void appendData(StringBuilder builder, String data)
	{
		builder.append(data);
		this.appendDataSeparator(builder);
	}

	public void appendMessageID(StringBuilder builder, String id)
	{
		builder.append(id);
		this.appendVoiceSeparator(builder);
	}
	
	public void appendLimit(StringBuilder builder)
	{
		this.appendData(builder, String.valueOf((this.getLimit() - 1)));
	}
}