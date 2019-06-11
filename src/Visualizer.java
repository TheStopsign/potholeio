import java.awt.EventQueue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import net.miginfocom.swing.MigLayout;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import java.awt.Canvas;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

public class Visualizer {
	
	public Hole[] holes = new Hole[50];

	private JFrame frmPotholeVisualizer;
	private final ButtonGroup visualTypes = new ButtonGroup();
	private final ButtonGroup modes = new ButtonGroup();
	private JLabel roadsImg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizer window = new Visualizer();
					window.frmPotholeVisualizer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Visualizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ArrayList<JRadioButton> chartTypes = new ArrayList<JRadioButton>();
		ArrayList<JCheckBox> dataTypes = new ArrayList<JCheckBox>();
		JRadioButton rdbtnMap = new JRadioButton("Map");
		JRadioButton rdbtnHeatmap = new JRadioButton("Heatmap");
		JRadioButton rdbtnBarGraph = new JRadioButton("Bar Graph");
		JCheckBox optionHoleSize = new JCheckBox("Hole Diameter");
		JCheckBox optionHoleDepth = new JCheckBox("Hole Depth");
		JCheckBox optionTraffic = new JCheckBox("Traffic Level");
		JButton showSettings = new JButton("M e n u");
		JButton hideSettings = new JButton("M e n u");
		
		ImageIcon tmp = new ImageIcon("src/troymap_roads.png");
		Image tmp2 = tmp.getImage().getScaledInstance((int)(3332*.45), (int)(2076*.45),  java.awt.Image.SCALE_SMOOTH);
		ImageIcon tmp3 = new ImageIcon(tmp2);
		roadsImg = new JLabel(tmp3);
		
		
		Color bckgndColor = SystemColor.desktop;
		
		//Tabbed Pane graphics fixing
		UIManager.put("TabbedPane.selected", bckgndColor);
		Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
		insets.top = 4; insets.left = 0; insets.bottom = 0; insets.right = 0;
		UIManager.put("TabbedPane.contentBorderInsets", insets);
		
		frmPotholeVisualizer = new JFrame();
		frmPotholeVisualizer.getContentPane().setForeground(Color.WHITE);
		frmPotholeVisualizer.setBackground(bckgndColor);
		frmPotholeVisualizer.getContentPane().setBackground(bckgndColor);
		frmPotholeVisualizer.setTitle("Pothole Visualizer");
		frmPotholeVisualizer.setBounds(100, 100, 1920, 1080);
		frmPotholeVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPotholeVisualizer.getContentPane().setLayout(null);
		
		JPanel renderContainer = new JPanel();
		renderContainer.setBounds(0, 0, 1920, 1000);
		renderContainer.setBorder(null);
		renderContainer.setBackground(bckgndColor);
		renderContainer.setLayout(null);
		frmPotholeVisualizer.getContentPane().add(renderContainer);
		
		RenderWindow renderWindow = new RenderWindow(chartTypes,dataTypes);
		renderWindow.setBounds(0, 0, 1920, 1000);
		renderWindow.setBorder(null);
		renderWindow.setOpaque(false);
		renderWindow.setLayout(null);
		
		JPanel dataToolbar = new JPanel();
		dataToolbar.setBounds(12, 12, 279, 966);
		dataToolbar.setForeground(Color.WHITE);
		dataToolbar.setBackground(bckgndColor);
		dataToolbar.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		renderContainer.add(dataToolbar);
		dataToolbar.setLayout(null);
		dataToolbar.hide();
		renderContainer.add(showSettings);
		renderContainer.add(renderWindow);
		
		////////////////////////////////////////////////////////////////////////
		//Chart Options
		////////////////////////////////////////////////////////////////////////
		
		
		JLabel lblCharts = new JLabel("Charts");
		lblCharts.setForeground(Color.WHITE);
		lblCharts.setHorizontalAlignment(SwingConstants.LEFT);
		lblCharts.setFont(new Font("Dialog", Font.BOLD, 24));
		lblCharts.setBounds(36, 95, 121, 36);
		dataToolbar.add(lblCharts);
		
