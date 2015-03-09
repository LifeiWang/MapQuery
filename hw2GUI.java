package csci585hw2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;


public class hw2GUI implements ActionListener, ItemListener, MouseListener,MouseMotionListener{
	/**
	 * 
	 */
	JFrame jFrame=null;
	JTextField jTextFieldx=null;
	JTextField jTextFieldy=null;
	JButton submitButton=null;
	ButtonGroup qButtonGroup=null;
	JTextArea resultArea=null;
	MapPanel mapPanel=null;
	MapPanel2 mapPanel2=null;
	JLayeredPane lpane=null;
	JLabel imageLabel=null;
	JPanel nnaPanel=null;
	
    JCheckBox ASCB=null;
    JCheckBox BuildingCB=null;
    JCheckBox StudentCB=null;
    
    HW2 hw2=null;
	
	private int radioButtonSelected=1;
	private int checkboxSelected=0;
	private int hasPolygon=0;
	private int pointQueryX=0;
	private int pointQueryY=0;
	private int []rangeQueryX;
	private int []rangeQueryY;
	private int queryCount=0;
	

	public hw2GUI(){
		if (jFrame==null){
			hw2=new HW2();
			
			jFrame = new JFrame("CSCI585HW2, Name = Wang, Lifei, Uscid = 6776835749");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setSize(1320, 730); //image 820*580
			jFrame.getContentPane().setLayout(null); 
			queryCount=0;
		}
	}
	public void buildView(){
		createJLabels();
		createCheckbox();
		createJTextField(); 
		createJTextArea();
		createJButton();
		createMapPanel();
		createQueryButtonGroup();
		jFrame.setVisible(true);
	}
	
	public int getRadioButtonValue(){
		return radioButtonSelected;
	}
	
	public int getCheckBoxValue(){
		return checkboxSelected;
	}
	
	private int createJLabels() {
		JLabel jLabel1 = new javax.swing.JLabel(); 
        jLabel1.setBounds(920, 30, 400, 40); 
        jLabel1.setText("Active Feature Type:");
        jFrame.add(jLabel1);
        JLabel jLabel2 = new javax.swing.JLabel(); 
        jLabel2.setBounds(920, 200, 400, 40); 
        jLabel2.setText("Query:");
        jFrame.add(jLabel2);
		JLabel jLabelx = new javax.swing.JLabel(); 
		jLabelx.setBounds(50, 600, 30, 50); 
		jLabelx.setText("X = ");
        jFrame.add(jLabelx);
        jFrame.add(jLabel2);
		JLabel jLabely = new javax.swing.JLabel(); 
		jLabely.setBounds(160, 600, 30, 50); 
		jLabely.setText("Y = ");
        jFrame.add(jLabely);
	    return 0; 
	   }
	
	private int createCheckbox(){
		//Create the check boxes.
        ASCB = new JCheckBox("AS");
        ASCB.setSelected(false);
        ASCB.setBounds(920, 70, 100, 40);

        BuildingCB = new JCheckBox("Building");
        BuildingCB.setSelected(false);
        BuildingCB.setBounds(920, 110, 100, 40);

        StudentCB = new JCheckBox("Student");
        StudentCB.setSelected(false);
        StudentCB.setBounds(920, 150, 100, 40);
        
        jFrame.add(ASCB);
        jFrame.add(BuildingCB);
        jFrame.add(StudentCB);
        
        ASCB.addItemListener(this);
        BuildingCB.addItemListener(this);
        StudentCB.addItemListener(this);

		return 0;
	}
	  

	private void createJTextField() { 
	   if(jTextFieldx == null) { 
	      jTextFieldx = new javax.swing.JTextField(); 
	      jTextFieldx.setBounds(80, 600, 50, 50); 
	      jTextFieldx.setEditable(false);
	      jTextFieldx.setText("0");
	      jFrame.add(jTextFieldx);
	   } 
	   if(jTextFieldy == null) { 
		   jTextFieldy = new javax.swing.JTextField(); 
		   jTextFieldy.setBounds(180, 600, 50, 50);
		   jTextFieldy.setEditable(false);
		   jTextFieldy.setText("0");
		   jFrame.add(jTextFieldy);
	   }
	}
	
