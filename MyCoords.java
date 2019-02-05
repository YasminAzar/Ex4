package Coords;

import java.awt.Point;

import Geom.Point3D;
/**
 * 
 * 
 * 
 */
public class MyCoords implements coords_converter
{

	final double RADIAN_ROUND = 2 * Math.PI ;
	final double LON_NORM = 0.847091174 ;
	final int EARTH_RADIUS = 6371000  ;
	final int DEGREES_ROUND = 360     ;
	final int METER_CONVERTION = 1000 ;


	/** computes a new point which is the gps point transformed by a 3D vector (in meters)*/

	public MyCoords()
	{

	}
	/**
	 * This function computes a new point which is the gps point transformed by a 3D vector (in meters)
	 * @param gps
	 * @param local_vector_in_meter
	 *  
	 */
	public  Point3D add(Point3D gps, Point3D local_vector_in_meter)
	{

		double addAltitude = local_vector_in_meter.z() + gps.z();

		Point3D addVector ;
		double xTodegrees = Math.asin(local_vector_in_meter.x() / EARTH_RADIUS);
		xTodegrees= Math.toDegrees(xTodegrees);
		double yTodegrees =  Math.asin((local_vector_in_meter.y() / (EARTH_RADIUS * LON_NORM))); ;

		xTodegrees += gps.x() ;
		yTodegrees += gps.y() ;

		addVector = new Point3D(xTodegrees , yTodegrees , addAltitude ) ;
		return addVector ;

	}
	/** This function computes the 3D distance (in meters) between the two gps points
	 * that we have received 
	 * @param gps0
	 * @param gps1 
	 * */

	public double distance3d(Point3D gps0, Point3D gps1)
	{		
		double toMetterLat = diff(gps0.x() , gps1.x());		
		double toMetterLon = diff(gps0.y() , gps1.y());

		toMetterLat = diff_radian(toMetterLat);
		toMetterLon = diff_radian(toMetterLon);

		toMetterLat = toMeter(toMetterLat);
		toMetterLon = toMeter(toMetterLon) * LON_NORM ;

		toMetterLat = Math.pow(toMetterLat, 2) ;
		toMetterLon = Math.pow(toMetterLon, 2) ;

		return Math.sqrt(toMetterLat + toMetterLon );

	}

