import java.util.*;

public class Wrapper
{
	public Stack<ConnectPoints> line;
	public Stack<Rectangle> rect;
	public Stack<Rectangle> creux;
	public Stack<ConnectPoints> top;
	public Stack<ConnectPoints> bot;
	public Point max;
	public Point min;
	public Point position;

	public int order;
	public double taille;



	public Wrapper()
	{
	}

	public Wrapper(Stack<ConnectPoints> l)
	{

		line=ConnectPoints.m_curv(l);
		rect=new Stack<Rectangle>();
		rect.add(new Rectangle(line));
		min=rect.elementAt(0).min.clone();
		max=rect.elementAt(0).max.clone();
		position=new Point((-1)*rect.elementAt(0).min.x,(-1)*rect.elementAt(0).min.y);
		topSetter();
		bottomSetter();
	}

	public void trial()
	{
		Stack<Rectangle> tmp=new Stack<Rectangle>();
		Point moy= new Point(0,0);
		for (Rectangle r:rect)
			moy.moyenne(r.min, r.max);
		moy.x=moy.x/rect.size();
		moy.y=moy.y/rect.size();
		int i=0;
		for (Rectangle r:rect)
		{
			tmp.addAll(r.subdiviser_rect(moy));
		}
		rect=tmp;
		topSetter();
		bottomSetter();
	}

	public void pointSet(double x, double y)
	{
		position.make((-1)*min.x+x,(-1)*min.y+y);
	}

	private void topSetter()
	{
		Stack<ConnectPoints> new_t=new Stack<ConnectPoints>();
		int i;
		boolean ins;
		for (Rectangle r:rect)
		{

			ins=true;
			for (i=0;i<new_t.size();i++)
			{
				if (r.min.x<new_t.elementAt(i).max.x && r.max.x>new_t.elementAt(i).min.x)
				{
					if (r.min.y > new_t.elementAt(i).min.y)
					{
						ins=false;
					}
				}
			}
			if (ins)
				if(i==new_t.size())
					new_t.add(new ConnectPoints(r.min,new Point(r.max.x, r.min.y)));


		}
		top=new_t;
	}

	private void bottomSetter()
	{
		Stack<ConnectPoints> new_t=new Stack<ConnectPoints>();
		int i;
		boolean ins;
		for (Rectangle r:rect)
		{

			ins=true;
			for (i=0;i<new_t.size();i++)
			{
				if (r.min.x<new_t.elementAt(i).max.x && r.max.x>new_t.elementAt(i).min.x)
				{
					if (r.max.y < new_t.elementAt(i).max.y)
					{
						ins=false;
					}
				}
			}
			if (ins)
				if(i==new_t.size())
					new_t.add(new ConnectPoints(new Point(r.min.x, r.max.y), r.max));

		}
		bot=new_t;
	}

	private  Stack<ConnectPoints> insert(Stack<ConnectPoints> tab, int i, ConnectPoints val)
	{
		Stack<ConnectPoints> new_t=new Stack<ConnectPoints>();
		for(int j=0; j<tab.size();j++)
		{
			if (j == i)
				new_t.add(val);
	 		new_t.add(tab.elementAt(j));
		}
		return new_t;
	}

	public Wrapper Clone()
	{
		Wrapper s=new Wrapper();
		s.line=ConnectPoints.vect_clone(line);
		s.rect=Rectangle.vect_clone(rect);
		s.max=max;
		s.min=min;
		s.position=position.clone();
		return s;
	}
}