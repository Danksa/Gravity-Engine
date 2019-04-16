package gravityengine.ecs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import gravityengine.physics.PhysicsState;

public class EntityComponentSystemTest
{
	@Test
	public void initializeEntityComponentSystem()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
	}
	
	@Test
	public void createEntity()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		Entity e = ecs.createEntity();		
		assertNotNull(e);
		
		boolean doesExist = ecs.doesEntityExist(e);
		assertTrue(doesExist);
	}
	
	@Test
	public void addComponent()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		Entity e = ecs.createEntity();
		
		DummyComponent d = new DummyComponent(ecs);
		assertNotNull(d);
		
		ecs.addComponent(e, d);
		
		BitSet expectedMask = new BitSet(32);
		expectedMask.set(0);
		
		assertEquals(expectedMask, ecs.getMask(d));
		
		DummyComponent dReturned = ecs.getComponent(e, DummyComponent.class);
		assertNotNull(dReturned);
		assertEquals(d, dReturned);
		
		
		
		Entity e2 = ecs.createEntity();
		
		DummyComponent2 d2 = new DummyComponent2(ecs);
		assertNotNull(d2);
		
		ecs.addComponent(e2, d2);
		
		BitSet expectedMask2 = new BitSet(32);
		expectedMask2.set(1);
		
		assertEquals(expectedMask2, ecs.getMask(d2));
		
		DummyComponent2 d2Returned = ecs.getComponent(e2, DummyComponent2.class);
		assertNotNull(d2Returned);
		assertEquals(d2, d2Returned);
		
		
		
		Entity e3 = ecs.createEntity();
		
		DummyComponent d11 = new DummyComponent(ecs);
		DummyComponent2 d12 = new DummyComponent2(ecs);
		
		ecs.addComponent(e3, d11);
		ecs.addComponent(e3, d12);
		
		assertEquals(expectedMask, ecs.getMask(d11));
		assertEquals(expectedMask2, ecs.getMask(d12));
		
		assertNotEquals(d, d11);
		assertNotEquals(d2, d12);
		
		DummyComponent d11Returned = ecs.getComponent(e3, DummyComponent.class);
		DummyComponent2 d12Returned = ecs.getComponent(e3, DummyComponent2.class);
		assertNotNull(d11Returned);
		assertNotNull(d12Returned);
		assertNotEquals(d11Returned, d12Returned);
		assertEquals(d11, d11Returned);
		assertEquals(d12, d12Returned);
	}
	
	@Test
	public void removeComponent()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		Entity e = ecs.createEntity();
		
		DummyComponent d = new DummyComponent(ecs);
		DummyComponent2 d2 = new DummyComponent2(ecs);
		assertNotNull(d);
		assertNotNull(d2);
		
		ecs.addComponent(e, d);
		ecs.addComponent(e, d2);
		
		BitSet expectedMask = new BitSet(32);
		expectedMask.set(0);
		
		assertEquals(expectedMask, ecs.getMask(d));
		
		DummyComponent dReturned = ecs.getComponent(e, DummyComponent.class);
		assertNotNull(dReturned);
		assertEquals(d, dReturned);
		
		BitSet expectedMask2 = new BitSet(32);
		expectedMask2.set(1);
		
		assertEquals(expectedMask2, ecs.getMask(d2));
		
		DummyComponent2 d2Returned = ecs.getComponent(e, DummyComponent2.class);
		assertNotNull(d2Returned);
		assertEquals(d2, d2Returned);
		
		ecs.removeComponent(e, d);
		
		dReturned = ecs.getComponent(e, DummyComponent.class);
		assertNull(dReturned);
		
		d2Returned = ecs.getComponent(e, DummyComponent2.class);
		assertNotNull(d2Returned);
		assertEquals(d2, d2Returned);
		assertNotEquals(d, d2Returned);
	}
	
	@Test
	public void addSystem()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		DummySystem s = new DummySystem(ecs);
		assertNotNull(s);
		
		BitSet expectedMask = new BitSet(32);
		expectedMask.set(0);
		assertEquals(expectedMask, s.getGroupMask());
		
		ecs.addSystem(s);
		
		
		
		DummySystem3 s3 = new DummySystem3(ecs);
		assertNotNull(s3);
		assertNotEquals(s, s3);
		
		BitSet expectedMask3 = new BitSet(32);
		expectedMask3.set(0);
		expectedMask3.set(1);
		assertEquals(expectedMask3, s3.getGroupMask());
		
		assertNotEquals(expectedMask, s3.getGroupMask());
		assertNotEquals(expectedMask3, s.getGroupMask());
		
		ecs.addSystem(s3);
	}
	
	@Test
	public void getEntityListForSystem()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		DummySystem s = new DummySystem(ecs);
		ecs.addSystem(s);
		
		Entity e1 = ecs.createEntity();
		ecs.addComponent(e1, new DummyComponent(ecs));
		
		Entity e2 = ecs.createEntity();
		ecs.addComponent(e2, new DummyComponent2(ecs));
		
		Entity e3 = ecs.createEntity();
		ecs.addComponent(e3, new DummyComponent(ecs));
		ecs.addComponent(e3, new DummyComponent2(ecs));
		
		BitSet mask1 = new BitSet(32);
		mask1.set(0);
		assertEquals(mask1, ecs.getMask(DummyComponent.class));
		
		BitSet mask2 = new BitSet(32);
		mask2.set(1);
		assertEquals(mask2, ecs.getMask(DummyComponent2.class));
		
		List<Entity> sEnts = ecs.getEntitiesFor(s);
		assertEquals(2, sEnts.size());
		assertEquals(e1, sEnts.get(0));
		assertEquals(e3, sEnts.get(1));
		
		DummySystem3 s3 = new DummySystem3(ecs);
		ecs.addSystem(s3);
		
		List<Entity> sEnts3 = ecs.getEntitiesFor(s3);
		assertEquals(1, sEnts3.size());
		assertEquals(e3, sEnts3.get(0));
	}
	
	@Test
	public void getEntityIteratorForSystem()
	{
		EntityComponentSystem ecs = new EntityComponentSystem();
		
		DummySystem s = new DummySystem(ecs);
		ecs.addSystem(s);
		
		Entity e = ecs.createEntity();
		ecs.addComponent(e, new DummyComponent(ecs));
		
		Entity e2 = ecs.createEntity();
		ecs.addComponent(e2, new DummyComponent2(ecs));
		ecs.addComponent(e2, new DummyComponent(ecs));
		
		Iterator<Entity> it = ecs.getEntityIteratorFor(s);
		assertNotNull(it);
		assertTrue(it.hasNext());
		assertEquals(e, it.next());
		assertTrue(it.hasNext());
		assertEquals(e2, it.next());
		assertFalse(it.hasNext());
	}
	
	private class DummyComponent extends EntityComponent
	{
		public DummyComponent(EntityComponentSystem ecs)
		{
			super(ecs);
		}
	}
	
	private class DummyComponent2 extends EntityComponent
	{
		public DummyComponent2(EntityComponentSystem ecs)
		{
			super(ecs);
		}
	}
	
	private class DummySystem extends PhysicsSystem
	{
		public DummySystem(EntityComponentSystem ecs)
		{
			super(ecs, DummyComponent.class);
		}

		@Override
		protected void update(Iterator<Entity> entities, PhysicsState state)
		{
			
		}
	}
	
	private class DummySystem3 extends PhysicsSystem
	{
		public DummySystem3(EntityComponentSystem ecs)
		{
			super(ecs, DummyComponent.class, DummyComponent2.class);
		}

		@Override
		protected void update(Iterator<Entity> entities, PhysicsState state)
		{
			
		}
	}
}
