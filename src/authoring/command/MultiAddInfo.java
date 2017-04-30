package authoring.command;

import java.util.ArrayList;
import java.util.List;

import authoring.networking.Packet;

/**
 * 
 * @author jimmy
 *
 */
public class MultiAddInfo extends Packet
{

	private static final long serialVersionUID = 1199205996330909954L;
	private List<AddInfo> multiAddInfo;

	public MultiAddInfo(List<AddInfo> addedEntities)
	{
		multiAddInfo = new ArrayList<AddInfo>();
		multiAddInfo.addAll(addedEntities);
	}

	public List<AddInfo> getAddedEntities()
	{
		return multiAddInfo;
	}
}
