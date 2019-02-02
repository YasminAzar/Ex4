EX_4 version 1.0 – Pacman Game
Object Oriented course 
This project is an example for using github as part of the Object Oriented course in Ariel University.
This project is a "Pacman" game that runs on a map of part of the city Ariel. It takes the bounds of the map in coordinates, and an image of the map. In this project you can either draw pacmans and fruits, or load csv files with coordinates, types, and other data of the pacmans and the fruits. When the game is finished, you can save the results in a KML file that fits GoogleEarth.
Explanation of our Packages and Classes- For more detailed explanations please read the Java docs:
Algorithms:
•	ShortAlgoEx4- This class is made to calculate the shortest path from one Pacman or more. The calculation of the shortest path is also based on time. When all the paths are ready the game will start and the Pacmans will start moving.
•	createGameFromCsv- This class creates a game from a CSV file.
•	SaveGameToCsv- This class saved the game to a CSV file.
Coords:
•	coords_converter - This interface represents a basic coordinate system converter, including:
1.	The 3D vector between two lat,lon, alt points
2.	Adding a 3D vector in meters to a global point.
3.	convert a 3D vector from meters to polar coordinates
•	MyCoords - A class that implements coords_conveter. 
File_Format:
•	Path2KML- This class converts the game results so it can be presented on googlEarth, with a timeline. It displays the pacmans and the fruits, in the order they were created, and than the order the pacmans eat the fruits.
GIS:
•	Pacman - Represents a pacman with ID, coordinates, eating radius and speed.

•	Fruit - Represents a fruit with ID, weight, and coordinates.
•	Box – Represents a box with height, width and coordinates.
•	Ghost - Represents a ghost with ID, coordinates and speed.
•	Player – Represents the player with ID, coordinates, eating radius and speed.
•	ConvertGPStoPIX - Converts GPS coordinates to PIXELS.
•	Game - contains an ArrayList of Fruit and another one for Pacman. It can be built from a csv file with the function csvToGame, and you can insert a game data to a csv file, with the function GameToCsv.
Geom:
•	Geom_element - This interface represents distance in 2 and 3 dimensions.
•	Point3D - This class represents a 3D point in space.
•	Map – This class displays the map we defined (in our game this is a picture of part of the city of Ariel). In this class we also calculated distances between points in pixels, angle between points and conversion from coordinates to pixel and vice versa. 
GUI:
•	MyGUI- This class extends JFrame and implements MouseListener and ComponentListener. Is a GUI that allows the user to play the game. It allows the user to draw fruits and pacman. By clicking once on fruit or pacman, it will allow you to start drawing, By clicking "Play" the game will start. By clicking "save" this will save the results of the game as a kml file that can be displayed on google earth with a timeline.
•	MySQL - This class copies the information generated from the games to SQL.
Tutorial To start the program: 
clone the Project -> src -> GUI -> Run the class MyGUI -> now you have 2 options: 
1) To build the game alone, click on Options and then select the creature you want to put on the screen and when you finish drawing you must put your player and then the game will begin. 
2) To load an existing game, click on file -> Ex4 games then select the desired game. After a built-in game you have to put your player on the screen and then the game will begin.








