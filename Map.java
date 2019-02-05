package Geom;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Geom.Point3D;

/**
 * This class displays the map we defined (in our game this is a picture of part of the city of Ariel). 
 * In this class we also calculated distances between points in pixels,
 * angle between points and conversion from coordinates to pixel and vice versa. 
 * @author Yasmin and Dan
 */
public class Map 
{
	
	        static final int mapWidth = 1300, mapHeight = 642;
			// offsets
			static final double mapLongitudeStart =35.202303  , mapLatitudeStart = 32.105731;
			// length of map in long/lat
			static final double mapLongitude = 35.212402-mapLongitudeStart, 
			        // invert because it decreases as you go down
			        mapLatitude = mapLatitudeStart-32.101869;
			
			
			public Map()
			{
				
			}
			/**
			 * This method converts points into pixels
			 * @param coordinates
			 * @return point3D in pixels
			 */
			public Point3D coordsToPixels(Point3D coordinates)//The source code:https://stackoverflow.com/questions/38748832/convert-longitude-and-latitude-coordinates-to-image-of-a-map-pixels-x-and-y-coor
			{
			    double x,y;
			    x=coordinates.y() - mapLongitudeStart;
			   
			    y = mapLatitudeStart-coordinates.x();
			  
			    int x1 = (int) (mapWidth*(x/mapLongitude));
			    int y1 = (int) (mapHeight*(y/mapLatitude));
		        
			    return new Point3D(x1,y1);

			}
			/**
			 * This method converts points into pixels
			 * @param pixelWidth
			 * @param pixelHeight
			 * @return point3D in pixels
			 */
			public Point3D pixelsToCoords(int pixelWidth, int pixelHeight)
			{
				double latitudeX = ((pixelWidth/mapWidth)*mapLongitude);
				double longtitudeY = ((pixelHeight/mapHeight)*mapLatitude);
				double CoordsX = mapLatitudeStart -longtitudeY; 
				double CoordsY = mapLongitudeStart+ latitudeX;
				Point3D pixelResult = new Point3D(CoordsX,CoordsY);
				return pixelResult;
			}
			
			/**
			 * This method calculates the distance between pixel points
			 * @param p1
			 * @param p2
			 * @return distance in meters
			 */
		public double PixDist(Point3D p1, Point3D p2)
		{
			Point3D p1Pixels =coordsToPixels(p1);
			Point3D p2Pixels =coordsToPixels(p2);
			double distanceMeters = p1Pixels.distance3D(p2Pixels);
			return distanceMeters;
		}
		/**
		 * This method calculates the angle between points
		 * @param point1
		 * @param point2
		 * @return angle
		 */
		public double angleBetween(Point3D point1, Point3D point2)
		{
			Point3D p1Pixels =coordsToPixels(point1);
			Point3D p2Pixels =coordsToPixels(point2);
			double angle = Math.toDegrees(Math.atan2(point2.x()-point1.x(),point2.y()-point1.y()));
			if(angle<0)
				angle+=360;
			return angle;
		}

	public static void main(String[] args) 
	{
		Map map = new Map();
		Point3D check = new Point3D(32.10332, 35.20904,0);
		System.out.println(map.coordsToPixels(check).toString());
	}

}