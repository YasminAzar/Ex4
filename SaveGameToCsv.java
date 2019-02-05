package Algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import Coords.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Pacman;
import GUI.MyGUI;
import Geom.Point3D;

/**
 * This class saves the game to a CSV file
 * @author Yasmin and Dan
 */

public class SaveGameToCsv 
{
	private final int COLUMN_NUM  = 9 ;
	
	
public SaveGameToCsv(Game game  , int save_counter)
{
	try 
	{
		Point3D coordinates ;
		Map map = new Map();
		Date date = new Date() ;
		String dateString = "Saved date : " +date.toString() ;
		String tempString = dateString;
		dateString = dateString.replaceAll(" ", "_") ;
		dateString = dateString.replaceAll(":", "-") ;
		tempString = dateString.substring(0,13) ;
		tempString += dateString.substring(17) ;
		dateString = tempString ;
		tempString = dateString.substring(0,28) ;
		tempString += dateString.substring(32);
		System.out.println(tempString);
		FileWriter fl = new FileWriter("Games/Saved games/"+dateString+".csv");
		BufferedWriter bw = new BufferedWriter(fl)  ;
		StringBuilder sb = new StringBuilder();
		String line = "" ;
		String[] columnSplitter = new String[COLUMN_NUM];
		int id ;
		int radius ;
		int speed_or_weight ;
		double latitude ;
		double longtitude ;
		double altitude ;
		
		sb.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+game.pacmanSet.size()+","+game.fruitSet.size() +"\n");
		MyGUI frame= new MyGUI();
		for (Pacman pacman : game.pacmanSet) 
		{
			coordinates = new Point3D(map.pixelToCoords(pacman.getP_Location().ix(), pacman.getP_Location().iy(),frame.WIDTH,frame.HEIGHT)) ;

			id =pacman.getP_id() ;
			latitude = coordinates.x();
			longtitude =coordinates.y();
			altitude = coordinates.z();
			speed_or_weight = pacman.getP_speed() ;
			radius =pacman.getEATTING_RADIUS() ;
			sb.append("P," +id + "," + latitude + "," + longtitude + "," + altitude + "," + speed_or_weight + "," + radius +"\n" ) ;
		}
		
		for (Fruit fruit : game.fruitSet) 
		{
			
			coordinates = new Point3D(map.pixelToCoords(fruit.getFruitLocation().ix(), fruit.getFruitLocation().iy(),frame.WIDTH,frame.HEIGHT)) ;
			id =fruit.getId() ;
			latitude = coordinates.x();
			longtitude =coordinates.y();
			altitude = coordinates.z();
			speed_or_weight = fruit.getWEIGHT() ;
			sb.append("F," +id + "," + latitude + "," + longtitude + "," + altitude + "," + speed_or_weight + "\n"  ) ;
		}
		
		bw.write(sb.toString());
		bw.close(); ;
		
	} 
	catch (IOException e) 
	{
		e.printStackTrace();
	}
}
	
}
