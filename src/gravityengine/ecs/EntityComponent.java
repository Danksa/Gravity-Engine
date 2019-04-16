package gravityengine.ecs;

import java.util.BitSet;

public abstract class EntityComponent
{
	private BitSet		mask;
	
	public EntityComponent(EntityComponentSystem ecs)
	{
		this.mask = ecs.getMask(this);
	}
	
	public BitSet getMask()
	{
		return mask;
	}
}
