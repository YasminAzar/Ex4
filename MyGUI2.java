package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.Cords;
import Coords.GeoBox;
import Coords.LatLonAlt;
import Robot.Fruit;
import Robot.Game;
import Robot.Packman;
import Robot.Play;

public class MyGUI2<JMenuActions> extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	// private variables
	private Container window;
	private JPanel _panel;
	private Graphics _paper;
	private int x, y;
	private boolean isGamer;
	private boolean flag=true;

	private Robot ro;
	private Packman pac;
	private Cords coo;
	private LatLonAlt LLA;
	private GeoBox box;
	public MouseListener mousePressed;

	private ArrayList<Packman> pacmanArray;
	private ArrayList<Packman> ghostArray;
	private ArrayList<Fruit> fruitArray;
	private ArrayList<GeoBox> boxes;
	private Play play;
	private Game game;
	private Box box1;
	private Packman myPacgame;
	private Fruit myFrugame;

	public MyGUI2(){
		super("Map Demo"); //setTitle("Map Counter");  // "super" Frame sets its title
		//	Moves and resizes this component. 
		//	The new location of the top-left corner is  specified by x and y, 
		//	and the new size is specified by width and height
		//	setBounds(x,y,width,height)
		this.setBounds(0,0,1433,700); //setSize(1433,700);        // "super" Frame sets its initial window size
		//      Exit the program when the close-window button clicked
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();

	}

	public MyGUI2(String csv_file_name) {
		String line = "";
		String cvsSplitBy = ",";
		this.pacmanArray = new ArrayList<Packman>();
		this.fruitArray = new ArrayList<Fruit>();
		this.ghostArray = new ArrayList<Packman>();
		this.boxes = new ArrayList<GeoBox>();
		//new Game(this.fruitArray, this.pacmanArray );

		try (BufferedReader br = new BufferedReader(new FileReader(new File(csv_file_name)))) 
		{
			int counter = 0;
			br.readLine();

			while ((line = br.readLine()) != null) //if the third line in the read file is not empty, read from it
			{
				String[] userInfo = line.split(cvsSplitBy); //userInfo is an array of all the information in a row
				if(counter > 0) {
					LatLonAlt cordinate = new LatLonAlt(userInfo[3]+","+userInfo[2]+","+userInfo[4]);

					if(userInfo[0].contains("P")) {
						Packman pacman = new Packman(cordinate , Double.parseDouble(userInfo[5]));
						pacmanArray.add(pacman);
					}
					else if (userInfo[0].contains("F")){
						Fruit fruit = new Fruit(cordinate);
						fruitArray.add(fruit);
					}
					else if (userInfo[0].contains("G")) {
						Packman ghost = new Packman(cordinate , Double.parseDouble(userInfo[5]));
						ghostArray.add(ghost);
					}
					else {
						LatLonAlt cordinate_p2= new LatLonAlt(userInfo[6]+","+userInfo[5]+","+userInfo[7]);
						GeoBox box = new GeoBox(cordinate , cordinate_p2);
						boxes.add(box);
						paintElement();


					}

				}
				counter++;
			}
		}

		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	LatLonAlt cordinate = this.cordinate;
	LatLonAlt cordinate_p2 = this.cordinate_p2;

	public void createGui(){              				
		//	A Container is a component  that can contain other GUI components
		window = this.getContentPane(); 
		window.setLayout(new FlowLayout());

		//	Add "panel" to be used for drawing            
		_panel = new JPanel();
		Dimension d= new Dimension(1433,642);
		_panel.setPreferredSize(d);		               
		window.add(_panel);

		// A menu-bar contains menus. A menu contains menu-items (or sub-Menu)
		JMenuBar menuBar;   // the menu-bar
		JMenu menu;         // each menu in the menu-bar
		JMenuItem menuItem1, menuItem2, Csv_read; // an item in a menu
		JMenuActions menuStop,menuPlay,menuclaer;
		menuBar = new JMenuBar();


		//		menu=new JMenu("play");
		//		menu.setMnemonic(KeyEvent.VK_A);
		//		menu.add(menu);
		//		menu=new JMenu("Clear");
		//		menu.setMnemonic(KeyEvent.VK_A);
		//		menu.add(menu);
		menu=new JMenu("options");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		menuItem1 = new JMenuItem("Stop", KeyEvent.VK_F);
		menu.add(menuItem1); // the menu adds this item
		menuItem1.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer=false;
			}
		});
		menuItem1 = new JMenuItem("Play", KeyEvent.VK_F);
		menu.add(menuItem1); // the menu adds this item
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = true;
				//paintElement();
			}
		});
		menuItem1 = new JMenuItem("Clear", KeyEvent.VK_F);
		menu.add(menuItem1); // the menu adds this item
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = false;
			}
		});
		// First Menu
		menu = new JMenu("Menu A");
		menu.setMnemonic(KeyEvent.VK_A);  // alt short-cut key
		menuBar.add(menu);  // the menu-bar adds this menu

		menuItem1 = new JMenuItem("Pacman", KeyEvent.VK_F);
		menu.add(menuItem1); // the menu adds this item
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = true;
			}
		});
		menuItem2 = new JMenuItem("Fruit", KeyEvent.VK_S);
		menu.add(menuItem2); // the menu adds this item
		menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = false;
			}
		});                     
		setJMenuBar(menuBar);  // "this" JFrame sets its menu-bar

		// Prepare an ImageIcon
		String imgMapFilename = "Ariel1.png";    
		ImageIcon imgBck = new ImageIcon(getClass().getResource(imgMapFilename));
		JLabel labelMap = new JLabel();
		labelMap.setIcon(imgBck);
		_panel.add(labelMap);

		// panel (source) fires the MouseEvent.
		//panel adds "this" object as a MouseEvent listener.
		_panel.addMouseListener(this);

		Csv_read = new JMenuItem("Play1", KeyEvent.VK_F);
		menu.add(Csv_read); // the menu adds this item
		Csv_read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select an Csv File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile().getPath());
					game theGame = new game(pacmanArray,fruitArray);
					theGame.setfile_dir(fileChooser.getSelectedFile().getPath());
					try {
						theGame.Csvread();
						myPacgame.pacmanArray = theGame.pacmanArray;
						myFrugame.Fruits_arr = theGame.Fruits_arr;
						myPacgame.file_directory = theGame.file_directory;
						myFrugame.file_directory = theGame.file_directory;
						isGamer = 2;

						repaint();

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
	}



	protected void paintElement() {
		//	The method getGraphics is called to obtain a Graphics object
		_paper = _panel.getGraphics();
		if(isGamer){
			//			_paper.setColor(Color.PINK);
			//			_paper.fillOval(x,y,20,20);
			//			Point3D p3=new Point3D(box.getMax().lon(),box.getMin().lat(), 0);
			_paper.setColor(Color.YELLOW);
			_paper.fillRect((int)box1.p3().x(), (int)box1.p3().y(), (int)box1.width(cordinate, cordinate_p2), (int)box1.height(cordinate, cordinate_p2));

		} 
		else {
			play.stop();

		}
		_paper.setFont(new Font("Monospaced", Font.PLAIN, 14));               
		_paper.drawString("("+Integer.toString(x)+", "+Integer.toString(y)+")",x,y-10);

	}

	@Override
	public void mousePressed(MouseEvent event) {
		//		if(isGamer){
		x = event.getX();
		y = event.getY();  
		paintElement();

	}
	// Not Used, but need to provide an empty body for compilation
	public void mouseReleased(MouseEvent event){}
	public void mouseClicked(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}

	public static void main(String[] args) {

		String csv_file_name="data/Ex4_OOP_example1.csv";
		MyGUI frame = new MyGUI(csv_file_name);
		frame.setBounds(0, 0, 1433, 642);
		frame.createGui();
		frame.setVisible(true);
		//frame.paintElement();
	}




}