	private javax.swing.JTextArea createJTextArea(){
		if (resultArea == null){
			JPanel panel=new JPanel();
			panel.setBounds(500, 600, 700, 80);
			panel.setPreferredSize(new Dimension(1,60));
			panel.setLayout(new BorderLayout());
			resultArea = new javax.swing.JTextArea(1,60);

			resultArea.setLineWrap(true);
			resultArea.setEditable(false);
			resultArea.setAlignmentX(Component.LEFT_ALIGNMENT);
			resultArea.setText("Your submitted query should be displayed here\n");
			
			JScrollPane remarkTextAreaScrollPane = new JScrollPane(resultArea);
//			remarkTextAreaScrollPane.setViewportView(resultArea);
//			remarkTextAreaScrollPane.setBounds(750, 600, 20, 100);
			
			panel.add(remarkTextAreaScrollPane, BorderLayout.WEST);
//			panel.add(resultArea,BorderLayout.WEST);
			jFrame.add(panel);
		}
		return resultArea;
	}
	  
	private javax.swing.JButton createJButton() 
	{ 
	      if(submitButton == null) { 
	    	  submitButton = new javax.swing.JButton(); 
	    	  submitButton.setBounds(920, 500, 200, 40); 
	    	  submitButton.setText("Submit Query");
	    	  submitButton.setActionCommand("Submit");
	    	  submitButton.addActionListener(this);
	    	  jFrame.add(submitButton);
	      } 
	      return submitButton; 
	} 
	   
	private void createQueryButtonGroup()
	{
		if (qButtonGroup==null){
			qButtonGroup=new ButtonGroup();
			JRadioButton wholeRegionButton = new JRadioButton("Whole Region");
			wholeRegionButton.setBounds(920, 250, 200, 50);
			wholeRegionButton.setActionCommand("01");
			wholeRegionButton.setSelected(true);
			qButtonGroup.add(wholeRegionButton);
			JRadioButton pointQueryButton = new JRadioButton("Point Query");
			pointQueryButton.setBounds(920, 300, 200, 50);
			pointQueryButton.setActionCommand("02");
			qButtonGroup.add(pointQueryButton);
			JRadioButton rangeQueryButton = new JRadioButton("Range Query");
			rangeQueryButton.setBounds(920, 350, 200, 50);
			rangeQueryButton.setActionCommand("03");
			qButtonGroup.add(rangeQueryButton);
			JRadioButton surroundingStuButton = new JRadioButton("Surrounding Student");
			surroundingStuButton.setBounds(920, 400, 200, 50);
			surroundingStuButton.setActionCommand("04");
			qButtonGroup.add(surroundingStuButton);
			JRadioButton emergencyQueryButton = new JRadioButton("Emergency Query");
			emergencyQueryButton.setBounds(920, 450, 200, 50);
			emergencyQueryButton.setActionCommand("05");
			qButtonGroup.add(emergencyQueryButton);
			
			emergencyQueryButton.addActionListener(this);
			surroundingStuButton.addActionListener(this);
			rangeQueryButton.addActionListener(this);
			pointQueryButton.addActionListener(this);
			wholeRegionButton.addActionListener(this);
			   
			jFrame.add(emergencyQueryButton);
			jFrame.add(surroundingStuButton);
			jFrame.add(rangeQueryButton);
			jFrame.add(pointQueryButton);
			jFrame.add(wholeRegionButton);

		}
	}
	
