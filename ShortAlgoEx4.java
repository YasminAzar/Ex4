package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import GIS.Box;
import GIS.Fruit;
import GIS.Game;
import GIS.Pacman;
import GIS.Player;
import GUI.MyGUI;
import Geom.Point3D;
import Robot.Play;
import Threads.ThreadPacman;
import Threads.ThreadPlayer;

/**
 *This class receives GAME (a collection of fruit and pecmans) and calculates the optimal route,
 *so that all fruits will eat as quickly as possible.
 *The goal of the algorithm is to minimize the amount of time it takes for all the pecmans 
 *to eat the fruits.
 *The matod:
 *Eat the fruits in the shortest time.
 *Do not touch the black boxes. The method we do this is:
 *We will consider the shortest time from our player for any fruit and send our player to this fruit.
 *We will perform Section 1 until we eat all the fruits.
 *In addition, before we want to go to the fruit we will check "Is the road free of boxes?" 
 *If the boxes disturb the top, then move to the right and then to the left, if it interferes from below,
 *we will move to the right and again to the right, if the box interferes with the left we will go up
 *and to the left and if the box interferes on the right, We will go up and to the right.
 *@author Yasmin and Dan
 */

public class ShortAlgoEx4 {

	/** 
	 * Every pacman finds the fruit closest to it and goes to eat it like this until all the fruits are eaten
	 * @param game
	 */
	Game game = new Game() ;

	public ShortAlgoEx4(Game game){

		Fruit chosenFruit ;
		Player player=new Player();
		Pacman pacman=new Pacman();
		Play play1=new Play();
		int newY = pacman.getP_Location().iy() ;
		int newX = pacman.getP_Location().ix() ;
		while(play1.isRuning())
		{
			
			double tempDistance, minDistance = Double.MAX_VALUE ;
			int minfruitIndex = 0 ;
			for (Fruit fruit : game.fruitSet) //find the index of the closet fruit
			{
				
				tempDistance = player.TimeToFruit(fruit) ;
				if(minDistance > tempDistance)
				{
					minDistance = tempDistance ;
					minfruitIndex = game.fruitSet.indexOf(fruit);
				}
				Point3D p1;
				Point3D p2;
				for(Box box1: game.boxSet)
				{
					
					if(box1.getUpperPoint().ix()==player.getPlayerLocation().ix())
							{
								newX=player.getPlayerLocation().ix()-1;
							}
					else if(box1.getUpperPoint().iy()==player.getPlayerLocation().iy())
							{
								newY=player.getPlayerLocation().iy()+1;
							}
					else if(box1.getLowerPoint().ix()==player.getPlayerLocation().ix())
							{
								newX=player.getPlayerLocation().ix()+1;
							}
					else if(box1.getLowerPoint().iy()==player.getPlayerLocation().iy())
							{
								newX=player.getPlayerLocation().iy()-1;
							}
					else
					{
						 newY = pacman.getP_Location().iy() ;
						 newX = pacman.getP_Location().ix() ;
					}
					
				}
			}
			chosenFruit = game.fruitSet.get(minfruitIndex);
			pacman.getP_Path().getFruitsPath().add(chosenFruit) ;
			chosenFruit.setChosedToPath();
		}
		}

	
				public void start(MyGUI mygui)
				{
					
					Thread th = new Thread();


						ThreadPlayer tp = new ThreadPlayer(mygui);

						tp.start();

				}
			}