		optionHoleSize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderWindow.render();
			}
		});
		
		rdbtnMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderWindow.render();
				optionTraffic.hide();
			}
		});
		rdbtnMap.setSelected(true);
		rdbtnMap.setForeground(Color.WHITE);
		rdbtnMap.setBackground(bckgndColor);
		visualTypes.add(rdbtnMap);
		rdbtnMap.setFont(new Font("Dialog", Font.BOLD, 16));
		rdbtnMap.setBounds(36, 135, 144, 23);
		dataToolbar.add(rdbtnMap);
		
		rdbtnHeatmap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roadsImg.show();
				renderWindow.render();
				optionTraffic.hide();
			}
		});
		rdbtnHeatmap.setForeground(Color.WHITE);
		rdbtnHeatmap.setBackground(bckgndColor);
		visualTypes.add(rdbtnHeatmap);
		rdbtnHeatmap.setFont(new Font("Dialog", Font.BOLD, 16));
		rdbtnHeatmap.setBounds(36, 162, 144, 23);
		dataToolbar.add(rdbtnHeatmap);
		
		rdbtnBarGraph.setBounds(36, 189, 144, 23);
		rdbtnBarGraph.setForeground(Color.white);
		rdbtnBarGraph.setBackground(bckgndColor);
		rdbtnBarGraph.setFont(new Font("Dialog", Font.BOLD, 16));
		visualTypes.add(rdbtnBarGraph);
		dataToolbar.add(rdbtnBarGraph);
		
		JLabel lblData = new JLabel("Data");
		lblData.setForeground(Color.WHITE);
		lblData.setHorizontalAlignment(SwingConstants.LEFT);
		lblData.setFont(new Font("Dialog", Font.BOLD, 24));
		lblData.setBounds(36, 236, 121, 36);
		dataToolbar.add(lblData);
		
		optionHoleSize.setForeground(Color.WHITE);
		optionHoleSize.setBackground(bckgndColor);
		optionHoleSize.setFont(new Font("Dialog", Font.BOLD, 16));
		optionHoleSize.setBounds(36, 280, 155, 23);
		dataToolbar.add(optionHoleSize);
		
		optionHoleDepth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderWindow.render();
			}
		});
		optionHoleDepth.setForeground(Color.WHITE);
		optionHoleDepth.setBackground(bckgndColor);
		optionHoleDepth.setFont(new Font("Dialog", Font.BOLD, 16));
		optionHoleDepth.setBounds(36, 307, 126, 23);
		dataToolbar.add(optionHoleDepth);
		
		showSettings.setBounds(12, 12, 279, 61);
		showSettings.setFont(new Font("Dialog", Font.BOLD, 20));
		showSettings.setForeground(SystemColor.desktop);
		showSettings.setBackground(Color.WHITE);
		showSettings.setOpaque(true);
		showSettings.setBorderPainted(false);
		
		hideSettings.setFont(new Font("Dialog", Font.BOLD, 20));
		hideSettings.setForeground(SystemColor.desktop);
		hideSettings.setBackground(Color.WHITE);
		hideSettings.setBounds(0, 0, 279, 60);
		dataToolbar.add(hideSettings);
		hideSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dataToolbar.hide();
				showSettings.setOpaque(true);
			}
		});
		hideSettings.setOpaque(true);
		hideSettings.setBorderPainted(false);
		
		optionTraffic.setForeground(Color.WHITE);
		optionTraffic.setFont(new Font("Dialog", Font.BOLD, 16));
		optionTraffic.setBackground(SystemColor.desktop);
		optionTraffic.setBounds(36, 334, 144, 23);
		dataToolbar.add(optionTraffic);
		optionTraffic.hide();
		
		
		showSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dataToolbar.show();
				showSettings.setOpaque(false);
			}
		});
		
		JPanel navigationWindow = new JPanel();
		navigationWindow.setBounds(0, 0, 1920, 1000);
		frmPotholeVisualizer.getContentPane().add(navigationWindow);
		navigationWindow.setBorder(null);
		navigationWindow.setBackground(bckgndColor);
		navigationWindow.hide();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Noto Serif CJK JP", Font.BOLD, 14));
		menuBar.setForeground(SystemColor.desktop);
		menuBar.setBackground(SystemColor.desktop);
		frmPotholeVisualizer.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Noto Serif CJK JP", Font.BOLD, 14));
		mnFile.setBackground(SystemColor.desktop);
		mnFile.setForeground(Color.WHITE);
		menuBar.add(mnFile);
		
		JMenu mode = new JMenu("Mode");
		mode.setFont(new Font("Noto Serif CJK JP", Font.BOLD, 14));
		mode.setForeground(Color.WHITE);
		mode.setBackground(SystemColor.desktop);
		menuBar.add(mode);
		
		JRadioButton rdbtnDataVisualization = new JRadioButton("Data Visualizer");
		rdbtnDataVisualization.setSelected(true);
		rdbtnDataVisualization.setForeground(SystemColor.desktop);
		rdbtnDataVisualization.setBackground(Color.WHITE);
		rdbtnDataVisualization.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderContainer.show();
				navigationWindow.hide();
			}
		});
		modes.add(rdbtnDataVisualization);
		mode.add(rdbtnDataVisualization);
		
		JRadioButton rdbtnNavigation = new JRadioButton("Route Planner");
		rdbtnNavigation.setForeground(SystemColor.desktop);
		rdbtnNavigation.setBackground(Color.WHITE);
		rdbtnNavigation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderContainer.hide();
				navigationWindow.show();
			}
		});
		modes.add(rdbtnNavigation);
		mode.add(rdbtnNavigation);
		
		chartTypes.add(0,rdbtnMap);	chartTypes.add(1,rdbtnHeatmap); chartTypes.add(2,rdbtnBarGraph);
		dataTypes.add(0,optionHoleSize);	dataTypes.add(1,optionHoleDepth);	dataTypes.add(2,optionTraffic);
		
	roadsImg.setFont(new Font("Dialog", Font.BOLD, 24));
	roadsImg.setBounds(0, 0, 1920, 1000);
	renderContainer.add(roadsImg);
		
