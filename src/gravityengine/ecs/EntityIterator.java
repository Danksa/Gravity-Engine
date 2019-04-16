package gravityengine.ecs;

import java.util.Iterator;
import java.util.List;

public class EntityIterator implements Iterator<Entity>
{
	private List<Entity>	entities;
	private int				index;
	
	public EntityIterator(List<Entity> entities)
	{
		if(entities == null) throw new IllegalArgumentException("entities must not be null!");
		this.entities = entities;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext()
	{
		return index < entities.size();
	}

	@Override
	public Entity next()
	{
		return entities.get(index++);
	}
	
}
