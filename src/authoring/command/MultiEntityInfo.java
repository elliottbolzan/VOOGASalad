package authoring.command;

import java.util.ArrayList;
import java.util.List;

import authoring.networking.Packet;

public class MultiEntityInfo<T extends EntityCommandInfo> extends Packet
{
	private static final long serialVersionUID = 871085261882852024L;
	private List<T> multiInfo;

	public MultiEntityInfo(List<T> multiInfo)
	{
		this.multiInfo = new ArrayList<T>();
		this.multiInfo.addAll(multiInfo);
	}

	public List<T> getInfo()
	{
		return multiInfo;
	}
}
