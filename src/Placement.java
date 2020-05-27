import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel; 
import java.util.*;
import java.io.*;
import java.io.IOException;


class Placement extends JPanel 
{
	public Stack<Shape> s;

	private boolean show_rect;
	private boolean show_point;
	
	public Placement()
	{
		s= new Stack<Shape>();
		show_point=false;
		show_rect=false;
		
		
	}


	public Placement(Stack<Shape> vct_s)
	{
		s=vct_s;
		show_point=false;
		show_rect=false;
		order();
	}

	private void order()
	{
		int ord=0,id=-1;
		double tmp;
		Stack<Shape> vct_s= new Stack<Shape>();
		for(Shape shape: s)
		{
			shape.order=-1;
			shape.taille=(shape.max.x-shape.min.x)*(shape.max.y-shape.min.y);
		}

		for (int i=0;i<s.size();i++) 
		{
			tmp=-1;
			for (int j=0;j<s.size();j++) 
			{
				if (s.elementAt(j).order == -1 && tmp<s.elementAt(j).taille)
				{
					tmp=s.elementAt(j).taille;
					id=j;
				}
			}
			s.elementAt(id).order=ord++;
			vct_s.add(s.elementAt(id));
		}
		s=vct_s;

	}
	public void setFic(String path, boolean ext)
	{
		Parser p= new Parser(path);
		if(path!="")
		{
			try
			{	
				p.lire();
			}
			catch(IOException exc)
			{
				return;
			}		
			if(ext)
				s.addAll(p.formes);
			else
				s=p.formes;

			order();

		  	for (Shape shape: s)
	  			for (int i=0;i<3;i++)
	  				shape.test();

			replaceAll();
			repaint();
		}
	}

	public void replaceAll()
	{
		Stack<Shape> vct_s=new Stack<Shape>();
		for(Shape shape: s)
			replace(vct_s, shape);
	}

	private void replace(Stack<Shape> vct_s, Shape sh)
	{
  		double offset_x=99999999,offset_y=0;
  		double height=(3543.0-(sh.max.y-sh.min.y))/1400.0;
  		
  		for (int i=0;i<1400;i++) 
  		{
  			double tmp_x=0;
  			double tmp_y=height*i;
  			for(Shape shape: vct_s)
			{
				if ((shape.position.y+shape.min.y)<(height*i+sh.max.y-sh.min.y)
						&& (shape.position.y+shape.max.y)>height*i) 
					{
						for(Rectangle r1:shape.rect)
						{
							for(Rectangle r2: sh.rect)
							{			
								if ((shape.position.y+r1.min.y)<(height*i-sh.min.y+r2.max.y)
									&& (shape.position.y+r1.max.y)>height*i-sh.min.y+r2.min.y) 
								{

									if(tmp_x<(shape.position.x+r1.max.x))
									{
										tmp_x=shape.position.x+r1.max.x;
									}
								}
							}
						}
					}
				
			}
			if(tmp_x<offset_x)
			{
				offset_x=tmp_x;
				offset_y=tmp_y;
			}
  		}
		sh.setP(offset_x,offset_y);
		vct_s.add(sh);
	}

  	public void paintComponent(Graphics g)
  	{
		super.paintComponent(g);

	  	for (Shape sh: s)
	  	{
		  	if (show_point) 
		  	{
			    g.setColor(Color.BLUE);
			    g.drawOval((int)sh.position.x/5,(int)sh.position.y/5,5,5);
				g.setColor(Color.BLACK);
				

			   	g.drawOval((int)(sh.position.x+sh.max.x)/5,(int)(sh.position.y+sh.max.y)/5,5,5);
			    g.setColor(Color.RED);
				g.drawOval((int)(sh.position.x+sh.min.x)/5,(int)(sh.position.y+sh.min.y)/5,5,5);
					
	  		}
		    g.setColor(Color.BLACK); 
			for(Ligne l : sh.ligne)
			{
			
				g.drawLine((int)(sh.position.x+l.pDepart.x)/5, (int)(sh.position.y+l.pDepart.y)/5,
				 		(int)(sh.position.x+l.pArrivee.x)/5, (int)(sh.position.y+l.pArrivee.y)/5);
			
			}
			if (show_rect)
			{
				g.setColor(Color.BLACK);
				for(Rectangle rect : sh.rect)
					{
						g.drawRect ((int)(sh.position.x+rect.min.x)/5, (int)(sh.position.y+rect.min.y)/5,
				    	 (int)(rect.max.x-rect.min.x)/5, (int)(rect.max.y-rect.min.y)/5); 
						g.fillRect((int)(sh.position.x+rect.min.x)/5, (int)(sh.position.y+rect.min.y)/5,50,50);


					}
				
			}

			
			
			
  		}  
  	}  
}