package gravityengine.vector;

public class Vector2
{
	public double		x;
	public double		y;
	
	public Vector2()
	{
		this(0, 0);
	}
	
	public Vector2(Vector2 v)
	{
		this(v.x, v.y);
	}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2 setTo(Vector2 v)
	{
		return setTo(v.x, v.y);
	}
	
	public Vector2 setTo(double x, double y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2 add(Vector2 v)
	{
		return translate(v.x, v.y);
	}
	
	public Vector2 subtract(Vector2 v)
	{
		return translate(-v.x, -v.y);
	}
	
	public Vector2 translate(double x, double y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 invert()
	{
		this.x = -x;
		this.y = -y;
		return this;
	}
	
	public double magnitude()
	{
		return Math.sqrt(squaredMagnitude());
	}
	
	public double squaredMagnitude()
	{
		return x * x + y * y;
	}
	
	public Vector2 normalize()
	{
		double oneOverMagnitude = 1.0d / magnitude();
		this.x *= oneOverMagnitude;
		this.y *= oneOverMagnitude;
		return this;
	}
	
	public double dot(Vector2 v)
	{
		return x * v.x + y * v.y;
	}
	
	public double distanceTo(Vector2 v)
	{
		return distanceBetween(this, v);
	}
	
	public double squaredDistanceTo(Vector2 v)
	{
		return squaredDistanceBetween(this, v);
	}
	
	public static double distanceBetween(Vector2 a, Vector2 b)
	{
		return Math.sqrt(squaredDistanceBetween(a, b));
	}
	
	public static double squaredDistanceBetween(Vector2 a, Vector2 b)
	{
		return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
	}
}
