package Algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import Coords.Map;
import GIS.Fruit;
import GIS.Pacman;
import Geom.Point3D;

/**
 *This class reads the game from a CSV file and defines what each element is
 *@author Yasmin and Dan
 *
 */
public class createGameFromCsv 
{
	private FileReader fr  ; 
	private BufferedReader br ;
	String[] elements  ;

	public createGameFromCsv(ArrayList<Pacman> pacmanList , ArrayList<Fruit> fruitList  , String csvFilePath)
	{
		
		Point3D pixels ;
		Point3D coordinats ;
		Map map = new Map() ;
		File game = new File(csvFilePath) ;		
		int id ;
		int altitude ;
		double latitude ;
		double longtitude ;
		int speed ;
		try 
		{
			fr = new FileReader(game);
			BufferedReader br = new BufferedReader(fr);
			
				String line = br.readLine() ;
				elements = new String[line.length()] ;
				while((line = br.readLine()) != null)
				{
					elements = line.split(",");
					id = Integer.parseInt(elements[1]) ;
					latitude = Double.parseDouble(elements[2]);
					longtitude = Double.parseDouble(elements[3]);		
					altitude = Integer.parseInt(elements[4]) ;
					speed = Integer.parseInt(elements[5]) ;
					
					coordinats = new Point3D(latitude , longtitude , altitude) ;
					pixels = new Point3D(coordinats);
					if(elements[0].equals("P"))
					{
						pacmanList.add(new Pacman(id , latitude , longtitude  ,speed  )) ;
					}
					if(elements[0].equals("F"))
					{
						fruitList.add(new Fruit(id , latitude , longtitude , "" )) ;
						
					}
					System.out.println(elements.toString());
				}		 
		} 	
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
