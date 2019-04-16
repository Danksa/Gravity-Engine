package gravityengine.ecs;

import java.util.BitSet;

public abstract class AbstractEntitySystem implements EntitySystem
{
	private BitSet		entityMask;
	
	@SafeVarargs
	public AbstractEntitySystem(EntityComponentSystem ecs, Class<? extends EntityComponent>... components)
	{
		this.entityMask = new BitSet(ecs.getComponentTypeCount());
		
		for(Class<? extends EntityComponent> c : components)
		{
			this.entityMask.or(ecs.getMask(c));
		}
	}
	
	@Override
	public BitSet getGroupMask()
	{
		return entityMask;
	}
}