	public void createMapPanel()
	{
		if (lpane==null){
			lpane = new JLayeredPane();
			lpane.setBounds(0, 0, 820, 580);
			
//			mapPanel = new JPanel();
//			mapPanel.setLayout(null);
//			mapPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
//			mapPanel.setBounds(0, 0, 820, 580);
//			mapPanel.setPreferredSize(new Dimension(820,580));
//			mapPanel.setOpaque(false);
			
			imageLabel=new JLabel();
			imageLabel.setIcon(new javax.swing.ImageIcon("map.jpg"));
			imageLabel.setBounds(0, 0, 820, 580);
			
			lpane.add(imageLabel, new Integer(0), 0);
//	        lpane.add(mapPanel, new Integer(1), 0);
//	        mapPanel.repaint();
			
			lpane.addMouseListener(this);
			lpane.addMouseMotionListener(this);
			
			jFrame.getContentPane().add(lpane);
			
		}
	}
	   

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String status=e.getActionCommand();
		
		switch (status){
		case "01":{
			radioButtonSelected=1;
			if (mapPanel != null){
				lpane.remove(mapPanel);
				mapPanel = null;
				jFrame.repaint();
			}
			if (mapPanel2 != null){
				lpane.remove(mapPanel2);
				mapPanel2 = null;
				hasPolygon=0;
				jFrame.repaint();
			}
			break;
		}
		case "02":{
			radioButtonSelected=2;
			if (mapPanel2 != null){
				lpane.remove(mapPanel2);
				mapPanel2 = null;
				hasPolygon=0;
				jFrame.repaint();
			}
			break;
		}
		case "03":{
			radioButtonSelected=3;
			if (mapPanel != null){
				lpane.remove(mapPanel);
				mapPanel = null;
				jFrame.repaint();
			}
			break;
		}
		case "04":{
			radioButtonSelected=4;
			if (mapPanel != null){
				lpane.remove(mapPanel);
				mapPanel = null;
				jFrame.repaint();
			}
			if (mapPanel2 != null){
				lpane.remove(mapPanel2);
				mapPanel2 = null;
				hasPolygon=0;
				jFrame.repaint();
			}
			break;
		}
		case "05":{
			radioButtonSelected=5;
			if (mapPanel != null){
				lpane.remove(mapPanel);
				mapPanel = null;
				jFrame.repaint();
			}
			if (mapPanel2 != null){
				lpane.remove(mapPanel2);
				mapPanel2 = null;
				hasPolygon=0;
				jFrame.repaint();
			}
			break;
		}
		case "Submit":{
			executeQuery();
			break;
			}
		default:
			break;
		}
	}
	
	public void executeQuery(){
//		String text = null;
//		text = " CheckBox:" + String.valueOf(checkboxSelected) + " Radio:" + String.valueOf(radioButtonSelected);
//		resultArea.append(text);
//		resultArea.append("\n");
		
		lpane.removeAll();
		lpane.add(imageLabel, new Integer(0), 0);
		
		//Whole Region Query
		if (radioButtonSelected==1){
			int choice=checkboxSelected;
			queryCount++;
			resultArea.append("Query "+queryCount+":\n");
			if (choice/100 == 1){
				JPanel panel1 = hw2.WholeRegionAnnouncementQuery();
				resultArea.append("SELECT * FROM announcement\n");
				lpane.add(panel1, new Integer(2),1);
			}
			choice = choice % 100;
			if (choice/10 == 1){
				JPanel panel2 = hw2.WholeRegionBuildingQuery();
				resultArea.append("SELECT * FROM building\n");
				lpane.add(panel2, new Integer(2),2);
			}
			choice = choice % 10;
			if (choice == 1){
				JPanel panel3 = hw2.WholeRegionStudentQuery();
				resultArea.append("SELECT * FROM student\n");
				lpane.add(panel3, new Integer(2),3);
			}
		}
		//Point Query
		if (radioButtonSelected==2){
			lpane.add(mapPanel, new Integer(1),0);
			if (pointQueryX== 0 && pointQueryY == 0){
				resultArea.append("Select a point first");
			}
			else{
				queryCount++;
				resultArea.append("Query "+queryCount+":\n");
				int choice=checkboxSelected;
				if (choice/100 == 1){
					JPanel panel1 = hw2.PointAnnouncementQuery(pointQueryX, pointQueryY);
					resultArea.append("SELECT a.aslocation, a.radius FROM announcement a WHERE sdo_within_distance(a.aslocation, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+pointQueryX+","+pointQueryY+",NULL),NULL,NULL),'distance=50+a.radius') = 'TRUE'\n");
					lpane.add(panel1, new Integer(1),1);
				}
				choice = choice % 100;
				if (choice/10 == 1){
					JPanel panel2 = hw2.PointBuildingQuery(pointQueryX, pointQueryY);
					resultArea.append("SELECT b.blocation from building b WHERE SDO_RELATE(b.blocation, mdsys.sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,4), mdsys.sdo_ordinate_array("+pointQueryX+","+(pointQueryY-50)+","+(pointQueryX+50)+","+pointQueryY+","+pointQueryX+","+(pointQueryY+50)+")), 'mask=ANYINTERACT') = 'TRUE'\n");
					lpane.add(panel2, new Integer(1),2);
				}
				choice = choice % 10;
				if (choice == 1){
					JPanel panel3 = hw2.PointStudentQuery(pointQueryX, pointQueryY);
					resultArea.append("SELECT s.plocation FROM student s WHERE sdo_within_distance(S.plocation, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+pointQueryX+","+pointQueryY+",NULL),NULL,NULL),'distance=50') = 'TRUE'\n");
					lpane.add(panel3, new Integer(1),3);
				}
			}
		}
		//Range Query
		if (radioButtonSelected == 3){
			lpane.add(mapPanel2, new Integer(1),0);
			if (rangeQueryX.length < 3){
				resultArea.append("Select at least three points first");
			}
			else{
				queryCount++;
				resultArea.append("Query "+queryCount+":\n");
				int choice=checkboxSelected;
				String query2 = "";
				for(int i=0; i < rangeQueryX.length && rangeQueryX[i] != 0; i++)
				{
					if(i==0){
				    	query2 = query2 + rangeQueryX[i];
					}
				    else{
				    	query2 = query2 +","+ rangeQueryX[i];
					}
					query2 = query2 +","+ rangeQueryY[i];
				}
				query2 = query2 + "," + rangeQueryX[0] + "," + rangeQueryY[0];
				String query3 = ")),'mask=ANYINTERACT') = 'TRUE'";
				
				if (choice/100 == 1){
					String query1 = "SELECT a.aslocation,a.radius,SDO_GEOM.SDO_DISTANCE(a.aslocation,"+"SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("
									+query2+")),0.005) AS dist"+
								" FROM announcement a WHERE SDO_WITHIN_DISTANCE(a.aslocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(";
					String query4 = ")), 'distance = 100') = 'TRUE'";
					String query = query1 + query2 + query4;
					resultArea.append(query);
					resultArea.append("\n");
					
					JPanel panel1=hw2.RangeAnnouncementQuery(query);
					lpane.add(panel1, new Integer(1),1);
				}
				choice = choice % 100;
				if (choice/10 == 1){
					String query1 =  "SELECT b.blocation FROM building b WHERE SDO_RELATE(b.blocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(";
					String query = query1 + query2 + query3;
					resultArea.append(query);
					resultArea.append("\n");
					
					JPanel panel2=hw2.RangeBuildingQuery(query);
					lpane.add(panel2, new Integer(1),2);
				}
				choice = choice % 10;
				if (choice == 1){
					String query1 =  "SELECT s.plocation FROM student s WHERE SDO_RELATE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(";
					String query = query1 + query2 + query3;
					resultArea.append(query);
					resultArea.append("\n");
					
					JPanel panel3=hw2.RangeStudentQuery(query);
					lpane.add(panel3, new Integer(1),3);
				}
			}
		}
		//Surrounding query
		if (radioButtonSelected == 4){
			lpane.add(nnaPanel, new Integer(1),0);
			if (pointQueryX== 0 && pointQueryY == 0){
				resultArea.append("Select a point first");
			}
			else{
				queryCount++;
				resultArea.append("Query "+queryCount+":\n");
				JPanel panel=hw2.SurroundingStudentQuery(pointQueryX, pointQueryY);
				lpane.add(panel, new Integer(1),1);
				String sbmt1 = "SELECT a.aslocation.sdo_point.x AS ax,a.aslocation.sdo_point.y AS ay,a.radius AS ar FROM announcement a WHERE "
		  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+pointQueryX+","+pointQueryY+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
				String sbmt2 = "SELECT s.plocation FROM student s WHERE SDO_INSIDE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),"
									+"SDO_ORDINATE_ARRAY(ax,(ay-ar),(ax+ar),ay,ax,(ay+ar))))='TRUE'";
				String sbmt = sbmt1 + "\n" + sbmt2 +"\n";
				resultArea.append(sbmt);
			}
		}
		if (radioButtonSelected == 5){
			lpane.add(nnaPanel, new Integer(1),0);
			if (pointQueryX== 0 && pointQueryY == 0){
				resultArea.append("Select a point first");
			}
			else{
				queryCount++;
				resultArea.append("Query "+queryCount+":\n");
				JPanel panel=hw2.EmergencyQuery(pointQueryX, pointQueryY);
				lpane.add(panel, new Integer(1),1);
				String sbmt1 = "SELECT a.aslocation.sdo_point.x AS ax,a.aslocation.sdo_point.y AS ay,a.radius AS ar FROM announcement a WHERE "
		  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+pointQueryX+","+pointQueryY+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
				String sbmt2 = "SELECT s.plocation FROM student s WHERE SDO_INSIDE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),"
						+"SDO_ORDINATE_ARRAY(ax,(ay-aradius),(ax+aradius),ay,ax,(ay+aradius))))='TRUE'";
				String sbmt3 = "SELECT a.aslocation.sdo_point.x,a.aslocation.sdo_point.y,a.radius,SDO_NN_DISTANCE(1) dist FROM announcement a WHERE SDO_NN(a.aslocation,SDO_GEOMETRY"+
						"(2001,NULL,sdo_point_type(s.plocation.sdo_point.x,s.plocation.sdo_point.y,NULL),NULL,NULL),'sdo_num_res=5',1) = 'TRUE' ORDER BY dist ASC";
				String sbmt = sbmt1 + "\n" +sbmt2 + "\nFor each student\n" +sbmt3 + "\n";
				resultArea.append(sbmt);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		Object source = ie.getItemSelectable();
		int index = 0;
        if (source == ASCB) {
            index = 100;
        } else if (source == BuildingCB) {
            index = 10;
        } else if (source == StudentCB) {
            index = 1;
        }
        
        if (ie.getStateChange() == ItemEvent.DESELECTED) {
            index = 0 - index;
        }
        
        checkboxSelected = checkboxSelected + index;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		int x = (int)me.getPoint().getX();
		int y = (int)me.getPoint().getY();
//		resultArea.append("Clicked at (" + x +"," + y +")");
		if (radioButtonSelected == 2){
			if (mapPanel != null){
				lpane.remove(mapPanel);
			}
			mapPanel = null;
			mapPanel = new MapPanel(x, y, 0);
			lpane.add(mapPanel, new Integer(1),0);
			pointQueryX = x;
			pointQueryY = y;
		}
		if (radioButtonSelected == 3){
			if (hasPolygon==1){
				//right click again to delete the polygon.
				if (me.getButton()==MouseEvent.BUTTON3){
					hasPolygon=0;
					lpane.remove(mapPanel2);
					lpane.repaint();
					mapPanel2=null;
				}
			}
			else{
				if (mapPanel2 == null){
					mapPanel2 = new MapPanel2(x,y);
					rangeQueryX=new int[20];
					rangeQueryX[0]=x;
					rangeQueryY=new int[20];
					rangeQueryY[0]=y;
					lpane.add(mapPanel2, new Integer(1),0);
				}
				else{
					if (me.getButton()==MouseEvent.BUTTON3){
						mapPanel2.setClosed(true);
						hasPolygon=1;
					}
					else{
						mapPanel2.addPoint(x, y);
					}
				}
			}
		}
		if (radioButtonSelected == 4){
			if (mapPanel != null){
				lpane.remove(mapPanel);
			}
			if (nnaPanel != null){
				lpane.remove(nnaPanel);
			}
			nnaPanel = null;
			mapPanel = null;
			pointQueryX=x;
			pointQueryY=y;
			mapPanel = new MapPanel(x, y, 1);
			lpane.add(mapPanel, new Integer(1),0);
			nnaPanel=hw2.NearestAnnouncementQuery(x, y);
			lpane.add(nnaPanel, new Integer(1),1);
			lpane.repaint();
		}
		if (radioButtonSelected == 5){
			if (mapPanel != null){
				lpane.remove(mapPanel);
			}
			if (nnaPanel != null){
				lpane.remove(nnaPanel);
			}
			nnaPanel = null;
			mapPanel = null;
			pointQueryX=x;
			pointQueryY=y;
			mapPanel = new MapPanel(x, y, 1);
			lpane.add(mapPanel, new Integer(1),0);
			nnaPanel=hw2.NearestAnnouncementQuery(x, y);
			lpane.add(nnaPanel, new Integer(1),1);
			lpane.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		double x = me.getPoint().getX();
		double y = me.getPoint().getY();
		jTextFieldx.setText(String.valueOf(x));
		jTextFieldy.setText(String.valueOf(y));
	}
	
	class MapPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1240816921580802600L;
		
		private int x;
		private int y;
		private int type; //0 for query 2, 1 for query 4
		MapPanel(int cx, int cy, int t) {
            // set a preferred size for the custom panel.
            this.setPreferredSize(new Dimension(820,580));
            this.setBounds(0, 0, 820, 580);
            this.setOpaque(false);
            x=cx;
            y=cy;
            type=t;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.red);
            
            if (type==0){
            	g.fillRect(x-3, y-3, 6, 6);
            	g.drawOval(x-50, y-50, 100, 100);
            }
            if (type==1){
            	g.fillRect(x-1, y-1, 2, 2);
            }
        }
	}
	
	class MapPanel2 extends JPanel //for query 3
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2370591288660402437L;
		private int []x;
		private int []y;
		private int index;
		private boolean closed;
		MapPanel2(int cx, int cy) {
            // set a preferred size for the custom panel.
            this.setPreferredSize(new Dimension(820,580));
            this.setBounds(0, 0, 820, 580);
            this.setOpaque(false);
            x=new int[20];
            y=new int[20];
            x[0]=cx;
            y[0]=cy;
            index=1;
            closed = false;
        }
		
		MapPanel2(int []cx, int []cy) {
            // set a preferred size for the custom panel.
            this.setPreferredSize(new Dimension(820,580));
            this.setBounds(0, 0, 820, 580);
            this.setOpaque(false);
            x=cx;
            y=cy;
            if (cx.length > 2){
            	closed=true;
            }
            else{
            	closed=false;
            }
        }
		
		public void addPoint(int a, int b){
			if (index < 20){
				x[index]=a;
				y[index]=b;
				rangeQueryX[index]=a;
				rangeQueryY[index]=b;
				index++;
				repaint();
			}
		}
		
		public void setClosed(boolean b){
			if (x.length > 2){
				closed=b;
				repaint();
			}
			else{
				resultArea.append("Choose at least three points.");
			}
		}
		
		@Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.red);
            if (closed){
            	g.drawPolygon(x, y, index);
            }
            else{
            	g.drawPolyline(x, y, index);
            }
        }
	}
}
