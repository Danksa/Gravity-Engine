package gravityengine.ecs;

import java.util.Iterator;

import gravityengine.physics.PhysicsState;
import gravityengine.render.RenderContext;

public abstract class PhysicsSystem extends AbstractEntitySystem
{
	@SafeVarargs
	public PhysicsSystem(EntityComponentSystem ecs, Class<? extends EntityComponent>... components)
	{
		super(ecs, components);
	}

	@Override
	public void process(Iterator<Entity> entities, PhysicsState state, RenderContext context)
	{
		this.update(entities, state);
	}
	
	protected abstract void update(Iterator<Entity> entities, PhysicsState state);
}
