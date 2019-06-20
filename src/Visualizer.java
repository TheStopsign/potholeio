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
	private JLabel roadsImg,minimap;
	JRadioButton rdbtnMap = new JRadioButton("Map");
	JRadioButton rdbtnHeatmap = new JRadioButton("Heatmap");
	JRadioButton rdbtnBarGraph = new JRadioButton("Bar Graph");
	JRadioButton optionHoleSize = new JRadioButton("Hole Diameter");
	JRadioButton optionHoleDepth = new JRadioButton("Hole Depth");
	JCheckBox optionTraffic = new JCheckBox("Traffic Level");
	JRadioButton optionVolCost = new JRadioButton("Hole Volume/Cost");

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

		JButton showSettings = new JButton("M e n u");
		JButton hideSettings = new JButton("M e n u");
		
		ImageIcon tmp = new ImageIcon("src/troymap_roads.png");
		Image tmp2 = tmp.getImage().getScaledInstance((int)(3332*.45), (int)(2076*.45),  java.awt.Image.SCALE_SMOOTH);
		ImageIcon tmp3 = new ImageIcon(tmp2);
		roadsImg = new JLabel(tmp3);
		
		Image tmp4 = tmp.getImage().getScaledInstance((int)(3332*.195), (int)(2076*.195),  java.awt.Image.SCALE_SMOOTH);
		ImageIcon tmp5 = new ImageIcon(tmp4);
		minimap = new JLabel(tmp5);
		
		
		Color bckgndColor = new Color(27,32,68);
		
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
		
		RenderWindow renderWindow = new RenderWindow();
		renderWindow.setBounds(0, 10, 1920, 1000);
		renderWindow.setBorder(null);
		renderWindow.setOpaque(false);
		renderWindow.setLayout(null);
		
		JPanel dataToolbar = new JPanel();
		dataToolbar.setBounds(12, 12, 279, 420);
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
		lblCharts.setBounds(36, 86, 121, 36);
		dataToolbar.add(lblCharts);
		
		optionHoleSize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				optionHoleDepth.setSelected(false);
				optionVolCost.setSelected(false);
				renderWindow.render();
			}
		});
		
		rdbtnMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renderWindow.render();
			}
		});
		rdbtnMap.setSelected(true);
		rdbtnMap.setForeground(Color.WHITE);
		rdbtnMap.setBackground(bckgndColor);
		visualTypes.add(rdbtnMap);
		rdbtnMap.setFont(new Font("Dialog", Font.BOLD, 16));
		rdbtnMap.setBounds(36, 126, 144, 23);
		dataToolbar.add(rdbtnMap);
		
		rdbtnHeatmap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roadsImg.show();
				renderWindow.render();
			}
		});
		rdbtnHeatmap.setForeground(Color.WHITE);
		rdbtnHeatmap.setBackground(bckgndColor);
		visualTypes.add(rdbtnHeatmap);
		rdbtnHeatmap.setFont(new Font("Dialog", Font.BOLD, 16));
		rdbtnHeatmap.setBounds(36, 153, 144, 23);
		dataToolbar.add(rdbtnHeatmap);
		
		rdbtnBarGraph.setBounds(36, 180, 144, 23);
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
				optionHoleSize.setSelected(false);
				optionVolCost.setSelected(false);
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
		showSettings.setForeground(bckgndColor);
		showSettings.setBackground(Color.WHITE);
		showSettings.setOpaque(true);
		showSettings.setBorderPainted(false);
		
		hideSettings.setFont(new Font("Dialog", Font.BOLD, 20));
		hideSettings.setForeground(bckgndColor);
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
		optionTraffic.setBackground(bckgndColor);
		optionTraffic.setBounds(36, 361, 144, 23);
		dataToolbar.add(optionTraffic);
		
		optionVolCost.setForeground(Color.WHITE);
		optionVolCost.setFont(new Font("Dialog", Font.BOLD, 16));
		optionVolCost.setBackground(bckgndColor);
		optionVolCost.setBounds(36, 334, 200, 23);
		optionVolCost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				optionHoleSize.setSelected(false);
				optionHoleDepth.setSelected(false);
				
				renderWindow.render();
			}
		});
		dataToolbar.add(optionVolCost);
		
		
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
		
		roadsImg.setFont(new Font("Dialog", Font.BOLD, 24));
		roadsImg.setBounds(0, 10, 1920, 1000);
		renderContainer.add(roadsImg);
	}
	
	class RenderWindow extends JPanel {

		ArrayList<JButton> hoverables;
		int edge = 325;
    	int startX = 145,startY = 10;
        int graphX = 230,graphY = 450,graphWidth = 1200, graphHeight = 500;
    	double regions[][] = new double[5][4];
        JLabel regionLbls[][] = new JLabel[5][4];
        JLabel yMin = new JLabel();
        JLabel yMed = new JLabel();
        JLabel yMax = new JLabel();
        JLabel chartTitle = new JLabel();
        JPanel minis[][] = new JPanel[5][3];
        int miniEdge = 140,miniX=1200,miniY=10;
        int alpha = 140;
    	Color c0 = new Color(255,255,255,alpha);
    	Color c1 = new Color(255,175,175,alpha);
    	Color c2 = new Color(255,100,100,alpha);
    	Color c3 = new Color(255,0,0,alpha);
    	double[] barContents = new double[15];
    	double[] regionCount = new double[15];
    	double[] regionTraffic = new double[15];
    	JPanel barHovers[] = new JPanel[15];
    	JLabel tMin = new JLabel();
    	JLabel tMed = new JLabel();
    	JLabel tMax = new JLabel();
    	JLabel trafficTitle = new JLabel("Average Traffic Level");
		
		public RenderWindow() {
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
			for(Hole h : holes) {
				this.add(h.hoverable);
				this.add(h.tt_id);
				this.add(h.tt_diam);
				this.add(h.tt_depth);
				this.add(h.tt_volume);
				this.add(h.tt_cost);
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
        		h.region = (5*y)+x;
        		regionCount[(5*y)+x]++;
        		regionTraffic[(5*y)+x]+=h.getTraffic();
			}
			
			for(int i=0;i<5;i++) {
	    		for(int j=0;j<3;j++) {
	    			regionLbls[i][j] = new JLabel();
	    			String contents = new String(""+((char)(65+j))+(i+1));
	    			regionLbls[i][j].setText(contents);
	    			regionLbls[i][j].setHorizontalAlignment(0);
	    			regionLbls[i][j].setForeground(Color.white);
	    			regionLbls[i][j].setFont(new Font("Dialog",Font.BOLD,24));
	    			this.add(regionLbls[i][j]);
	    		}
	        }
			this.add(yMin); yMin.hide();
			this.add(yMed); yMed.hide();
			this.add(yMax); yMax.hide();
			this.add(chartTitle); chartTitle.hide();
			
			for(int i=0;i<5;i++) {
	    		for(int j=0;j<3;j++) {
	    			int f = i;
	    			int g = j;
	    			JPanel temp = new JPanel();
	    			JPanel temp2 = new JPanel();
	    			temp.setBounds(miniX+(miniEdge*i), miniY+(miniEdge*j), miniEdge, miniEdge);
        			temp.setBackground(c2);
        			temp2.setBackground(new Color(255,0,0,0));
	    			temp.setBorder(new LineBorder(Color.black));
	    			temp.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseEntered(MouseEvent e) {
	    					temp.setBackground(new Color(255,255,255,175));
	            			temp2.setBackground(new Color(255,255,255,175));
	    				}
	    			});
	    			temp.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseExited(MouseEvent e) {
		        			temp.setBackground(c2);
		        			temp2.setBackground(new Color(255,0,0,0));
	    				}
	    			});
	    			temp2.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseEntered(MouseEvent e) {
	    					temp.setBackground(new Color(255,255,255,175));
	            			temp2.setBackground(new Color(255,255,255,175));
	    				}
	    			});
	    			temp2.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseExited(MouseEvent e) {
	    					temp.setBackground(c2);
	            			temp2.setBackground(new Color(255,0,0,0));
	    				}
	    			});
	    			minis[i][j] = temp;
	    			barHovers[(5*j)+i] = temp2;
	    			this.add(minis[i][j]);
	    			this.add(barHovers[(5*j)+i]);
	    			minis[i][j].hide();
	    			barHovers[(5*j)+i].hide();
	    		}
    		}
			this.add(tMin);
    		this.add(tMed);
    		this.add(tMax);
    		trafficTitle.setForeground(Color.white);
    		trafficTitle.setFont(new Font("Dialog",Font.BOLD,24));
    		trafficTitle.setBounds(1525, 535, 300, 80);
    		this.add(trafficTitle);
			minimap.setBounds(miniX-100, miniY-240, 900	, 900);
			this.add(minimap);
			minimap.hide();
			repaint();
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
	        chartTitle.hide(); trafficTitle.hide();
	        if(rdbtnMap.isSelected()) {
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++)
		        		regionLbls[i][j].hide();
	        	}
	        	optionTraffic.hide();
	        }else {
	        	optionTraffic.show();
	        }
	        if(rdbtnHeatmap.isSelected()) {
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++) {
		        		regions[i][j]=0;
	        		}
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
	        		
	        		if(optionVolCost.isSelected())
        				regions[x][y]+=hole.getDiameter()*hole.getDepth();
        			else if(optionHoleSize.isSelected())
        				regions[x][y]+=hole.getDiameter();
        			else if(optionHoleDepth.isSelected())
        				regions[x][y]+=hole.getDepth();
        			else
	        			regions[x][y]++;
	        		//TODO
	        		if(optionTraffic.isSelected()) {
	        			regions[x][y]*=(hole.getTraffic()/3);
	        		}
	        	}
	        	double maxes[] = new double[3];
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++) {
	        			if(optionVolCost.isSelected()) {
	        				maxes[0] = 0.05; maxes[1] = 0.15; maxes[2] = 0.25;
	        			}else if(optionHoleSize.isSelected()) {
	        				maxes[0] = 1; maxes[1] = 2; maxes[2] = 3;
	        			}else if(optionHoleDepth.isSelected()) {
	        				maxes[0] = 0.15; maxes[1] = 0.3; maxes[2] = 0.4;
	        			}else{
	        				maxes[0] = 1; maxes[1] = 2; maxes[2] = 4;
	        			}
	        			if(optionTraffic.isSelected()) {
	        				maxes[0] /= 5; maxes[1] /= 5; maxes[2] /= 4;
	        			}
	        			if(regions[i][j]<=maxes[0]) {
	        				g2.setColor(c0);
	        			}else if(regions[i][j]<=maxes[1]) {
	        				g2.setColor(c1);
	        			}else if(regions[i][j]<=maxes[2]) {
	        				g2.setColor(c2);
	        			}else{
	        				g2.setColor(c3);
	        			}
		        		g2.fillRect(startX+(edge*i), startY+(edge*j), edge, edge);
	        			regionLbls[i][j].setBounds(startX+(edge*(i+1))-(edge*3/4), startY+(edge*(j+1))-(edge*3/4), edge/2, edge/2);
	        			regionLbls[i][j].show();
        				g2.setColor(Color.white);
	        			g2.drawLine(startX+(edge*i), startY+(edge*j), startX+(edge*(i+1)), startY+(edge*(j)));
	        			g2.drawLine(startX+(edge*i), startY+(edge*j), startX+(edge*(i)), startY+(edge*(j+1)));
	        			g2.drawLine(startX+(edge*(i+1)), startY+(edge*(j+1)), startX+(edge*(i+1)), startY+(edge*(j)));
	        			g2.drawLine(startX+(edge*(i+1)), startY+(edge*(j+1)), startX+(edge*(i)), startY+(edge*(j+1)));
	        		}
	        	}
	        }
	        if(rdbtnMap.isSelected()||rdbtnHeatmap.isSelected()) {
	        	roadsImg.show();minimap.hide();
	        	yMin.hide(); yMed.hide(); yMax.hide();
	        	tMin.hide();tMed.hide();tMax.hide();
	        	for (Hole hole : holes) { // Draw points
	        		hole.hoverable.show();
		        	if(optionVolCost.isSelected()) {
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
		        	}else if(optionHoleSize.isSelected()) {
		        		g2.setColor(new Color(255,0,0));
		        		hole.setPixelDiameter((int)(20*hole.getDiameter())+4);
		        	}else if(optionHoleDepth.isSelected()) {
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
	        	for(int i=0;i<5;i++) {
	        		for(int j=0;j<3;j++) {
	        			minis[i][j].hide();
	        			barHovers[(5*j)+i].hide();
	        		}
	        	}
	        }else {
	        	for(Hole h : holes) {
	        		h.hoverable.hide();
	        	}
	        }
	        if(rdbtnBarGraph.isSelected()) {
	        	minimap.show();
	        	
	        	Rectangle graphRegion = new Rectangle(graphX, graphY,graphWidth,graphHeight);
	        	int barWidth = (int)graphRegion.getWidth()/15;
        		
        		roadsImg.hide();
	        	g2.setColor(Color.white);
	        	g2.drawLine((int)(graphRegion.getX()),(int)(graphRegion.getY()),
	        			(int)(graphRegion.getX()),(int)(graphRegion.getY()+graphRegion.getHeight()));
	        	g2.drawLine((int)(graphRegion.getX()),(int)(graphRegion.getY()+graphRegion.getHeight()),
        				(int)(graphRegion.getX()+(barWidth*15)),(int)(graphRegion.getY()+graphRegion.getHeight()));
	        	for(int i=0;i<15;i++) {
	        		barContents[i] = 0;
	        		regionTraffic[i] = 0;
	        	}
	        	if(optionVolCost.isSelected()) {
                    // pothole size and depth
                    chartTitle.setText("Pothole Volume(m^3) and Cost($)");
	        		for(Hole h : holes) {
	        			for(int i=0;i<15;i++) {
	        				if(i==h.region)
	        					barContents[i]+=h.getDiameter();
	        			}
	        		}
	        	}else if(optionHoleSize.isSelected()) {
                    chartTitle.setText("Pothole Diameter(m)");
	        		for(Hole h : holes) {
	        			for(int i=0;i<15;i++) {
	        				if(i==h.region)
	        					barContents[i]+=h.getDiameter()*h.getDepth();
	        			}
	        		}
	        	}else if(optionHoleDepth.isSelected()) {
                    chartTitle.setText("Pothole Depth(m)");
	        		for(Hole h : holes) {
	        			for(int i=0;i<15;i++) {
	        				if(i==h.region)
	        					barContents[i]+=h.getDepth();
	        			}
	        		}
	        	}else{
                    chartTitle.setText("Pothole Count");
	        		for(Hole h : holes) {
	        			for(int i=0;i<15;i++) {
	        				if(i==h.region)
	        					barContents[i]++;
	        			}
	        		}
	        	}
	        	//TODO
	        	if(optionTraffic.isSelected()) {
	        		for(Hole h : holes) {
	        			for(int i=0;i<15;i++) {
	        				if(i==h.region) {
	        					regionTraffic[i]+=h.getTraffic();
	        					break;
	        				}
	        			}
	        		}
	        		g2.setColor(Color.white);
	        		g2.drawLine((int)(graphRegion.getX()+graphRegion.getWidth()), (int)graphRegion.getY(),
	        				(int)(graphRegion.getX()+graphRegion.getWidth()), (int)(graphRegion.getY()+graphRegion.getHeight()));
	        	}
	        	for(int i=0;i<15;i++) {
	        		regionTraffic[i] /= regionCount[i];
	        	}
	        	int buffer = 10;
	        	double maxN = getMaxValue(barContents);
	        	double maxT = getMaxValue(regionTraffic);
        		for(int n=0;n<barContents.length;n++) {
        			int h = (int)(graphRegion.getHeight()*(barContents[n]/maxN));
        			int t = (int)(graphRegion.getHeight()*(regionTraffic[n]/maxT));
        			
        			if(optionTraffic.isSelected() && h<t) {
        				g2.setColor(Color.blue);
        				g2.fillRect((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-t), barWidth, t);
	        			
        				barHovers[n].setBounds((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+1+graphRegion.getHeight()-t), barWidth-1, t-1);
        				
	        			g2.setColor(Color.red);
	        			g2.fillRect((int)graphRegion.getX()+1+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-h), barWidth-(2*buffer), h);
	
	        			g2.setColor(Color.white);
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-t));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n+buffer), (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-h));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()));
        			}else if(optionTraffic.isSelected()) {
        				
	        			g2.setColor(Color.red);
	        			g2.fillRect((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h), barWidth, h);

	        			g2.setColor(Color.blue);
        				g2.fillRect((int)graphRegion.getX()+1+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-t), barWidth-(2*buffer), t);
        				
        				barHovers[n].setBounds((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+1+graphRegion.getHeight()-h), barWidth-1, h-1);
	        			
	        			g2.setColor(Color.white);
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-h));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-t));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n)+buffer, (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()-t),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth-buffer, (int)(graphRegion.getY()+graphRegion.getHeight()));
        			}else {
        				g2.setColor(Color.red);
	        			g2.fillRect((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h), barWidth, h);
        				
        				barHovers[n].setBounds((int)graphRegion.getX()+1+(barWidth*n), (int)(graphRegion.getY()+1+graphRegion.getHeight()-h), barWidth-1, h-1);
	        			
	        			g2.setColor(Color.white);
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-h));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n), (int)(graphRegion.getY()+graphRegion.getHeight()));
	        			g2.drawLine((int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()-h),
	        					(int)graphRegion.getX()+(barWidth*n)+barWidth, (int)(graphRegion.getY()+graphRegion.getHeight()));
        			}
        		}
        		for(int i=0;i<5;i++) {
            		for(int j=0;j<3;j++) {
            			regionLbls[i][j].setBounds((int)graphRegion.getX()+1+(barWidth*((5*j)+i)), (int)(graphRegion.getY()+graphRegion.getHeight()),
            					barWidth, 50);
            			regionLbls[i][j].show();
            			minis[i][j].show();
            			barHovers[(5*j)+i].show();
            		}
        		}
        		if(optionVolCost.isSelected()) {
        			yMin.setBounds((int)graphRegion.getX()-75,(int)(graphRegion.getY()+graphRegion.getHeight()-15),300,30); yMin.setForeground(Color.white); yMin.setFont(new Font("Dialog",Font.BOLD,24));
        			yMin.setText("0.00"); yMin.show();
        			yMed.setBounds((int)graphRegion.getX()-175,(int)(graphRegion.getY()+(graphRegion.getHeight()/2)-15),300,30); yMed.setForeground(Color.white); yMed.setFont(new Font("Dialog",Font.BOLD,24));
        			yMed.setText(String.format("%.2f|%.2f", maxN/2,(50*((maxN/2)/0.179)))); yMed.show();
        			yMax.setBounds((int)graphRegion.getX()-195,(int)(graphRegion.getY()-15),300,30); yMax.setForeground(Color.white); yMax.setFont(new Font("Dialog",Font.BOLD,24));
        			yMax.setText(String.format("%.2f|%.2f", maxN,(50*((maxN)/0.179)))); yMax.show();
        		}else if(!optionHoleDepth.isSelected()&&!optionHoleSize.isSelected()&&!optionVolCost.isSelected()){
        			yMin.setBounds((int)graphRegion.getX()-35,(int)(graphRegion.getY()+graphRegion.getHeight()-15),100,30); yMin.setForeground(Color.white); yMin.setFont(new Font("Dialog",Font.BOLD,24));
        			yMin.setText("0"); yMin.show();
        			yMed.setBounds((int)graphRegion.getX()-35,(int)(graphRegion.getY()+(graphRegion.getHeight()/2)-15),100,30); yMed.setForeground(Color.white); yMed.setFont(new Font("Dialog",Font.BOLD,24));
        			yMed.setText(""+(int)(maxN/2)); yMed.show();
        			yMax.setBounds((int)graphRegion.getX()-35,(int)(graphRegion.getY()-15),100,30); yMax.setForeground(Color.white); yMax.setFont(new Font("Dialog",Font.BOLD,24));
        			yMax.setText(""+(int)(maxN)); yMax.show();
        		}else {
        			yMin.setBounds((int)graphRegion.getX()-75,(int)(graphRegion.getY()+graphRegion.getHeight()-15),100,30); yMin.setForeground(Color.white); yMin.setFont(new Font("Dialog",Font.BOLD,24));
        			yMin.setText("0.00"); yMin.show();
        			yMed.setBounds((int)graphRegion.getX()-75,(int)(graphRegion.getY()+(graphRegion.getHeight()/2)-15),100,30); yMed.setForeground(Color.white); yMed.setFont(new Font("Dialog",Font.BOLD,24));
        			yMed.setText(String.format("%.2f", maxN/2)); yMed.show();
        			yMax.setBounds((int)graphRegion.getX()-75,(int)(graphRegion.getY()-15),100,30); yMax.setForeground(Color.white); yMax.setFont(new Font("Dialog",Font.BOLD,24));
        			yMax.setText(String.format("%.2f", maxN)); yMax.show();
        		}
        		if(optionTraffic.isSelected()) {
        			tMin.setBounds((int)(graphRegion.getX()+graphRegion.getWidth()+10),(int)(graphRegion.getY()+graphRegion.getHeight()-15),100,30); tMin.setForeground(Color.white); tMin.setFont(new Font("Dialog",Font.BOLD,24));
        			tMin.setText("Low"); tMin.show();
        			tMed.setBounds((int)(graphRegion.getX()+graphRegion.getWidth()+10),(int)(graphRegion.getY()+(graphRegion.getHeight()/2)-15),100,30); tMed.setForeground(Color.white); tMed.setFont(new Font("Dialog",Font.BOLD,24));
        			tMed.setText("Med"); tMed.show();
        			tMax.setBounds((int)(graphRegion.getX()+graphRegion.getWidth()+10),(int)(graphRegion.getY()-15),100,30); tMax.setForeground(Color.white); tMax.setFont(new Font("Dialog",Font.BOLD,24));
        			tMax.setText("High"); tMax.show();
        			trafficTitle.show();
        		}else {
        			tMin.hide();
        			tMed.hide();
        			tMax.hide();
        		}
    			chartTitle.setBounds(250, 300, 1000, 50); // center title
    			chartTitle.setForeground(Color.white);
    			chartTitle.setHorizontalAlignment(0);
    			chartTitle.setFont(new Font("Dialog", Font.BOLD, 36));
    			chartTitle.show();
        	}
	        repaint();
	    }
	}
}
