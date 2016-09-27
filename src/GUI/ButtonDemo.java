package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

import preprocessing.module1;
import sessionpageview.CSVcreation;
import workingCmeans.FuzzyCMeans;
import kmarkov.MainClass;

public class ButtonDemo extends JPanel
						implements ActionListener, MouseListener {

	//private JPanel contentPane;
	protected JButton btnPreprocessing, btnClustering, btnCsvCreation, btnPrediction;
	JEditorPane jep;
	
	/**
	 * Launch the application.
	 */
	 public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI(); 
	            }
	        });
	    }

	 private static void createAndShowGUI() {

	        //Create and set up the window.
	        JFrame frame = new JFrame("ButtonDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        //Create and set up the content pane.
	        ButtonDemo newContentPane = new ButtonDemo();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        frame.setSize(500, 500);
	        newContentPane.setBackground(new Color(170,170,255));
	    }
	 
	 public void actionPerformed(ActionEvent e) {
	       /* if ("disable".equals(e.getActionCommand())) {
	            b2.setEnabled(false);
	            b1.setEnabled(false);
	            b3.setEnabled(true);
	        } else {
	            b2.setEnabled(true);
	            b1.setEnabled(true);
	            b3.setEnabled(false);
	        }*/
	    	String process = e.getActionCommand();
	    	int action = 0;
	    	int next = 0;
	    	if(process.equals("pre-process"))
	    		action = 1;
	    	else if(process.equals("CSV"))
	    		action = 2;
	    	else if(process.equals("C-means"))
	    		action = 3;
	    	else if(process.equals("markov"))
	    		action = 4;
	    	switch(action){
	    		case 1: {
	    			JLabel lbl = new JLabel("preprocess");
	    			lbl.setToolTipText("preprocessing in progress...");
	    			module1.main1();
	    			printFile(action);
	    				break;
	    		}
	    		case 2: {
	    			remove(jep);
	    			JLabel lbl = new JLabel("CSV file generation");
	    			lbl.setToolTipText("Generating CSV file...");
	    			CSVcreation.main2();
	    			printFile(action);
	    				break;
	    		}
	    		case 3: try {
	    			//remove(jep);
	    			JLabel lbl = new JLabel("Fuzzy clustering");
	    			lbl.setToolTipText("clustering in progress...");
	    			FuzzyCMeans.main3();
	    			fuzzy();
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	    				break;
	    		case 4: try {
	    			remove(jep);
	    			JLabel lbl = new JLabel("markov model");
	    			lbl.setToolTipText("Building the Markov model...");
					MainClass.main4();
					printFile(action);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    				break;
	    	}
	    }
	 
	/**
	 * Create the frame.
	 */
	 
	public ButtonDemo() {
		
		btnPreprocessing = new JButton("Pre-Processing");
		btnPreprocessing.setBounds(156, 82, 200, 75);
		btnPreprocessing.setActionCommand("pre-process");
		
		btnCsvCreation = new JButton("CSV creation");
		btnCsvCreation.setBounds(156, 160, 200, 75);
		btnCsvCreation.setActionCommand("CSV");
		
		btnClustering = new JButton("Clustering");
		btnClustering.setBounds(156, 238, 200, 75);
		btnClustering.setActionCommand("C-means");
		
		btnPrediction = new JButton("Prediction");
		btnPrediction.setBounds(156, 316, 200, 75);
		btnPrediction.setActionCommand("markov");

		btnPreprocessing.addActionListener(this);
		btnCsvCreation.addActionListener(this);
		btnClustering.addActionListener(this);
		btnPrediction.addActionListener(this);
		setLayout(null);
		
		/*JLabel lblFileLink = new JLabel("prediction.txt");

		// To indicate the the link is clickable
		lblFileLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblFileLink.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            try {
		                Desktop.getDesktop().open(
		                        new File("prediction.txt"));
		            } catch (Exception e1) {

		                e1.printStackTrace();
		            }
		        }
		    });
		*/
		
		add(btnPreprocessing);
		add(btnCsvCreation);
		add(btnClustering);
		add(btnPrediction);
		//add(lblFileLink);
		
		/*JEditorPane jep = new JEditorPane();
		jep.setBounds(141, 252, 106, 20);
		jep.setContentType("text/html");//set content as html
        jep.setText("<a href='preprocess.txt'>Preprocess</a>.");

        jep.setEditable(false);//so its not editable
        jep.setOpaque(false);//so we dont see whit background

        jep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    System.out.println(hle.getURL());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        //desktop.browse(hle.getURL().toURI());
                    	File openFile = new File("preprocess.txt");
                    	desktop.open(openFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
		add(jep);*/
	}
	
	public void printFile(final int choice){
		jep = new JEditorPane();
		jep.setBounds(141, 400, 106, 20);
		jep.setContentType("text/html");//set content as html
		String text = "";
		switch(choice){
			case 1: text = new String("<a href='preprocess.txt'>session file</a>");
					break;
			case 2: text = new String("<a href='preprocess.txt'>CSV file</a>");
					break;
			case 4: text = new String("<a href='preprocess.txt'>TPM</a>");
					break;
		}
		
        jep.setText(text);

        jep.setEditable(false);//so its not editable
        jep.setOpaque(false);//so we dont see whit background

        jep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    System.out.println(hle.getURL());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                    	
                        //desktop.browse(hle.getURL().toURI());
                    	/*File openFile = new File(finalName);*/
                    	File openFile = null;
                    	switch(choice){
                    		case 1: openFile = new File("session.txt");
                    				desktop.open(openFile);
                    				break;
                    		case 2: openFile = new File("SPVM.csv");
                    				desktop.open(openFile);
                    				break;
                    		case 4: openFile = new File("clusterK11.txt");
                    				desktop.open(openFile);
                    				break;
                    	}
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
		add(jep);
	}
	
	public void fuzzy(){
		final JEditorPane jep = new JEditorPane();
		jep.setBounds(141, 450, 106, 20);
		jep.setContentType("text/html");//set content as html
        jep.setText("<a href='clustering.txt'>Clustering results</a>.");

        jep.setEditable(false);//so its not editable
        jep.setOpaque(false);//so we dont see whit background

        jep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    System.out.println(hle.getURL());
                    Desktop desktop = Desktop.getDesktop();
                    
                    try {
                    	String line1 = null;
                    	int xposn = 500;
                    	int yposn = 310;
                    	
                    	File openFile = new File("clustering.txt");
                    	FileReader freader = new FileReader(openFile);
                    	BufferedReader buffreader = new BufferedReader(freader);
                    	
                    	while((line1=buffreader.readLine())!=null){
                    		
                    		
                    		JLabel label = new JLabel(line1);
                    		JLabel label1 = new JLabel("cluster1");
                    		JLabel label2 = new JLabel("cluster2");
                    	//	JLabel label3 = new JLabel("cluster3");
                    	//	JLabel label4 = new JLabel("cluster4");
                    		
           
           /*         		jep.paintImmediately(350,175,250,250);
                    		jep.paintImmediately(700, 175, 250, 250);
                    		jep.paintImmediately(1000, 175, 250, 250);*/
                    		
                    		add(label);
                    		add(label1);
                    		add(label2);
                    //		add(label3);
                    		//add(label4);
                    		
                    		label.setLocation(xposn, yposn);
                    		label.setSize(86, 14);
                    		label1.setLocation(550,140);
                    		label1.setSize(50,50);
                    		
                    		label2.setLocation(1000,140);
                    		label2.setSize(50,50);
                    		
                    	//	label3.setLocation(1050,140);
                    	//	label3.setSize(50,50);
                    		
                    	/*	label4.setLocation(1000,500);
                    		label4.setSize(50,50);*/
                    		xposn += 500;
                    		//yposn += 200;
                    	}
                    	
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
		add(jep);
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void paint(Graphics g)
	{
		
		// call superclass version of method paint
		super.paint( g );
		
		int fontSize = 20;
		g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));    
		g.setColor(Color.red);
		g.drawString("USER BEHAVIOR ANALYSIS ON WEB USING FUZZY C-MEANS CLUSTERING ALGORITHM AND Kth ORDER MARKOV MODEL",50,50);

		// draw oval
		g.setColor(Color.blue);
		g.drawOval(450,175,250,250);
	//	g.fillOval(350,175,250,250);
		g.drawOval(900, 175, 250, 250);
	//	g.fillOval(700, 175, 250, 250);
	//	g.drawOval(1000, 175, 250, 250);

		//g.drawOval(350, 450, 250, 250);
		//g.drawOval(700, 450, 250, 250);
		
	}
}
