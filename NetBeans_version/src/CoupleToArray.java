import java.io.*;
import java.util.*;
class CoupleToArray{
	private String fichier;
	public Stack<Wrapper> formes;
	private boolean rester;
	public CoupleToArray(String s){
		fichier=s;
		formes = new Stack<>();
	}
	public ArrayList<String> adapter(ArrayList<String> couple){
		String[] copyCouple;
		String copy;
		rester=true;
		for(int i=0;i<couple.size();i++){
			if(couple.get(i).contains(String.valueOf('>'))){
				couple.remove(i);
				rester=false;
			}
			else if(couple.get(i).contains(String.valueOf('"'))){
				copy=couple.get(i).substring(0,couple.get(i).indexOf('"'));
				couple.set(i,copy);
			}
			else if(couple.get(i).contains("e")){
				copy=couple.get(i);
				copyCouple=copy.split(",");
				if(copyCouple[0].contains(String.valueOf('e')))
					copyCouple[0]="0";
                                else copyCouple[1]="0";

				copy=copyCouple[0]+","+copyCouple[1];
				couple.set(i,copy);
			}}
			return couple;
	}
	public void traiter(ArrayList<String> couple, int flag, Point translate){
		Stack<ConnectPoints> l = new Stack<>();
		ConnectPoints l2;
		Wrapper s;
		boolean avancer=false;
		Point origine = new Point(0,0);
		Point origineDepart;
		Point ancien = new Point(0,0);
		Point nouveau = new Point(0,0);
		Point tireur1 = new Point(0,0);
		Point tireur2 = new Point(0,0);
		Point tmp;
        origineDepart=Point.add(translate,Point.convertir(couple.get(0)));
		l2=new ConnectPoints(ancien,ancien,ancien,ancien);
		l.add(l2);
		for(int i=1;i<couple.size();i++){
			if(couple.get(i).contains("m") || couple.get(i).contains("l")){
				flag=1;
				avancer=true;
				if(couple.get(i).contains("m"))
				{

					origine = Point.convertir(couple.get(i+1));
				}
			}

			else if(couple.get(i).contains("c")){
				flag=2;
				avancer=true;
			}

			else if(couple.get(i).contains("M") || couple.get(i).contains("L"))
			{
				flag=3;
				avancer=true;
				if(couple.get(i).contains("M"))
				{

					tmp = Point.add(Point.convertir(couple.get(i+1)),translate);
					origine = Point.sub(tmp,origine);
				}
			}

			else if(couple.get(i).contains("C")){
				flag=4;
				avancer=true;
			}

			else if(couple.get(i).contains("z")){
				flag=5;
				avancer=true;
			}

			if(avancer){
				i++;
				avancer=false;
			}

			switch(flag){
				case 1:
                                    nouveau=Point.add(ancien,Point.convertir(couple.get(i)));
                                    l2=new ConnectPoints(ancien,nouveau,ancien,nouveau);
                                    l.add(l2);
                                    ancien=nouveau;
                                    break;

				case 2:
                                    tireur1=Point.add(ancien,Point.convertir(couple.get(i)));
                                    i++;
                                    tireur2=Point.add(ancien,Point.convertir(couple.get(i)));
                                    i++;
                                    nouveau=Point.add(ancien,Point.convertir(couple.get(i)));
                                    l2=new ConnectPoints(ancien,nouveau,tireur1,tireur2);
                                    l.add(l2);
                                    ancien=nouveau;
                                    break;

				case 3:
                                    tmp=Point.add(translate,Point.convertir(couple.get(i)));
                                    nouveau=Point.sub(tmp,origineDepart);
                                    l2=new ConnectPoints(ancien,nouveau,ancien,nouveau);
                                    l.add(l2);
                                    ancien=nouveau;
                                    break;
				case 4:
                                    tmp=Point.add(translate,Point.convertir(couple.get(i)));
                                    tireur1=Point.sub(tmp,origineDepart);
                                    i++;
                                    tmp=Point.add(translate,Point.convertir(couple.get(i)));
                                    tireur2=Point.sub(tmp,origineDepart);
                                    i++;
                                    tmp=Point.add(translate,Point.convertir(couple.get(i)));
                                    nouveau=Point.sub(tmp,origineDepart);
                                    l2=new ConnectPoints(ancien,nouveau,tireur1,tireur2);
                                    l.add(l2);
                                    ancien=nouveau;
                                    break;
				case 5:
                                    if(i<couple.size() && !(couple.get(i).contains("m"))){
					l2=new ConnectPoints(ancien,origine,ancien,origine);
					l.add(l2);
					ancien=origine;
                                    }
                                    else
					i--;
                                    if(i==(couple.size()-1)){
						l2=new ConnectPoints(ancien,origine,ancien,origine);
						l.add(l2);
					}

					break;
				case 6:
					nouveau=Point.add(ancien,Point.convertir(couple.get(i)));
					ancien=nouveau;
					break;

				default:
					break;
			}
		}
		s= new Wrapper(l);
		formes.add(s);
	}