//		renderWindow.paintComponent(renderWindow.getGraphics());
	}
	
	class RenderWindow extends JPanel {
		
		ArrayList<JRadioButton> chartTypes;
		ArrayList<JCheckBox> dataTypes;
		ArrayList<JButton> hoverables;
		int edge = 325;
    	int startX = 145,startY = 10;
    	double regions[][] = new double[5][4];
        JLabel regionLbls[][] = new JLabel[5][4];
		
		public RenderWindow(ArrayList<JRadioButton> chartTypes,ArrayList<JCheckBox> dataTypes) {
			this.chartTypes = chartTypes;
			this.dataTypes = dataTypes;
			int[] xCoord = new int[50];
			int[] yCoord = new int[50];
			float[] diameter = new float[50];
			float[] depth = new float[50];
			int[] traffic = new int[50];
			
	        File file = new File("src/Potholes - Sheet1.csv");
	        try {
	        	Scanner reader = new Scanner(file);
				String line = reader.nextLine();
				int i = 0;
				while (reader.hasNextLine()) {
	                line = reader.nextLine();
	                String[] sep = line.split(",");
	                xCoord[i] = Integer.parseInt(sep[0]);
	                yCoord[i] = Integer.parseInt(sep[1]);
	                diameter[i] = Float.parseFloat(sep[2]);
	                depth[i] = Float.parseFloat(sep[3]);
	                traffic[i] = Integer.parseInt(sep[4]);
	                holes[i] = new Hole(i+1,xCoord[i],yCoord[i],diameter[i],depth[i],traffic[i]);
	                i++;
	            }
		        reader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			setBackground(Color.WHITE);
			repaint();
			for(Hole h : holes) {
				this.add(h.hoverable);
				this.add(h.tt_id);
				this.add(h.tt_diam);
				this.add(h.tt_depth);
				int x=-1,y=-1;
				if(h.getX()<startX+(edge*1)) {
        			x = 0;
        		}else if(h.getX()<startX+(edge*2)) {
        			x = 1;
        		}else if(h.getX()<startX+(edge*3)) {
        			x = 2;
        		}else if(h.getX()<startX+(edge*4)) {
        			x = 3;
        		}else if(h.getX()<startX+(edge*5)) {
        			x = 4;
        		}
        		if(h.getY()<startY+(edge*1)) {
        			y = 0;
        		}else if(h.getY()<startY+(edge*2)) {
        			y = 1;
        		}else if(h.getY()<startY+(edge*3)) {
        			y = 2;
        		}
        		h.region = h.region+((char)(65+y))+(x+1);
			}
			
			for(int i=0;i<5;i++) {
	    		for(int j=0;j<3;j++) {
	    			regionLbls[i][j] = new JLabel();
	    			regionLbls[i][j].setBounds(startX+(edge*(i+1))-(edge*3/4), startY+(edge*(j+1))-(edge*3/4), edge/2, edge/2);
	    			String contents = new String(""+((char)(65+j))+(i+1));
	    			regionLbls[i][j].setText(contents);
	    			regionLbls[i][j].setHorizontalAlignment(0);
	    			regionLbls[i][j].setForeground(Color.black);
	    			regionLbls[i][j].setFont(new Font("Dialog",0,24));
//	    			this.add(regionLbls[i][j]);
	    		}
	        }
		}
		
		public void render() {
			paintComponent(this.getGraphics());
		}
		
		// +----------------------------------------------------+
		// |	chartTypes		dataTypes		igms			|
		// +----------------------------------------------------+
		//0|	Map				Hole Size		roads
		//1|	Heat			Hole Depth		
		//2|					Hole
		//3|
		//4|
		//5|
		//6|
		
		public double getMaxValue(double[] numbers) {
	        double maxValue = numbers[0];
	            for (int i = 0; i < numbers.length; i++) {
	                if (numbers[i] > maxValue) {
	                    maxValue = numbers[i];
	                }
	            }
	        return maxValue;
	    }
		
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setColor(Color.red);
	        if(chartTypes.get(1).isSelected()) {
	        	int alpha = 100;
	        	Color c0 = new Color(255,255,255,alpha);
	        	Color c1 = new Color(255,150,150,alpha);
	        	Color c2 = new Color(255,100,100,alpha);
	        	Color c3 = new Color(255,0,0,alpha);
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++)
		        		regions[i][j]=0;
	        	}
	        	for(Hole hole : holes) {
	        		int x=-1,y=-1;
	        		if(hole.getX()<startX+(edge*1)) {
	        			x = 0;
	        		}else if(hole.getX()<startX+(edge*2)) {
	        			x = 1;
	        		}else if(hole.getX()<startX+(edge*3)) {
	        			x = 2;
	        		}else if(hole.getX()<startX+(edge*4)) {
	        			x = 3;
	        		}else if(hole.getX()<startX+(edge*5)) {
	        			x = 4;
	        		}
	        		if(hole.getY()<startY+(edge*1)) {
	        			y = 0;
	        		}else if(hole.getY()<startY+(edge*2)) {
	        			y = 1;
	        		}else if(hole.getY()<startY+(edge*3)) {
	        			y = 2;
	        		}
	        		
	        		if(dataTypes.get(0).isSelected() && dataTypes.get(1).isSelected())
        				regions[x][y]+=hole.getDiameter()*hole.getDepth();
        			else if(dataTypes.get(0).isSelected())
        				regions[x][y]+=hole.getDiameter();
        			else if(dataTypes.get(1).isSelected())
        				regions[x][y]+=hole.getDepth();
        			else
	        			regions[x][y]++;
	        	}
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++) {
	        			if(dataTypes.get(0).isSelected() && dataTypes.get(1).isSelected()) {
	        				if(regions[i][j]<=0.05) {
		        				g2.setColor(c0);
		        			}else if(regions[i][j]<=0.15) {
		        				g2.setColor(c1);
		        			}else if(regions[i][j]<=0.25) {
		        				g2.setColor(c2);
		        			}else{
		        				g2.setColor(c3);
		        			}
			        		g2.fillRect(startX+(edge*i), startY+(edge*j), edge, edge);
	        				
	        			}else if(dataTypes.get(0).isSelected()) {
		        			if(regions[i][j]<=1) {
		        				g2.setColor(c0);
		        			}else if(regions[i][j]<=2) {
		        				g2.setColor(c1);
		        			}else if(regions[i][j]<=3) {
		        				g2.setColor(c2);
		        			}else{
		        				g2.setColor(c3);
		        			}
			        		g2.fillRect(startX+(edge*i), startY+(edge*j), edge, edge);
	        			}else if(dataTypes.get(1).isSelected()) {
	        				if(regions[i][j]<=0.15) {
		        				g2.setColor(c0);
		        			}else if(regions[i][j]<=0.3) {
		        				g2.setColor(c1);
		        			}else if(regions[i][j]<=0.4) {
		        				g2.setColor(c2);
		        			}else{
		        				g2.setColor(c3);
		        			}
			        		g2.fillRect(startX+(edge*i), startY+(edge*j), edge, edge);
	        			}else{
		        			if(regions[i][j]<=1) {
		        				g2.setColor(c0);
		        			}else if(regions[i][j]<=2) {
		        				g2.setColor(c1);
		        			}else if(regions[i][j]<=4) {
		        				g2.setColor(c2);
		        			}else{
		        				g2.setColor(c3);
		        			}
			        		g2.fillRect(startX+(edge*i), startY+(edge*j), edge, edge);
	        			}
//	        			regionLbls[i][j].show();
        				g2.setColor(Color.white);
	        			g2.drawLine(startX+(edge*i), startY+(edge*j), startX+(edge*(i+1)), startY+(edge*(j)));
	        			g2.drawLine(startX+(edge*i), startY+(edge*j), startX+(edge*(i)), startY+(edge*(j+1)));
	        			g2.drawLine(startX+(edge*(i+1)), startY+(edge*(j+1)), startX+(edge*(i+1)), startY+(edge*(j)));
	        			g2.drawLine(startX+(edge*(i+1)), startY+(edge*(j+1)), startX+(edge*(i)), startY+(edge*(j+1)));
	        		}
	        	}
	        }
	        if(chartTypes.get(0).isSelected()||chartTypes.get(1).isSelected()) {
	        	roadsImg.show();
	        	for (Hole hole : holes) { // Draw points
		        	if(dataTypes.get(0).isSelected() && dataTypes.get(1).isSelected()) {
		        		if(hole.getDepth()<0.05) {
		        			g2.setColor(new Color(255,170,170));
		        		}else if(hole.getDepth()<0.1) {
		        			g2.setColor(new Color(255,85,85));
		        		}else if(hole.getDepth()<0.15) {
		        			g2.setColor(new Color(255,0,0));
		        		}else{
		        			g2.setColor(new Color(170,0,0));
		        		}
		        		hole.setPixelDiameter((int)(20*hole.getDiameter())+4);
		        	}else if(dataTypes.get(0).isSelected()) {
		        		g2.setColor(new Color(255,0,0));
		        		hole.setPixelDiameter((int)(20*hole.getDiameter())+4);
		        	}else if(dataTypes.get(1).isSelected()) {
		        		if(hole.getDepth()<0.05) {
		        			g2.setColor(new Color(255,170,170));
		        		}else if(hole.getDepth()<0.1) {
		        			g2.setColor(new Color(255,85,85));
		        		}else if(hole.getDepth()<0.15) {
		        			g2.setColor(new Color(255,0,0));
		        		}else{
		        			g2.setColor(new Color(170,0,0));
		        		}
		        		hole.setPixelDiameter(20);       		
		        	}else{
		        		g2.setColor(new Color(255,0,0));
		        		hole.setPixelDiameter(20);
		            }
	        		g2.fillOval(hole.getX()-hole.getOffset(), hole.getY()-hole.getOffset(),hole.getPixelDiameter(), hole.getPixelDiameter());
		        }
	        }
	        if(chartTypes.get(2).isSelected()) {
	        	
	        	Rectangle graphRegion = new Rectangle(startX+75, startY+200,1400,700);
	        	int barWidth = (int)graphRegion.getWidth()/15;
        		double[] barContents = new double[15];
        		
        		roadsImg.hide();
	        	g2.setColor(Color.white);
	        	g2.drawLine((int)(graphRegion.getX()),(int)(graphRegion.getY()),
	        			(int)(graphRegion.getX()),(int)(graphRegion.getY()+graphRegion.getHeight()));
	        	g2.drawLine((int)(graphRegion.getX()),(int)(graphRegion.getY()+graphRegion.getHeight()),
        				(int)(graphRegion.getX()+(barWidth*15)),(int)(graphRegion.getY()+graphRegion.getHeight()));
	        	
	        	if(dataTypes.get(0).isSelected() && dataTypes.get(1).isSelected()) {
	        		for(Hole h : holes) {
	        			switch(h.region) {
	        			case "A1":
	        				barContents[0]+=h.getDiameter();
	        				break;
	        			case "A2":
	        				barContents[1]+=h.getDiameter();
	        				break;
	        			case "A3":
	        				barContents[2]+=h.getDiameter();
	        				break;
	        			case "A4":
	        				barContents[3]+=h.getDiameter();
	        				break;
	        			case "A5":
	        				barContents[4]+=h.getDiameter();
	        				break;
	        			case "B1":
	        				barContents[5]+=h.getDiameter();
	        				break;
	        			case "B2":
	        				barContents[6]+=h.getDiameter();
	        				break;
	        			case "B3":
	        				barContents[7]+=h.getDiameter();
	        				break;
	        			case "B4":
	        				barContents[8]+=h.getDiameter();
	        				break;
	        			case "B5":
	        				barContents[9]+=h.getDiameter();
	        				break;
	        			case "C1":
	        				barContents[10]+=h.getDiameter();
	        				break;
	        			case "C2":
	        				barContents[11]+=h.getDiameter();
	        				break;
	        			case "C3":
	        				barContents[12]+=h.getDiameter();
	        				break;
	        			case "C4":
	        				barContents[13]+=h.getDiameter();
	        				break;
	        			case "C5":
	        				barContents[14]+=h.getDiameter();
	        				break;
	        			}
	        		}
	        	}else if(dataTypes.get(0).isSelected()) {
	        		for(Hole h : holes) {
	        			switch(h.region) {
	        			case "A1":
	        				barContents[0]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "A2":
	        				barContents[1]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "A3":
	        				barContents[2]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "A4":
	        				barContents[3]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "A5":
	        				barContents[4]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "B1":
	        				barContents[5]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "B2":
	        				barContents[6]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "B3":
	        				barContents[7]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "B4":
	        				barContents[8]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "B5":
	        				barContents[9]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "C1":
	        				barContents[10]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "C2":
	        				barContents[11]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "C3":
	        				barContents[12]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "C4":
	        				barContents[13]+=h.getDiameter()*h.getDepth();
	        				break;
	        			case "C5":
	        				barContents[14]+=h.getDiameter()*h.getDepth();
	        				break;
	        			}
	        		}
	        	}else if(dataTypes.get(1).isSelected()) {
	        		for(Hole h : holes) {
	        			switch(h.region) {
	        			case "A1":
	        				barContents[0]+=h.getDepth();
	        				break;
	        			case "A2":
	        				barContents[1]+=h.getDepth();
	        				break;
	        			case "A3":
	        				barContents[2]+=h.getDepth();
	        				break;
	        			case "A4":
	        				barContents[3]+=h.getDepth();
	        				break;
	        			case "A5":
	        				barContents[4]+=h.getDepth();
	        				break;
	        			case "B1":
	        				barContents[5]+=h.getDepth();
	        				break;
	        			case "B2":
	        				barContents[6]+=h.getDepth();
	        				break;
	        			case "B3":
	        				barContents[7]+=h.getDepth();
	        				break;
	        			case "B4":
	        				barContents[8]+=h.getDepth();
	        				break;
	        			case "B5":
	        				barContents[9]+=h.getDepth();
	        				break;
	        			case "C1":
	        				barContents[10]+=h.getDepth();
	        				break;
	        			case "C2":
	        				barContents[11]+=h.getDepth();
	        				break;
	        			case "C3":
	        				barContents[12]+=h.getDepth();
	        				break;
	        			case "C4":
	        				barContents[13]+=h.getDepth();
	        				break;
	        			case "C5":
	        				barContents[14]+=h.getDepth();
	        				break;
	        			}
	        		}
	        	}else{
	        		for(Hole h : holes) {
	        			switch(h.region) {
	        			case "A1":
	        				barContents[0]++;
	        				break;
	        			case "A2":
	        				barContents[1]++;
	        				break;
	        			case "A3":
	        				barContents[2]++;
	        				break;
	        			case "A4":
	        				barContents[3]++;
	        				break;
	        			case "A5":
	        				barContents[4]++;
	        				break;
	        			case "B1":
	        				barContents[5]++;
	        				break;
	        			case "B2":
	        				barContents[6]++;
	        				break;
	        			case "B3":
	        				barContents[7]++;
	        				break;
	        			case "B4":
	        				barContents[8]++;
	        				break;
	        			case "B5":
	        				barContents[9]++;
	        				break;
	        			case "C1":
	        				barContents[10]++;
	        				break;
	        			case "C2":
	        				barContents[11]++;
	        				break;
	        			case "C3":
	        				barContents[12]++;
	        				break;
	        			case "C4":
	        				barContents[13]++;
	        				break;
	        			case "C5":
	        				barContents[14]++;
	        				break;
	        			}
	        		}
	        	}
	        	double maxN = getMaxValue(barContents);
        		for(int n=0;n<barContents.length;n++) {
        			int h = (int)(graphRegion.getHeight()*(barContents[n]/maxN));
        			g2.setColor(Color.red);
        			g2.fillRect((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h), barWidth, h);
        			
        		}
	        	
	        }
	        repaint();
	    }
	}
}
