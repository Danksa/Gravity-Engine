package gravityengine.ecs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityComponentSystem
{
	private Map<Class<? extends EntityComponent>, Integer>		componentTypes;
	private Map<Class<? extends EntitySystem>, BitSet>			systemMasks;
	private EntityGroupContainer								entityGroups;
	
	private List<Entity>										entities;
	private Map<Integer, ComponentMap>							components;
	private List<EntitySystem>									systems;
	
	private int		nextEntityId;
	
	public EntityComponentSystem()
	{
		this.componentTypes = new HashMap<Class<? extends EntityComponent>, Integer>();
		this.systemMasks = new HashMap<Class<? extends EntitySystem>, BitSet>();
		this.entityGroups = new EntityGroupContainer();
		
		this.components = new HashMap<Integer, ComponentMap>();
		
		this.entities = new ArrayList<Entity>();
		this.systems = new ArrayList<EntitySystem>();
		
		this.nextEntityId = 1; // Don't use ID 0
	}
	
	public Entity createEntity()
	{
		Entity e = new Entity(getNextEntityId());
		
		this.ensureThatComponentMapExists(e.getId());
		this.entities.add(e);
		
		return e;
	}
	
	public void addComponent(Entity e, EntityComponent c)
	{
		if(!doesEntityExist(e)) return;
		
		this.components.get(e.getId()).put(c);
		
		this.entityGroups.updateEntity(this, e);
		
		System.out.println("Add Component");
		System.out.println("Groups:");
		System.out.println(entityGroups);
	}
	
	private void ensureThatComponentMapExists(int entityId)
	{
		if(!components.containsKey(entityId))
		{
			this.components.put(entityId, new ComponentMap());
		}
	}
	
	public void removeComponent(Entity e, EntityComponent c)
	{
		if(!doesEntityExist(e)) return;
		
		int entityId = e.getId();
		
		if(!components.containsKey(entityId)) return;
		if(!components.get(entityId).contains(c.getClass())) return;
		
		this.components.get(entityId).remove(c.getClass());
		
		this.entityGroups.updateEntity(this, e);
	}
	
	public boolean doesEntityExist(Entity e)
	{
		return entities.contains(e);
	}
	
	private int getNextEntityId()
	{
		return nextEntityId++;
	}
	
	public List<Entity> getEntities()
	{
		return entities;
	}
	
	public void addSystem(EntitySystem s)
	{
		BitSet groupMask = s.getGroupMask();
		
		if(!systemMasks.containsKey(s.getClass()))
		{
			this.systemMasks.put(s.getClass(), groupMask);
		}
		
		this.entityGroups.updateGroupMask(this, groupMask);
		
		this.systems.add(s);
	}
	
	public Iterator<Entity> getEntityIteratorFor(EntitySystem s)
	{
		return new EntityIterator(getEntitiesFor(s));
	}
	
	public List<Entity> getEntitiesFor(EntitySystem s)
	{
		System.out.println("System Mask: " + s.getGroupMask());
		return entityGroups.getEntitiesForGroup(s.getGroupMask());
	}
	
	public BitSet getMask(Entity e)
	{
		ComponentMap entityComponents = components.get(e.getId());
		
		BitSet mask = new BitSet(getComponentTypeCount());
		for(Class<? extends EntityComponent> c : entityComponents.keySet())
		{
			mask.or(getMask(c));
		}
		
		return mask;
	}
	
	public <C extends EntityComponent> C getComponent(Entity entity, Class<C> component)
	{
		int entityId = entity.getId();
		if(!components.containsKey(entityId)) return null;
		if(!components.get(entityId).contains(component)) return null;
		return (C) components.get(entityId).get(component);
	}
	
	public int getComponentTypeCount()
	{
		return componentTypes.size();
	}
	
	public BitSet getMask(EntityComponent c)
	{
		return getMask(getId(c));
	}
	
	public int getId(EntityComponent c)
	{
		return getId(c.getClass());
	}
	
	public BitSet getMask(Class<? extends EntityComponent> c)
	{
		return getMask(getId(c));
	}
	
	public int getId(Class<? extends EntityComponent> c)
	{
		if(componentTypes.containsKey(c)) return componentTypes.get(c);
		
		int nextId = componentTypes.size();
		this.componentTypes.put(c, nextId);
		
		return nextId;
	}
	
	private BitSet getMask(int componentId)
	{
		BitSet bits = new BitSet();
		bits.set(componentId);
		
		return bits;
	}
	
	private class ComponentMap
	{
		private Map<Class<? extends EntityComponent>, EntityComponent>	map;
		
		public ComponentMap()
		{
			this.map = new HashMap<Class<? extends EntityComponent>, EntityComponent>();
		}
		
		public void put(EntityComponent c)
		{
			this.map.put(c.getClass(), c);
		}
		
		@SuppressWarnings("unchecked")
		public <C extends EntityComponent> C get(Class<C> c)
		{
			return (C) map.get(c);
		}
		
		public void remove(Class<? extends EntityComponent> c)
		{
			this.map.remove(c);
		}
		
		public boolean contains(Class<? extends EntityComponent> c)
		{
			return map.containsKey(c);
		}
		
		public Set<Class<? extends EntityComponent>> keySet()
		{
			return map.keySet();
		}
	}
}