	public void lire() throws IOException
	{
		BufferedReader br=null;

		Point translateGroupe = new Point(0,0);
		Point translateLocal = new Point(0,0);
		Point translate = new Point(0,0);
		Point origine = new Point(0,0);
		Point premier;
		Point deuxieme = new Point(0,0);
		Point test;

		int lettre;
		int taille;
		int flag=1;
		String ligne;
		char[] mot = new char [8];
		int alarme=0;
		String[] copyCouple;
		String[] buf = new String[100];
		ArrayList<String> couple = new ArrayList<String>(0);
		String[] mega = new String[1000];
		rester=true;

		try{
			br = new BufferedReader(new FileReader(fichier));
		}

		catch(FileNotFoundException exc){
			return;
		}

		while ((lettre=br.read()) != -1)
		{
			if(lettre=='<' && br.read()=='g')
			{
				while ((lettre=br.read()) != -1)
				{
					alarme--;

					if(lettre=='t')
					{
						br.read(mot,0,5);
						if(mot[0]=='r' && mot[1]=='a' && mot[2]=='n' && mot[3]=='s' && mot[4]=='l')
						{
							ligne=br.readLine();
							buf=ligne.split("\\(");
							buf=buf[1].split("\\)");
							translateGroupe=Point.convertir(buf[0]);
						}
					}

					if(lettre=='g' && br.read()=='>')
					{
						br.close();
						return;
					}

					if(lettre=='<')
						alarme=2;



					if(alarme==1 && lettre=='g')
					{
						rester=true;
						couple.clear();
						taille=0;
						premier=new Point(0,0);
						while((lettre=br.read()) != -1)
						{
							if(lettre=='g' && br.read()=='>')
							{
								traiter(couple,flag,translateGroupe);
								flag=1;
								break;
							}

							if(lettre=='t'){
								br.read(mot,0,5);
								if(mot[0]=='r' && mot[1]=='a' && mot[2]=='n' && mot[3]=='s' && mot[4]=='l')
								{
									ligne=br.readLine();

									buf=ligne.split("\\(");
									buf=buf[1].split("\\)");
									translateGroupe=Point.add(translateGroupe,Point.convertir(buf[0]));
								}
							}

							if(lettre=='d')
							{
								br.read(mot,0,4);
								if(mot[0]=='=' && mot[1]=='"' && (mot[2]=='m' || mot[2]=='M') && mot[3]==' ')
								{
									if(mot[2]=='M')
										flag=3;

									ligne=br.readLine();
									copyCouple=ligne.split(" ");
									couple.addAll((List<String>) Arrays.asList(copyCouple));
									couple=adapter(couple);

									deuxieme=Point.convertir(couple.get(taille));
									test=Point.sub(deuxieme,premier);
									premier=Point.convertir(couple.get(taille));

									couple.set(taille,(String.valueOf(test.x)+','+String.valueOf(test.y)));

									if(taille!=0)
									{
										couple.add(taille,"m");
										taille++;
									}


									taille+=copyCouple.length;

								}
							}
						}
					}

					if(alarme==1 && lettre=='p')
					{
						rester=true;
						translateLocal= new Point(0,0);
						br.mark(8000);

						while((lettre=br.read()) != '>')
						{

							if(lettre=='t')
							{
								br.read(mot,0,5);
								if(mot[0]=='r' && mot[1]=='a' && mot[2]=='n' && mot[3]=='s' && mot[4]=='l')
								{
									ligne=br.readLine();

									buf=ligne.split("\\(");
									buf=buf[1].split("\\)");
									translateLocal=Point.convertir(buf[0]);
								}
							}
						}

						br.reset();
						translate=Point.add(translateGroupe,translateLocal);

						while((lettre=br.read()) != '>' && rester){
							if(lettre=='d')
							{
								br.read(mot,0,4);
								if(mot[0]=='=' && mot[1]=='"' && (mot[2]=='m' || mot[2]=='M') && mot[3]==' ')
								{
									if(mot[2]=='M')
										flag=3;

									couple.clear();
									ligne=br.readLine();
									couple.addAll((List<String>) Arrays.asList(ligne.split(" ")));
									couple=adapter(couple);
									traiter(couple,flag,translate);
									flag=1;

								}
							}
						}
					}

				}

			}
		}

		br.close();
		}

}