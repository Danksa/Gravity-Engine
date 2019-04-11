package gravityengine.vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2Test
{
	@Test
	public void shouldInstantiateWithoutParameters()
	{
		Vector2 v = new Vector2();
		assertTrue(v != null);
		assertEquals(0, v.x, 0);
		assertEquals(0, v.y, 0);
	}
	
	@Test
	public void shouldInstantiateWithCoordinates()
	{
		Vector2 v = new Vector2(5.0d, 4.0d);
		assertTrue(v != null);
		assertEquals(5.0d, v.x, 0);
		assertEquals(4.0d, v.y, 0);
	}
	
	@Test
	public void shouldInstantiateWithVector2()
	{
		Vector2 v = new Vector2(3.0d, -17.5d);
		
		Vector2 copy = new Vector2(v);
		
		assertTrue(copy != null);
		assertNotEquals(v, copy);
		assertEquals(v.x, copy.x, 0);
		assertEquals(v.y, copy.y, 0);
	}
	
	@Test
	public void shouldReturnXValue()
	{
		Vector2 v = new Vector2(4.0d, 7.0d);
		
		assertEquals(4.0d, v.x, 0.000001d);
	}
	
	@Test
	public void shouldReturnYValue()
	{
		Vector2 v = new Vector2(4.0d, 7.0d);
		
		assertEquals(7.0d, v.y, 0);
	}
	
	@Test
	public void doesVector2WithoutParametersReturnZeroCoordinates()
	{
		Vector2 v = new Vector2();
		
		assertEquals(0.0d, v.x, 0);
		assertEquals(0.0d, v.y, 0);
	}
	
	@Test
	public void shouldSetNewCoordinates()
	{
		Vector2 v = new Vector2();
		
		Vector2 vCopy = v.setTo(5.0d, 4.0d);
		
		assertEquals(v, vCopy);
		assertEquals(5.0d, v.x, 0);
		assertEquals(4.0d, v.y, 0.);
		
		vCopy = v.setTo(-18.0d, 3.1415d);
		assertEquals(v, vCopy);
		assertEquals(-18.0d, v.x, 0);
		assertEquals(3.1415d, v.y, 0);
	}
	
	@Test
	public void shouldSetToVector2Coordinates()
	{
		Vector2 v = new Vector2();
		Vector2 target = new Vector2(-3.0d, 2.2d);
		
		Vector2 vCopy = v.setTo(target);
		
		assertNotEquals(vCopy, target);	// should not be same instance as target
		assertEquals(vCopy, v); // should return same instance as acton upon instance
		assertEquals(target.x, v.x, 0.0000001d);
		assertEquals(target.y, v.y, 0.0000001d);
	}
	
	@Test
	public void shouldReturnLength()
	{
		Vector2 v = new Vector2(5.0d, 0.0d);
		
		assertEquals(5.0d, v.magnitude(), 0.0000001d);
		
		v.setTo(-4.0d, 0.0d);
		assertEquals(4.0d, v.magnitude(), 0.0000001d);
		
		v.setTo(2.0d, 2.0d);
		assertEquals(2.0d * Math.sqrt(2.0d), v.magnitude(), 0.0000001d);
		
		v.setTo(-3.0d, 4.0d);
		assertEquals(5.0d, v.magnitude(), 0.0000001d);
	}
	
	@Test
	public void shouldReturnSquaredLength()
	{
		Vector2 v = new Vector2(5.0d, 0.0d);
		
		assertEquals(25.0d, v.squaredMagnitude(), 0.0000001d);
		
		v.setTo(-4.0d, 0.0d);
		assertEquals(16.0d, v.squaredMagnitude(), 0.0000001d);
		
		v.setTo(2.0d, 2.0d);
		assertEquals(8.0d, v.squaredMagnitude(), 0.0000001d);
		
		v.setTo(-3.0d, 4.0d);
		assertEquals(25.0d, v.squaredMagnitude(), 0.0000001d);
	}
	
	@Test
	public void shouldReturnNormalizedVector2()
	{
		Vector2 v = new Vector2(4.0d, -8.0d);
		
		Vector2 vSame = v.normalize();
		
		assertEquals(vSame, v);
		assertEquals(1.0d, v.magnitude(), 0.0000001d);
		assertEquals(Math.sqrt(5.0d) / 5.0d, v.x, 0.0000001d);
		assertEquals(-2.0d * Math.sqrt(5.0d) / 5.0d, v.y, 0.0000001d);
	}
	
	@Test
	public void shouldTranslateByCoordinates()
	{
		Vector2 v = new Vector2(1.0d, 2.0d);
		
		Vector2 vSame = v.translate(-1.0d, -2.0d);
		
		assertEquals(v, vSame);
		assertEquals(0.0d, v.x, 0.0000001d);
		assertEquals(0.0d, v.y, 0.0000001d);
	}
	
	@Test
	public void shouldAddVector2()
	{
		Vector2 a = new Vector2(4.0d, 1.0d);
		Vector2 b = new Vector2(1.0d, 4.0d);
		
		Vector2 result = a.add(b);
		
		assertEquals(a, result);
		assertEquals(5.0d, result.x, 0.0000001d);
		assertEquals(5.0d, result.y, 0.0000001d);
	}
	
	@Test
	public void shouldSubtractVector2()
	{
		Vector2 a = new Vector2(8.5d, -3.0d);
		Vector2 b = new Vector2(-0.5d, 1.0d);
		
		Vector2 result = a.subtract(b);
		
		assertEquals(a, result);
		assertEquals(9.0d, result.x, 0.0000001d);
		assertEquals(-4.0d, result.y, 0.0000001d);
	}
	
	@Test
	public void shouldInvert()
	{
		Vector2 v = new Vector2(-3.0d, 2.0d);
		
		Vector2 inverted = v.invert();
		
		assertEquals(v, inverted);
		assertEquals(3.0d, inverted.x, 0.0000000001d);
		assertEquals(-2.0d, inverted.y, 0.0000000001d);
	}
	
	@Test
	public void shouldReturnDistanceBetweenTwoVectors()
	{
		Vector2 a = new Vector2(-1.0d, 1.0d);
		Vector2 b = new Vector2(5.0d, 1.0d);
		
		double dist = Vector2.distanceBetween(a, b);
		
		assertEquals(6.0d, dist, 0.0000000001d);
		
		b.setTo(a);
		dist = Vector2.distanceBetween(a, b);
		assertEquals(0.0d, dist, 0.0000000001d);
		
		b.setTo(a.x, a.y + 5.0d);
		dist = Vector2.distanceBetween(a, b);
		assertEquals(5.0d, dist, 0.0000000001d);
	}
	
	@Test
	public void shouldReturnSquaredDistanceBetweenTwoVectors()
	{
		Vector2 a = new Vector2(-1.0d, 1.0d);
		Vector2 b = new Vector2(5.0d, 1.0d);
		
		double dist = Vector2.squaredDistanceBetween(a, b);
		
		assertEquals(36.0d, dist, 0.0000000001d);
		
		b.setTo(a);
		dist = Vector2.squaredDistanceBetween(a, b);
		assertEquals(0.0d, dist, 0.0000000001d);
		
		b.setTo(a.x, a.y + 5.0d);
		dist = Vector2.squaredDistanceBetween(a, b);
		assertEquals(25.0d, dist, 0.0000000001d);
	}
	
	@Test
	public void shouldReturnDistanceToVector()
	{
		Vector2 a = new Vector2(1.0d, 1.0d);
		Vector2 b = new Vector2(1.0d, 5.0d);
		
		assertEquals(4.0d, a.distanceTo(b), 0.0000000001d);
		assertEquals(4.0d, b.distanceTo(a), 0.0000000001d);
	}
	
	@Test
	public void shouldReturnSquaredDistanceToVector()
	{
		Vector2 a = new Vector2(1.0d, 1.0d);
		Vector2 b = new Vector2(1.0d, 5.0d);
		
		assertEquals(16.0d, a.squaredDistanceTo(b), 0.0000000001d);
		assertEquals(16.0d, b.squaredDistanceTo(a), 0.0000000001d);
	}
}
