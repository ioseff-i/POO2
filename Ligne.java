import java.util.*;

class Ligne
{
	public Point pDepart;
	public Point pArrivee;
	public Point tireur1;
	public Point tireur2;
	public Point min;
	public Point max;

	public Ligne(Point point1, Point point2)
	{
		pDepart=point1;
		pArrivee=point2;
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}

	public Ligne(Point point1, Point point2, Point t1, Point t2)
	{
		pDepart=point1;
		pArrivee=point2;
		tireur1=t1;
		tireur2=t2;
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
	public Ligne(double x1, double y1, double x2, double y2)
	{
		pDepart=new Point(x1,y1);
		pArrivee=new Point(x2,y2);
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
	public Ligne()
	{

	}

	public double abscisse(double a)
	{

		double coeffD=(pArrivee.x-pDepart.x)/(pArrivee.y-pDepart.y);
		double val=(pDepart.x+coeffD*(a-pDepart.y));
		if (val<min.x)
			val=min.x;
		if (val>max.x)
			val=max.x;
		return  val;
	}

	public double ordonnee(double a)
	{
		double coeffD=(pArrivee.y-pDepart.y)/(pArrivee.x-pDepart.x);
		double val=(pDepart.y+coeffD*(a-pDepart.x));
		if (val<min.y)
			val=min.y;
		if (val>max.y)
			val=max.y;
		return val;
	}

	

	public Ligne clone()
	{
		Ligne l =new Ligne();
		l.pDepart=pDepart.clone();
		l.pArrivee=pArrivee.clone();
		l.min=min.clone();
		l.max=max.clone();
		return l;
	}

	public static Vector<Ligne> vect_clone(Vector<Ligne> lignes)
	{
		Vector<Ligne> l=new Vector<Ligne>();
		for (Ligne ligne:lignes)
			l.add(ligne.clone());
		return l;
	}

	public static Vector<Ligne> setCurve(Ligne l)
	{
		double t=0.0;
		double dx,ax,dy,ay;
		Vector<Ligne> vect_l=new Vector<Ligne>();
		dx =l.pArrivee.x;
		dy =l.pArrivee.y;
		for(t=0.05;t<=1;t+=0.05)
		{
			ax =(t*t*t)*l.pDepart.x+3*t*t*(1-t)*l.tireur1.x+3*t*(1-t)*(1-t)*l.tireur2.x+(1-t)*(1-t)*(1-t)*l.pArrivee.x;
			ay =(t*t*t)*l.pDepart.y+3*t*t*(1-t)*l.tireur1.y+3*t*(1-t)*(1-t)*l.tireur2.y+(1-t)*(1-t)*(1-t)*l.pArrivee.y;
			vect_l.add(new Ligne(new Point(dx,dy),new Point(ax,ay)));
			dx=ax;
			dy=ay;
		}
		return vect_l;
	}

	public static Vector<Ligne> conv_curve(Vector<Ligne> lignes)
	{		
		Vector<Ligne> vect_l=new Vector<Ligne>();
		for (Ligne l:lignes) 
		{
			if(l.tireur1!=null && l.tireur2!=null)
				vect_l.addAll(setCurve(l));
			else
				vect_l.add(l);
		}
		return vect_l;
		
	} 
}

