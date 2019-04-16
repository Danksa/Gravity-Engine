package gravityengine.ecs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGroupContainer
{
	private Map<BitSet, List<Entity>>		groups;
	
	public EntityGroupContainer()
	{
		this.groups = new HashMap<BitSet, List<Entity>>();
	}
	
	public void updateEntity(EntityComponentSystem ecs, Entity e)
	{
		BitSet entityMask = ecs.getMask(e);
		
		if(!contains(entityMask))
		{
			this.initialize(entityMask);
		}
		
		for(BitSet mask : groups.keySet())
		{
			if(isIncluded(entityMask, mask))
			{
				if(!groups.get(mask).contains(e))
				{
					this.groups.get(mask).add(e);
				}
			}
			else
			{
				this.groups.get(mask).remove(e);
				
				// TODO: Delete unneeded masks (Beware of ConcurrentModificationException!) 
//				if(groups.get(mask).isEmpty())
//				{
//					this.groups.remove(mask);
//				}
			}
		}
	}
	
	public void updateGroupMask(EntityComponentSystem ecs, BitSet groupMask)
	{
		if(!contains(groupMask))
		{
			this.initialize(groupMask);
		}
		
		for(Entity e : ecs.getEntities())
		{
			if(isIncluded(ecs.getMask(e), groupMask))
			{
				if(!groups.get(groupMask).contains(e))
				{
					this.groups.get(groupMask).add(e);
				}
			}
			else
			{
				this.groups.get(groupMask).remove(e);
				
				if(groups.get(groupMask).isEmpty())
				{
					this.groups.remove(groupMask);
				}
			}
		}
	}
	
	public List<Entity> getEntitiesForGroup(BitSet groupMask)
	{
		System.out.println("Size for " + groupMask + ": " + groups.get(groupMask).size());
		
		return groups.get(groupMask);
	}
	
	private boolean isIncluded(BitSet entityMask, BitSet groupMask)
	{
		BitSet anded = (BitSet) entityMask.clone();
		anded.and(groupMask);
		return anded.equals(groupMask);
	}
	
	public boolean contains(BitSet mask)
	{
		return groups.containsKey(mask);
	}
	
	private void initialize(BitSet mask)
	{
		if(groups.containsKey(mask)) return;
		
		this.groups.put(mask, new ArrayList<Entity>());
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		for(BitSet set : groups.keySet())
		{
			s.append("\tMask: " + set + "\n");
			
			for(Entity er : groups.get(set))
			{
				s.append("\t\tEntity: " + er.getId() + "\n");
			}
		}
		
		return s.toString();
	}
}
