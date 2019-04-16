package gravityengine.ecs;

import java.util.Iterator;

import gravityengine.physics.PhysicsState;
import gravityengine.render.RenderContext;

public abstract class RenderingSystem extends AbstractEntitySystem
{
	@SafeVarargs
	public RenderingSystem(EntityComponentSystem ecs, Class<? extends EntityComponent>... components)
	{
		super(ecs, components);
	}

	@Override
	public void process(Iterator<Entity> entities, PhysicsState state, RenderContext context)
	{
		this.render(entities, context);
	}
	
	protected abstract void render(Iterator<Entity> entities, RenderContext context); // PhysicsState may be eventually needed for different speeds of lights etc.?
}
