class Point
{
	public double x;
	public double y;

	public Point(double x1, double x2)
	{
		x=x1;
		y=x2;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void set(double x1, double y1)
	{
		x=x1;
		y=y1;
	}	

	public void setX(double x1)
	{
		x=x1;
	}

	public void setY(double y1)
	{
		y=y1;
	}

	public void moyenne(Point min, Point max)
	{
		x+=Math.abs(max.x-min.x);
		y+=Math.abs(max.y-min.y);
	}

	public static boolean equal(Point p1, Point p2)
	{
		return (p1.x==p2.x && p1.y==p2.y);
	}

	public static Point add(Point p1, Point p2)
	{
		Point p = new Point(0,0);
		p.setX(p1.getX()+p2.getX());
		p.setY(p1.getY()+p2.getY());
		return p;
	}

	public static Point sub(Point p1, Point p2)
	{
		Point p = new Point(0,0);
		p.setX(p1.x-p2.x);
		p.setY(p1.y-p2.y);
		return p;
	}

	public static Point min2(Point p1, Point p2)
	{
		double x,y;
		if (p1.x<p2.x)
			x=p1.x;
		else
			x=p2.x;
		if (p1.y<p2.y)
			y=p1.y;
		else
			y=p2.y;
		return new Point(x,y);
	}

	public static Point min3(Point p1, Point p2, Point p3)
	{
		if(p1==null)
			return Point.min2(p2,p3);
		else
			return Point.min2(p1,Point.min2(p2,p3));
	}

	public static Point max2(Point p1, Point p2)
	{
		double x,y;
		if (p1.x>p2.x)
			x=p1.x;
		else
			x=p2.x;
		if (p1.y>p2.y)
			y=p1.y;
		else
			y=p2.y;
		return new Point(x,y);
	}

	public static Point convertir(String s)
	{
		String[] valeurs = new String[2];
		valeurs=s.split(",");

		Double x = new Double(valeurs[0]);
		Double y = new Double(valeurs[1]);
		
		Point p=new Point(x,y);
		return p;
	}

	public static Point max3(Point p1, Point p2, Point p3)
	{
		if(p1==null)
			return Point.max2(p2,p3);
		else
			return Point.max2(p1,Point.max2(p2,p3));
	}
	public Point clone()
	{
		return new Point(x,y);
	}
}
