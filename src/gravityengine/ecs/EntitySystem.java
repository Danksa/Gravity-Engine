package gravityengine.ecs;

import java.util.BitSet;
import java.util.Iterator;

import gravityengine.physics.PhysicsState;
import gravityengine.render.RenderContext;

public interface EntitySystem
{
	public void process(Iterator<Entity> entities, PhysicsState state, RenderContext context);
	
	public BitSet getGroupMask();
}