	/**
	 * This method computes the polar representation of the 3D vector which will be received after
	 * calculationof the two points: gps0-->gps1 
	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance
	 *Formulas from this site : https://www.omnicalculator.com/other/azimuth
	 * @param gps0
	 * @param gps1
	 **/
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) 
	{
		double[] aziElevDist = new double[3];
		double azimuth   ;
		double elevation ; 
		double distance  ;
		double latInitialPoint  = gps0.x() ;
		double latFinalPoint    = gps1.x() ;
		double lonInitialPoint = gps0.y()     ;
		double lonFinalPoint   = gps1.y()     ;
		double cosLatInitialPoint= Math.cos(latInitialPoint) ;
		double cosLatFinalPoint = Math.cos(latFinalPoint)   ;
		double sinLatInitialPoint = Math.sin(latInitialPoint)   ;
		double sinLatFinalPoint   = Math.sin(latFinalPoint)    ;
		double diffLat               = latFinalPoint  - latInitialPoint ;
		double diffLon              = lonFinalPoint - lonInitialPoint  ;
		double sinDiffLat        = Math.sin(diffLat/2) ; 
		double sinDiffLon        = Math.sin(diffLon/2) ;  
		double cosDiffLon       = Math.cos(diffLon) ;

		double a = Math.pow(sinDiffLat, 2)  + cosLatInitialPoint*cosLatFinalPoint*Math.pow(sinDiffLon, 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) ;
		double d = EARTH_RADIUS * c ;

		distance = distance3d(gps0 , gps1) ;
		azimuth = Math.atan2(Math.sin(diffLon) * cosLatFinalPoint  , (cosLatInitialPoint * sinLatFinalPoint) - ( sinLatInitialPoint * cosLatFinalPoint * cosDiffLon ) ) ;

		elevation =Math.toDegrees( Math.asin((gps1.z() - gps0.z())/distance)) ;
		aziElevDist[0] = azimuth ;
		aziElevDist[1] = elevation ;
		aziElevDist[2] = distance ;

		//		double sinLatInitalPoint = Math.sin(latInitialPoint);
		//		double sinLatFinalPoint  = Math.sin(latFinalPoint);
		//		double cosLatInitalPoint = Math.cos(latInitialPoint);
		//		double cosLatFinalPoint  = Math.cos(latFinalPoint);
		//		double a =Math.pow(Math.sin(diffLat / 2), 2) + Math.pow(Math.sin(diffLon / 2), 2) *Math.cos(latInitialPoint)*Math.cos(latFinalPoint);
		//		double c =  2*Math.atan2(Math.sqrt(a) , Math.sqrt(1-a));
		//		double d = EARTH_RADIUS * c ;
		//		
		//				
		//	    distance = distance3d(gps0,gps1);
		//	    
		//	    aziElevDist[2] = distance ;
		//	    
		//	    azimuth = Math.atan2(sinDiffLon * cosLatFinalPoint , (cosLatInitalPoint * sinLatFinalPoint ) - sinLatInitalPoint * cosLatFinalPoint*cosDiffLon);
		//	    
		//	    aziElevDist[0] = azimuth < 0 ? (Math.toDegrees(azimuth)+360) : Math.toDegrees(azimuth);
		//	    
		//	    elevation =Math.toDegrees( Math.asin((gps1.z() - gps0.z())/distance))  ;//lambda = asin{ (elev2 - elev1) / d }
		//	    aziElevDist[1] = elevation ;
		//	    
		return aziElevDist ;

	}
	
	/** This function computes the 3D vector (in meters) between two gps points gps0 and gps1
	 * @param gps0
	 * @param gps1 
	 **/
	public Point3D vector3D(Point3D gps0, Point3D gps1)
	{
		Point3D metterVectore ;
		double toMetterLat = diff(  gps1.x() ,gps0.x());		
		double toMetterLon = diff(gps1.y() , gps0.y());
		double toMetterAlt = diff(gps1.z() , gps0.z());
		toMetterLat = diff_radian(toMetterLat);
		toMetterLon = diff_radian(toMetterLon);

		toMetterLat = toMeter(toMetterLat);
		toMetterLon = toMeter(toMetterLon) * LON_NORM ;

		metterVectore = new Point3D(toMetterLat , toMetterLon , toMetterAlt);
		return metterVectore ;
	}
	/**
	 * This function returns 3D point that represent a 2d vector in meter
	 * @param point1
	 * @param point2 
	 */
	public Point3D twoPointsToMetter(Point3D point1 , Point3D point2) 
	{
		Point3D p ;
		double toMetterLat = diff(point1.x() , point2.x());		
		double toMetterLon = diff(point1.y() , point2.y());

		toMetterLat = diff_radian(toMetterLat);
		toMetterLon = diff_radian(toMetterLon);

		toMetterLat = toMeter(toMetterLat);
		toMetterLon = toMeter(toMetterLon) * LON_NORM ;

		p = new Point3D(toMetterLat , toMetterLon ) ;
		return p ;

	}
	
	/********************************PRIVATE METHODES***********************************/
	private double diff(double endpoint , double statpoint)
	{
		return endpoint - statpoint ;
	}

	private double diff_radian(double diff)
	{
		return (Math.PI * diff) / 180 ;
	}

	private double toMeter(double diffR)
	{

		return Math.sin(diffR) * EARTH_RADIUS;
	}


	/**
	 * This method checks whether the coordinates are valid
	 * @return true if this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p - The point that we will receive
	 * @return - an answer true if this is a valid point else if not
	 */

	public boolean isValid_GPS_Point(Point3D p)
	{
		boolean isValid = true ;

		if(p.x() < -90 || p.x() > 90)
		{
			isValid = false ;
		}
		else if(p.y() < -180 || p.y() > 180)
		{
			isValid = false ;
		}
		else if (p.z() < -450 )
		{
			isValid = false ;
		}
		return isValid ;
	}




	public static void main(String[] args) {
		MyCoords coords = new MyCoords();	
		Map map= new Map() ;

		Point3D gps0 = new Point3D(2,2);
		Point3D gps1 = new Point3D(1,1);

		System.out.println(coords.add(gps0, gps1));	

	}

}
