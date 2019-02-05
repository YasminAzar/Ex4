package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Algorithms.AlgoEx4;
import Algorithms.ShortestPathAlgo;
import GIS.Box;
import GIS.Fruit;
import GIS.Game;
import GIS.Ghost;
import GIS.Pacman;
import GIS.Player;
import Geom.Point3D;
import Robot.Play;
import Threads.ThreadGhost;
import Threads.ThreadPacman;
import Threads.ThreadPlayer;

/**
 * This is a graphical class that allows the robots and fruits to be displayed on the map,
 * The activity of algorithms, the preservation of data, and the performance of data recovery from files of csv  
 * or Creating a "game" by selecting robots and fruits and placing them on the map
 * @author Yasmin and Dan
 *
 */
public class MyGUI extends JFrame implements MouseListener , ComponentListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public	 int WIDTH = 1433 ;
	public  int HEIGHT = 642 ; 

	private final double DEFULT_SPEED = 1;

	File mapFile                = new File("Ariel1comic.png ");
	File fruit1File             = new File("Images/fruits.png");
	String clickedFruitImage    ="Images/fruits/red.png" ;
	public BufferedImage arielMap ;
	BufferedImage pacmanOrFruitImage ;
	Dimension dimension ;
	paintFruits fruitPanel ;
	Game game = new Game() ;
	MenuBar menuBar  ;
	Menu openOrSave  ;		
	Menu drawMenu ;
	MenuItem openItem     ;
	MenuItem saveItem     ;
	MenuItem fruitItem_1 ;
	MenuItem playerItem ;
	MenuItem pacmenItem  ;
	MenuItem boxItem ;
	private int mapWidth;
	private int mapHeight;
	boolean pacmanOn    ;
	boolean fruit_1_On  ;
	boolean player_On   ;
	boolean box_On      ;
	boolean pathReady   ;
	boolean paintFruit  ;
	boolean playerClick ;
	boolean ex3         ;
	boolean ex4         ;
	double widthRatio   ;
	double heightRatio  ;
	private int fruitIdCounter;
	private int pacmanIdCounter;
	Eatting_effect effect ;
	MySQL sql=new MySQL();
	Play EX4play ;
	private boolean firstTime;

	/**
	 * Create the frame.
	 */
	public MyGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init() ;
		getContentPane().addMouseListener( this);
	}


	 void init()
	{
		effect = new Eatting_effect();
		pacmanOn   = false ;
		fruit_1_On = false ;
		box_On     = false ;
		pathReady  = false ;
		playerClick = false;
		paintFruit = true  ;
		ex3 = true         ;
		ex4 = true         ;
		firstTime = true   ;
		MenuBar menuBar = new MenuBar() ;
		Menu openOrSave = new Menu("file") ;		

		MenuItem loadEx3    = new MenuItem("Ex3 games" ) ;
		MenuItem loadEx4    = new MenuItem("Ex4 games" ) ;
		saveItem    = new MenuItem("Save") ;

		Menu drawMenu = new Menu("Options");
		pacmenItem   = new MenuItem("Pacman");
		fruitItem_1  = new MenuItem("Fruit");
		playerItem = new MenuItem("Player") ;

		boxItem  = new MenuItem("Box") ;
		Menu play = new Menu("menu");
		MenuItem playGame = new MenuItem("Play");
		fruitPanel = new paintFruits(this);
		fruitPanel.setSize(new Dimension(WIDTH, HEIGHT));
		openOrSave.add(loadEx3);
		openOrSave.add(loadEx4) ;
		drawMenu.add(pacmenItem);
		drawMenu.add(fruitItem_1);
		drawMenu.add(playerItem);
		drawMenu.add(boxItem);
		play.add(playGame) ;
		play.add(saveItem);
		menuBar.add(openOrSave);
		menuBar.add(drawMenu);
		menuBar.add(play);
		this.add(fruitPanel) ;
		this.setMenuBar(menuBar);	
		setTitle("Pacman");    
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		pack();

		try {
			arielMap  = ImageIO.read(mapFile);
			mapWidth  = arielMap.getWidth() ;
			mapHeight = arielMap.getHeight() ;
			setPreferredSize(new Dimension(mapWidth, mapHeight));
		} catch (IOException e) {
			e.printStackTrace();
		}


		loadEx3.addActionListener(e ->
		{
			ex3 = true  ;
			ex4 = false ;
			JFileChooser fc = new JFileChooser() ; 
			JButton open = new JButton() ;
			fc.setCurrentDirectory(new java.io.File("Games/ex3_games"));
			if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION )
			{		
				String gamePath = fc.getSelectedFile().getAbsolutePath();
				game = new Game(gamePath);
				repaint();
			}

		});

		loadEx4.addActionListener(e ->
		{
			ex4 = true  ;
			ex3 = false ;
			JFileChooser fc = new JFileChooser() ; 
			JButton open = new JButton() ;
			fc.setCurrentDirectory(new java.io.File("Games/ex4_games"));
			if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION )
			{				
				String gamePath = fc.getSelectedFile().getAbsolutePath();
				//EX4play = new Play(gamePath) ;
				game = new Game(gamePath);
				repaint();
			}

		});

		playGame.addActionListener(e ->
		{
			play();
		});
		
		playerItem.addActionListener(e -> 
		{
			pacmanOn = false   ;
			fruit_1_On = false ;
			box_On = false ;
			playerClick = true;

		});
		
		pacmenItem.addActionListener(e ->{
//			System.out.println("in pacman");
			fruit_1_On = false ;
			box_On = false ;
			if(pacmanOn == true)
			{
				pacmanOn = false ;
			}
			else if(pacmanOn == false)
			{
				pacmanOn = true ;
			}
			repaint();

		});

		fruitItem_1.addActionListener(e ->{
			pacmanOn   = false ;
			box_On = false ;
			if(fruit_1_On == true)
			{
				fruit_1_On = false ;
			}
			else if(fruit_1_On == false)
			{
				fruit_1_On = true ;
			}
		});


		boxItem.addActionListener(e ->{
			pacmanOn   = false ;
			fruit_1_On = false ;
			if(box_On  == true)
			{
				box_On = false ;
			}
			else if(box_On == false)
			{
				box_On = true ;
			}
		});		 

	}

	public Eatting_effect getEffect() {
		return effect;
	}

	/**
	 * This method draws the elements
	 * @param Graphics
	 */
	public void paint(Graphics g)
	{
		Image image = createImage(2000,2000);
		Image effectI = createImage(5000,5000);
		Graphics g1 = image.getGraphics();
		Graphics g2 = effectI.getGraphics();
		if(effect.activate == true)
		{
			g2.drawImage(arielMap,10 ,55, this.getWidth()-17, this.getHeight()-64, this) ;
		}
		else
			g1.drawImage(arielMap,10 ,55, this.getWidth()-17, this.getHeight()-64, this) ;

		for (Box box : game.boxSet) 
		{
			if(effect.activate == true)
			{          
				
				g2.drawImage(box.getBoxImage(), (box.getLowerPoint().ix())*this.getWidth()/WIDTH, (box.getUpperPoint().iy()+44)* this.getHeight()/HEIGHT , box.getBoxWidth(), box.getBoxHeight(), this);
				
			}
			else
				g1.drawImage(box.getBoxImage(), (box.getLowerPoint().ix())*this.getWidth()/WIDTH, (box.getUpperPoint().iy()+44)* this.getHeight()/HEIGHT , box.getBoxWidth(), box.getBoxHeight(), this);

		}
		for (Ghost ghost : game.ghostSet) 
		{
			if(effect.activate == true)
			{
				g2.drawImage(ghost.getG_image(), ghost.getG_point().ix()*this.getWidth()/WIDTH , (ghost.getG_point().iy()+44) *this.getHeight()/HEIGHT, 50, 50, this) ;
			}
			else
				g1.drawImage(ghost.getG_image(), ghost.getG_point().ix()*this.getWidth()/WIDTH , (ghost.getG_point().iy()+44) *this.getHeight()/HEIGHT, 50, 50, this) ;

		}

		if(effect.activate == true)
		{

			int x = effect.getEpoint().ix()*this.getWidth()/WIDTH ;
			int y = effect.getEpoint().iy()*this.getHeight()/HEIGHT;
			g2.drawImage(effect.getE_image(),x,y,50,50,this);			
		}

		for (Pacman pacman : game.pacmanSet) 
		{
			AffineTransform at = AffineTransform.getTranslateInstance(pacman.getP_Location().ix()*this.getWidth()/WIDTH, (pacman.getP_Location().iy())*this.getHeight()/HEIGHT);
			at.rotate(Math.toRadians(45));
			Graphics2D g2d = (Graphics2D) g ;
			if(effect.activate == true)
			{
				g2.drawImage(pacman.getP_Image(), pacman.getP_Location().ix()*this.getWidth()/WIDTH, (pacman.getP_Location().iy()+44)*this.getHeight()/HEIGHT,25,25, this);
			}
			else
				g1.drawImage(pacman.getP_Image(), pacman.getP_Location().ix()*this.getWidth()/WIDTH, (pacman.getP_Location().iy()+44)*this.getHeight()/HEIGHT,25 , 25, this);
		}
		
		Player player = game.getPlayer() ;
		if(playerClick == true)
		{
			g1.drawImage(player.getP_Image(), player.getPlayerLocation().ix()*this.getWidth()/WIDTH, (player.getPlayerLocation().iy()+44)*this.getHeight()/HEIGHT,30 , 30, this);
		
			
			g2.drawImage(player.getP_Image(), player.getPlayerLocation().ix()*this.getWidth()/WIDTH, (player.getPlayerLocation().iy()+44)*this.getHeight()/HEIGHT,30,30, this);
		}

		for (Fruit fruit : game.fruitSet) 
		{
			int x = fruit.getFruitLocation().ix()*this.getWidth()/WIDTH ;
			int y = (fruit.getFruitLocation().iy()+44)*this.getHeight()/HEIGHT;
			int width  = 20  ;
			int height = 20 ;
			BufferedImage fruitImage = fruit.getFruitImage() ;
			if(effect.activate == true)
			{
				g2.drawImage(fruitImage,x , y, width, height, this) ;
			}
			else
				g1.drawImage(fruitImage,x , y, width, height, this) ;

		}

		if(effect.activate == true)
		{
			g.drawImage(effectI,0,0,this);
		}
		else
			g.drawImage(image,0,0,this);

	}

