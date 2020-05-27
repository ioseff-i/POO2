import java.util.*;

public class Shape
{	
	public Stack<Ligne> ligne;
	public Stack<Rectangle> rect;
	public Stack<Rectangle> creux; 
	public Stack<Ligne> top; 
	public Stack<Ligne> bot; 
	public Point max;
	public Point min;
	public Point position;

	public int order;
	public double taille;

	

	public Shape()
	{
	}

	public Shape(Stack<Ligne> l)
	{
		
		ligne=Ligne.conv_curve(l);
		rect=new Stack<Rectangle>();
		rect.add(new Rectangle(ligne));
		min=rect.elementAt(0).min.clone();
		max=rect.elementAt(0).max.clone();
		position=new Point((-1)*rect.elementAt(0).min.x,(-1)*rect.elementAt(0).min.y);
		setTop();
		setBot();
	}

	public void test()
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
		setTop();
		setBot();
	}

	public void setP(double x, double y)
	{
		position.set((-1)*min.x+x,(-1)*min.y+y);
	}

	private void setTop()
	{
		Stack<Ligne> new_t=new Stack<Ligne>();
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
					new_t.add(new Ligne(r.min,new Point(r.max.x, r.min.y)));
				
			
		}
		top=new_t;
	}

	private void setBot()
	{
		Stack<Ligne> new_t=new Stack<Ligne>();
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
					new_t.add(new Ligne(new Point(r.min.x, r.max.y), r.max));
				
		}
		bot=new_t;
	}

	private  Stack<Ligne> insert(Stack<Ligne> tab, int i, Ligne val)
	{
		Stack<Ligne> new_t=new Stack<Ligne>();
		for(int j=0; j<tab.size();j++)
		{
			if (j == i)
				new_t.add(val);
	 		new_t.add(tab.elementAt(j));
		}
		return new_t;
	}

	public Shape Clone()
	{
		Shape s=new Shape();
		s.ligne=Ligne.vect_clone(ligne);
		s.rect=Rectangle.vect_clone(rect);
		s.max=max;
		s.min=min;
		s.position=position.clone();
		return s;
	}
}