import java.util.*;

class Rectangle
{

	public Stack<Ligne> ligne;
	public Point min;
	public Point max;
	

	public Rectangle(Stack<Ligne> l)
	{
		ligne=l;
		if(l.size()>0)
		{
			Search_min();
			Search_max();
		}
	}
	public Rectangle()
	{
		ligne=new Stack<Ligne>();
	}
	
	public void addM(Ligne l)
	{
		if (l!=null) 
		{
			ligne.add(l);
			Search_min();
			Search_max();	
		}
	}

	private void Search_min()
	{
		double x,y;
		x=ligne.elementAt(0).min.x;
		y=ligne.elementAt(0).min.y;
		for (Ligne l: ligne) 
		{
			if (l.min.x<x)
				x=l.min.x;
			if (l.min.y<y)
				y=l.min.y;			
		}
		min= new Point(x,y);
	}

	private void Search_max()
	{
		double x,y;
		x=ligne.elementAt(0).max.x;
		y=ligne.elementAt(0).max.y;
		for (Ligne l: ligne) 
		{
			if (l.max.x>x)
				x=l.max.x;
			if (l.max.y>y)
				y=l.max.y;			
		}
		max= new Point(x,y);
	}

	public void border_max(char c, double bx, double by)
	{
		if (c=='i') 
		{
			if(bx!=-1 && max.x>bx)
				max.x=bx;
			if(by!=-1 && max.y>by)
				max.y=by;
		}
		else
		{
			if(bx!=-1 && max.x<bx)
				max.x=bx;
			if(by!=-1 && max.y<by)
				max.y=by;
		}
	}
	public void border_min(char c, double bx, double by)
	{
		if (c=='i') 
		{
			if(bx!=-1 && min.x>bx)
				min.x=bx;
			if(by!=-1 && min.y>by)
				min.y=by;
		}
		else
		{
			if(bx!=-1 && min.x<bx)
				min.x=bx;
			if(by!=-1 && min.y<by)
				min.y=by;
		}
	}

	public Stack<Rectangle> subdiviser_rect(Point moy)
	{
		Stack<Rectangle> rect=new Stack<Rectangle>();

		if (max.x-min.x<moy.x && max.y-min.y<moy.y)
		{
			rect.add(this);
			return rect;
		}

		double minx=min.x+(max.x-min.x)/2.0;	
		double miny=min.y+(max.y-min.y)/2.0;
		Rectangle r;
		if ((r=top_left())!=null)
		{
			r.border_min('s',min.x,min.y);
			r.border_max('i',minx,miny);
			if(r.cohenSutherland())
				rect.add(r);
		}		
		if ((r=top_right())!=null)
		{
			r.border_min('s',minx,min.y);
			r.border_max('i',max.x,miny);
			if(r.cohenSutherland())
				rect.add(r);
		}
		if ((r=bottom_left())!=null)
		{
			r.border_min('s',min.x,miny);
			r.border_max('i',minx,max.y);
			if(r.cohenSutherland())
				rect.add(r);
		}
		if ((r=bottom_right())!=null)
		{
			r.border_min('s',minx,miny);
			r.border_max('i',max.x,max.y);
			if(r.cohenSutherland())
				rect.add(r);
		}
		return rect;
	}

