import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class ConnectPoints{
	public Point pDepart;
	public Point pArrivee;
	public Point tireur1;
	public Point tireur2;
	public Point min;
	public Point max;
        public ConnectPoints()
	{

	}

	public ConnectPoints(Point point1, Point point2){
		pDepart=point1;
		pArrivee=point2;
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
        public ConnectPoints(Point point1, Point point2, Point t1, Point t2){
		pDepart=point1;
		pArrivee=point2;
		tireur1=t1;
		tireur2=t2;
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
	public ConnectPoints(double x1, double y1, double x2, double y2){
		pDepart=new Point(x1,y1);
		pArrivee=new Point(x2,y2);
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}


	public double xCorr(double a){

		double coeffD=(pArrivee.x-pDepart.x)/(pArrivee.y-pDepart.y);
		double val=(pDepart.x+coeffD*(a-pDepart.y));
		if (val<min.x)
			val=min.x;
		if (val>max.x)
			val=max.x;
		return  val;
	}

	public double yCorr(double a){
		double coeffD=(pArrivee.y-pDepart.y)/(pArrivee.x-pDepart.x);
		double val=(pDepart.y+coeffD*(a-pDepart.x));
		if (val<min.y)
			val=min.y;
		if (val>max.y)
			val=max.y;
		return val;
	}



        @Override
	public ConnectPoints clone() throws CloneNotSupportedException{
		ConnectPoints l =new ConnectPoints();
		l.pDepart=pDepart.clone();
		l.pArrivee=pArrivee.clone();
		l.min=min.clone();
		l.max=max.clone();
		return l;
	}

	public static Stack<ConnectPoints> vect_clone(Stack<ConnectPoints> lines){
		Stack<ConnectPoints> l=new Stack<>();
                lines.forEach((ligne) -> {
                    try {
                        l.add(ligne.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(ConnectPoints.class.getName()).log(Level.SEVERE, null, ex);
                    }
            });
		return l;
	}

	public static Stack<ConnectPoints> curv(ConnectPoints l){
		double t=0.0;
		double dx,ax,dy,ay;
		Stack<ConnectPoints> vect_l=new Stack<>();
		dx =l.pArrivee.x;
		dy =l.pArrivee.y;
		for(t=0.05;t<=1;t+=0.05){
			ax =(t*t*t)*l.pDepart.x+3*t*t*(1-t)*l.tireur1.x+3*t*(1-t)*(1-t)*l.tireur2.x+(1-t)*(1-t)*(1-t)*l.pArrivee.x;
			ay =(t*t*t)*l.pDepart.y+3*t*t*(1-t)*l.tireur1.y+3*t*(1-t)*(1-t)*l.tireur2.y+(1-t)*(1-t)*(1-t)*l.pArrivee.y;
			vect_l.add(new ConnectPoints(new Point(dx,dy),new Point(ax,ay)));
			dx=ax;
			dy=ay;
		}
		return vect_l;
	}

	public static Stack<ConnectPoints> m_curv(Stack<ConnectPoints> lines){
		Stack<ConnectPoints> vect_l=new Stack();
                lines.forEach((l) -> {
                    if(l.tireur1!=null && l.tireur2!=null)
                        vect_l.addAll(curv(l));
                    else
                        vect_l.add(l);
            });
		return vect_l;

	}
}