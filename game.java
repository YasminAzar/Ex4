package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import Robot.Fruit;
import Robot.Packman;

public class game {

	public game(ArrayList<Packman> pacmanArray, ArrayList<Fruit> fruitArray) {
	}
	public void setfile_dir(String path) {
		System.out.println("data/Ex4_OOP_example1.csv");
		
	}
	public void Csvread() {
		
		String csvFile = "data1.csv"; //Read from this file
		String line = "";
		String cvsSplitBy = ",";
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+ 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");

		//try and catch for the reading part
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
		{
			br.readLine();
			br.readLine();

			while ((line = br.readLine()) != null) //if the third line in the read file is not empty, read from it
			{
				String[] userInfo = line.split(cvsSplitBy); //userInfo is an array of all the information in a row

				System.out.println("MAC: " + userInfo[0] + " , SSID: " + userInfo[1] +
						" ,AuthMode: " + userInfo[2] + " ,FirstSeen: " + " ,Channel: "+ userInfo[4]+
						" ,RSSI: "+userInfo[5]+" ,CurrentLatitude: "+userInfo[7]+" ,CurrentLongitude: "+userInfo[6]+
						" ,AltitudeMeters: "+userInfo[8]+" ,AccuracyMeters: "+userInfo[9]+" ,Type: "+userInfo[10]);
			}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		String fileName = "data1.kml"; //write to this file
		String kmlstart="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+ 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";
		PrintWriter pw =null;
		File kmlFile=new File(fileName);

		String kmlend="</kml>";
		String kmldoc="<Document>";
		String sb1 = new String();

}
}
