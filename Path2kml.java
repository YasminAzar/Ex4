package fileFormat;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import Coords.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Pacman;
import GIS.Path;
import GUI.MyGUI;
import Geom.Point3D;

/**
 * This class converts a path from a CSV file to a KML file.
 * base on: https://stackoverflow.com/questions/18725039/java-create-a-kml-file-and-insert-elements-in-existing-file 
 * @author Yasmin and Dan
 *
 */
public class Path2kml 
{

	String[]colorSelected= {"3f0407","3f043c","24043f","040f3f",
			"04323f","043f21","043f08","3f3904","3f2504",
			"3f0f04","ea1b16","2fea16","16e8ea","eae716"};
	String date;
	Path path = new Path();
	Game game = new Game();

	ArrayList<Point3D>OnePath = new ArrayList<Point3D>();
	ArrayList<ArrayList<Point3D>>fullPath = new ArrayList<ArrayList<Point3D>>();
	int indexOfPacman = 0;


	public Path2kml()
	{}

	public String plusTime(String date,int time) {
		if(time<=0) {
			return date;
		}
		double s=0,m=0,h=0;
		if(time>=60) {
			if(time>=3600) {
				m=time/60;
				s=m*100;
				s%=100;
				h=m/60;
			}
			m=time/60;
			s=m*100;
			s%=100;
			h=0;
		}
		else {
			s=time;
		}

		String cr=date.substring(11,19);
		cr.replace("Z","");	
		String [] sr=cr.split(":");
		int addedTime[]=new int [3];
		addedTime [0]=Integer.parseInt(sr[0])+(int)(h);
		addedTime [1]=Integer.parseInt(sr[1])+(int)(m);
		addedTime [2]=Integer.parseInt(sr[2])+(int)(s);
		if(addedTime[2]>59) {
			int count=addedTime[2]/60;
			while(count>0) {
				addedTime [2]-=60;
				count--;
			}

			if(addedTime[1]==59) {
				addedTime[1]=0;
				if(addedTime[0]==23) {
					addedTime[0]=0;
				}
				else {
					addedTime[0]++;
				}
			}
			else {
				addedTime[1]++;
			}

		}

		if(addedTime[1]>59) {
			int count=addedTime[1]/60;
			while(count>0) {
				addedTime [1]-=60;
				count--;
			}
			if(addedTime[0]==23) {
				addedTime[0]=0;
			}
			else{
				addedTime[0]++;
			}
		}

		if(addedTime[0]>23) {
			int count=addedTime[0]/24;
			while(count>0) {
				addedTime [0]-=24;
				count--;
			}
		}
		String s1="";
		s1=String.format("%02d:%02d:%02d",addedTime[0],addedTime[1],addedTime[2]);
		return date.substring(0,10)+"T"+s1+"Z";
	}
	public String getTime() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss ").format(Calendar.getInstance().getTime());
		timeStamp=timeStamp.replace("_", "T").replace(" ", "Z");
		date=timeStamp;
		return timeStamp;
	}

	public  void readPath(Game game,MyGUI mf)
	{

		try 
		{

			FileWriter fw = new FileWriter("PacmanPath.kml") ;

			Date dateCurrent = new Date() ;
			BufferedWriter bw = new BufferedWriter(fw) ;
			StringBuilder sb = new StringBuilder();
			collectPoint(game,mf);


			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\r\n" + "");

			for(Pacman pacman : game.pacmanSet)
			{

				Iterator<Point3D> pacmanIt = OnePath.iterator();
				while(pacmanIt.hasNext())
				{
					String time = getTime();
					Point3D point = pacmanIt.next();


					sb.append("<Placemark>\n") ;
					sb.append("<name>"+"Pacman"+"</name>\n");
					sb.append("<TimeStamp>\n");
					sb.append("<when>"+time+"</when>\n");
					sb.append("</TimeStamp>");
					sb.append("<description>\n");
					sb.append("</description>\n");
					sb.append("<Point>\n") ;
					sb.append("<coordinates>"+" "+point.y()+","+point.x()+","+point.z()+"</coordinates>\n");
					sb.append("</Point>\n");
					sb.append("</Placemark>\n") ;

				}

				Iterator <Pacman>secondIterator = game.pacmanSet.iterator();
				int colorNumber =0;
				while(secondIterator.hasNext()) {
					Pacman Pacman = secondIterator.next();
					String setPoints="";
					for(int i=0;i<OnePath.size();i++) {
						setPoints+=""+OnePath.get(i)+"\n";
					}
					sb.append(""+"<Placemark>\n");
					sb.append("<name>\n"+"Selected Path\n"+"</name>\n");
					sb.append("<description>\n"+"Path\n"+"</description>\n");
					sb.append("<LineString>\n");
					sb.append("<coordinates>\n"+setPoints+"</coordinates>\n");
					sb.append("</LineString>\n");
					sb.append("</Placemark>\n");

					if(colorNumber!=colorSelected.length-1)
						colorNumber++;
					else
						colorNumber=0;
				}

			}
			sb.append("\n</Folder>\n");
			sb.append("</Document></kml>") ;
			bw.write(sb.toString());
			bw.close();

		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void collectPoint(Game game, MyGUI mf)
	{
		Map map= new Map();

		Iterator<Pacman> pacmans = game.pacmanSet.iterator();
		Iterator<Fruit>fruits = game.fruitSet.iterator();
		while(pacmans.hasNext())
		{
			Pacman p1 = pacmans.next();
			Point3D convert =map.pixelToCoords((int)p1.getP_Location().y(),(int) p1.getP_Location().x(),mf.getWidth(),mf.getHeight());
			System.out.println(convert.y());
			OnePath.add(convert);
		}
		while(fruits.hasNext())
		{
			Fruit p1 = fruits.next();
			Point3D convert =map.pixelToCoords((int)p1.getFruitLocation().y(),(int) p1.getFruitLocation().x(),mf.getWidth(),mf.getHeight());
			System.out.println(convert.y());
			OnePath.add(convert);	
		}
	}
}