/**
 * This method defines what game plays ( Ex3 or Ex4)
 */
	public void play()
	{
		if(ex3 == true)
		{
//			ShortestPathAlgo spa = new ShortestPathAlgo(game);
			pathReady = true ;
			for (Pacman pacman : game.pacmanSet) 
			{
				int index = game.pacmanSet.indexOf(pacman);
				System.out.println(game.pacmanSet.get(index).getP_Path().toString());
			}

//			Thread th = new Thread();
			for (Pacman pacman : game.pacmanSet) 
			{
				ThreadPacman tp = new ThreadPacman(this, WIDTH, HEIGHT , pacman);
				tp.start();
			}

		}
		else if(ex4 == true)
		{
//			AlgoEx4 algo4 = new AlgoEx4();
			
			EX4play.setIDs(203526132 , 203336474);
			String map_data = EX4play.getBoundingBox() ;
			System.out.println("Bounding Box info: "+map_data);

			ArrayList<String> board_data = EX4play.getBoard();
			for(int i=0;i<board_data.size();i++) 
			{
				System.out.println(board_data.get(i));
			}
//			Thread th = new Thread();
			pathReady = true ;
			for (Pacman pacman : game.pacmanSet) 
			{
				int index = game.pacmanSet.indexOf(pacman);
				System.out.println(game.pacmanSet.get(index).getP_Path().toString());

			}
			
			for (Pacman pacman : game.pacmanSet) 
			{
				ThreadPacman tp1 = new ThreadPacman(this, WIDTH, HEIGHT , pacman);
				tp1.start();

			}
			
			for (Ghost ghost : game.ghostSet) 
			{
				ThreadGhost tg = new ThreadGhost(this , ghost) ;
				tg.start();
			}
			
		}
		System.out.println(sql.toString());
	}

	public void drawPath(Pacman pacman){}

	public Game getGame()
	{
		return game ;
	}

	public int getMapHeight()
	{
		return HEIGHT ;
	}

	public int getMapWidth()
	{
		return WIDTH ;
	}
	public void setPaintFruit()
	{
		paintFruit = true ;
	}
	@Override
	public void componentResized(ComponentEvent e) {
		widthRatio   = e.getComponent().getWidth() /WIDTH  ;
		heightRatio  = e.getComponent().getHeight() /HEIGHT ;
		repaint();
		WIDTH =  e.getComponent().getWidth() ;
		HEIGHT = e.getComponent().getHeight() ;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(firstTime == true)
		{
			System.out.println("player was clicked");
			Point3D PlayerClicked = new Point3D(e.getX() ,e.getY() ) ;
			game.setPlayer(PlayerClicked);
			firstTime = false ;
			playerClick = true ;
			
			for (Pacman pacman : game.pacmanSet) 
			{
				ThreadPacman tp = new ThreadPacman(this, WIDTH, HEIGHT , pacman);
				tp.start();
			}
			for (Ghost ghost : game.ghostSet) 
			{
				ThreadGhost tg = new ThreadGhost(this , ghost) ;
				tg.start();
			}
//			repaint() ;
		}
		else  
		{
			
			Point3D playerdir = new Point3D(e.getX() ,e.getY() ) ;		
			game.getPlayer().setDirection(playerdir);

			ThreadPlayer tplayer = new ThreadPlayer(this);
			tplayer.start();
			for (Pacman pacman : game.pacmanSet) 
			{
				int index = game.pacmanSet.indexOf(pacman);
				System.out.println(game.pacmanSet.get(index).getP_Path().toString());

			}
			for (Pacman pacman : game.pacmanSet) 
			{
				ThreadPacman tp = new ThreadPacman(this, WIDTH, HEIGHT , pacman);
				tp.start();
			}
			for (Ghost ghost : game.ghostSet) 
			{
				ThreadGhost tg = new ThreadGhost(this , ghost) ;
				tg.start();
			}
			
		}
		if(pacmanOn == true)
		{
			Pacman  pacman = new Pacman(pacmanIdCounter , e.getX()*WIDTH/this.getWidth() , e.getY()*HEIGHT/this.getHeight() , DEFULT_SPEED) ;
			System.out.println(pacman.getP_Location());
			game.pacmanSet.add(pacman) ;
			repaint();
		}

		else if(fruit_1_On)
		{
			Fruit fruit = new Fruit(fruitIdCounter , e.getX()*WIDTH/this.getWidth() , e.getY()*HEIGHT/this.getHeight() ,clickedFruitImage ) ;
			game.fruitSet.add(fruit) ;
			repaint();
		}
		}
	

	@Override
	public void componentMoved(ComponentEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void componentShown(ComponentEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void componentHidden(ComponentEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void mousePressed(MouseEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void mouseReleased(MouseEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void mouseEntered(MouseEvent e) {}
		// TODO Auto-generated method stub

	@Override
	public void mouseExited(MouseEvent e) {}
		// TODO Auto-generated method stub
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					MyGUI frame = new MyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
	}

}