	private boolean cohenSutherland()
	{
		Point t_min=null,t_max=null;
		int c1,c2;
		Stack<Integer> remove= new Stack<Integer>();
		for (int i=0;i<ligne.size();i++) 
		{
			Point p1=ligne.elementAt(i).pDepart.clone();
			Point p2=ligne.elementAt(i).pArrivee.clone();
			c1=code(p1);
			c2=code(p2);
			while((c1|c2)!=0 && (c1&c2)==0)
			{
				if (c1==0) 
				{
					Point p3=p1;
					p1=p2;
					p2=p3;
					int c3=c1;
					c1=c2;
					c2=c3;
				}
				if ((c1&1)==1) 
				{
					p1.x=ligne.elementAt(i).abscisse(min.y);
					p1.y=min.y;	
				}
				else if ((c1&2)==2)
				{
					p1.x=ligne.elementAt(i).abscisse(max.y);
					p1.y=max.y;
				}
				else if ((c1&4)==4)
				{
					p1.x=max.x;
					p1.y=ligne.elementAt(i).ordonnee(max.x);
				}
				else
				{
					p1.x=min.x;
					p1.y=ligne.elementAt(i).ordonnee(min.x);
				}
			c1=code(p1);
			}
			if(c1!=0 || c2!=0)
				remove.add(i);
			else
			{
				t_min=Point.min3(t_min, p1, p2);
				t_max=Point.max3(t_max, p1, p2);
			}
		}
		int j=0;

		for (int i: remove)
		{
			ligne.remove((i-j));
			j++;
		}
		if (t_min!=null && t_max!=null) 
		{
			min.x=t_min.x;
			min.y=t_min.y;
			max.x=t_max.x;
			max.y=t_max.y;
		}
		
		if(ligne.size()>0)
			return true;
		else
			return false;
	}

	private int code(Point p)
	{
		int c=0;
		if (p.x<min.x)
			c+=8;
		else if(p.x>max.x)
			c+=4;
		if (p.y<min.y)
			c+=1;
		else if(p.y>max.y)
			c+=2;
		return c;
	}
	
	private boolean collision(Point d1, Point f1, Point d2, Point f2)
	{
		if(d1.x>f2.x || f1.x<d2.x || d1.y>f2.y || f1.y <d2.y)
			return false;
		else
			return true;
	}

	private Rectangle top_left()
	{
		Stack<Ligne> l=new Stack<Ligne>();
		double minx=min.x+(max.x-min.x)/2;
		double miny=min.y+(max.y-min.y)/2;
		for (Ligne ln:ligne) 
			if(collision(ln.min, ln.max, min, new Point(minx,miny)))
					l.add(ln);
		if(l.size()>0)
			return new Rectangle(l);	
		else
			return null;
	}

	private Rectangle top_right()
	{
		Stack<Ligne> l=new Stack<Ligne>();
		double minx=min.x+(max.x-min.x)/2;
		double miny=min.y+(max.y-min.y)/2;
		for (Ligne ln:ligne) 
		{
			if(collision(ln.min, ln.max, new Point(minx,min.y), new Point(max.x,miny)))
			//if (ln.max.x>=minx && ln.min.y<=miny)
				l.add(ln);
		}
		if(l.size()>0)
			return new Rectangle(l);	
		else
			return null;
	}

	private Rectangle bottom_left()
	{
		Stack<Ligne> l=new Stack<Ligne>();
		double minx=min.x+(max.x-min.x)/2;
		double miny=min.y+(max.y-min.y)/2;
		for (Ligne ln:ligne) 
		{
			if(collision(ln.min, ln.max, new Point(min.x,miny), new Point(minx,max.y)))
			//if (ln.min.x<=minx && ln.max.y>=miny)
				l.add(ln);
		}
		if(l.size()>0)
			return new Rectangle(l);	
		else
			return null;
	}

	private Rectangle bottom_right()
	{
		Stack<Ligne> l=new Stack<Ligne>();
		double minx=min.x+(max.x-min.x)/2;
		double miny=min.y+(max.y-min.y)/2;
		for (Ligne ln:ligne) 
		{
			if(collision(ln.min, ln.max, new Point(minx,miny), new Point(max.x,max.y)))
			if (ln.max.x>=minx && ln.max.y>=miny)
				l.add(ln);
		}
		if(l.size()>0)
			return new Rectangle(l);	
		else
			return null;
	}

	
	
	public Rectangle clone()
	{
		Rectangle r=new Rectangle();
		r.ligne=Ligne.vect_clone(ligne);
		r.min=min.clone();
		r.max=max.clone();
		return r;
	}

	public static Stack<Rectangle> vect_clone(Stack<Rectangle> rects)
	{
		Stack<Rectangle> r=new Stack<Rectangle>();
		for (Rectangle rect:rects)
			r.add(rect.clone());
		return r;
	}
}