package csci585hw2;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import oracle.sdoapi.OraSpatialManager;
import oracle.sdoapi.geom.*;
import oracle.sdoapi.adapter.*;
//import oracle.sdoapi.sref.*;
import oracle.sql.STRUCT;


public class HW2 {
	Connection mainConnection=null;
	boolean submitMode=true;
	
  	public static void main(String[] args)
    {
  		hw2GUI ui=new hw2GUI();
  		ui.buildView();
  		
    }
  	
  	public HW2(){
  		mainConnection = ConnectToDB();
  	}
  	
  	public Connection ConnectToDB()
 	{
		try
		{
			Connection conn=null;
			// loading Oracle Driver
    		System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
	    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	    	System.out.println(", Loaded.");
	    	String URL = null;
	    	String userName = null;
	    	String password = null;
	    	if (submitMode==true){
	    	//submit url
	    		URL = "jdbc:oracle:thin:@localhost:1521:hw2";
            	userName = "system";
            	password = "hw2";
	    	}else{
	    		//my url
	    		URL = "jdbc:oracle:thin:@localhost:1521:hw1csci585";
	    		userName = "SYSTEM";
	    		password = "123456";
	    	}

	    	System.out.print("Connecting to DB...");
	    	conn = DriverManager.getConnection(URL, userName, password);
	    	System.out.println(", Connected!");
	    	
	    	return conn;
	    	
   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
		return null;
 	}
  	
    public void ShowMetaData(ResultSet mainResultSet)
    {
    	try
		{
                                // shows meta data
//    	    System.out.println("\n ** Obtaining Meta Data ** " );
	        ResultSetMetaData meta = mainResultSet.getMetaData();
	        for( int col=1; col<=meta.getColumnCount(); col++ )
		        System.out.println( "Column: " + meta.getColumnName(col) + "\t, Type: " + meta.getColumnTypeName(col) );
    	}
		catch( Exception e )
    	{ System.out.println( " Error : " + e.toString() ); }
    }
  	
  	
  	public JPanel WholeRegionStudentQuery()
  	{
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  		
  		class AllStudentPanel extends JPanel
  		{

  			/**
  			 * 
  			 */
  			private static final long serialVersionUID = -7053750467599770975L;
  			AllStudentPanel(){
  				this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  			}
  			

  			@Override
  		    public void paintComponent(Graphics g) 
  				{
  				super.paintComponent(g);
  			  		try {
  			  			System.out.println("WholeRegionStudentQuery");
  			    		String query=null;
  			    		query = "select * from student";
  			  			Statement stmt=mainConnection.createStatement();
  						ResultSet rs=null;
  						STRUCT point;		//Structure to handle Geometry Objects
  						Geometry geom;     	//Structure to handle Geometry Objects
  						rs = stmt.executeQuery(query);
//  						ShowMetaData(rs);
//  						int tupleCount=1;

  				  		@SuppressWarnings("deprecation")
  						GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

  			 	        while( rs.next() )
  			    	    {
//  				    	    System.out.print( "Tuple " + tupleCount++ + " : " );

//  				    	    String Point_ID = rs.getString( 1 );
//  				    	    System.out.print( "\"" + Point_ID + "\"," );

  				    	    point = (STRUCT)rs.getObject(2);

  							geom = sdoAdapter.importGeometry( point );
  			      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
  			      			{
  								oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
  								double X = point0.getX();
  								double Y = point0.getY();
  								g.setColor(Color.green);
  								g.fillRect((int) X-5, (int) Y-5, 10, 10);
//  								System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
  							}

//  					        System.out.println();
  			       	    }
  					} catch (Exception e) {
  						// TODO Auto-generated catch block
  						System.out.println( " Error : " + e.toString() );
  					}
  				}
  		}
   		
  		AllStudentPanel panel=new AllStudentPanel();
  		return panel;

  	}
  	
  	public JPanel WholeRegionAnnouncementQuery()
  	{
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  		
  		class AllAnnouncementPanel extends JPanel
  		{
  			/**
			 * 
			 */
			private static final long serialVersionUID = 4394555089816823733L;

			public AllAnnouncementPanel(){
  				this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  			}
  			
  			@Override
  			public void paintComponent(Graphics g){
  				super.paintComponent(g);
  				try {
  		  			String query=null;
  		  	  		query = "select * from announcement";
  		  			Statement stmt=mainConnection.createStatement();
  					ResultSet rs=null;
  					STRUCT point;		//Structure to handle Geometry Objects
  					Geometry geom;     	//Structure to handle Geometry Objects
  					rs = stmt.executeQuery(query);
//  					ShowMetaData(rs);
//  					int tupleCount=1;

  			  		@SuppressWarnings("deprecation")
  					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);
  			  		
  			  		g.setColor(Color.red);
  			  		
  		 	        while( rs.next() )
  		    	    {
//  			    	    System.out.print( "Tuple " + tupleCount++ + " : " );

//  			    	    String Point_ID = rs.getString( 1 );
//  			    	    System.out.print( "" + Point_ID + ", " );

  			    	    point = (STRUCT)rs.getObject(2);
  			    	    int X=0;
  			    	    int Y=0;
  						geom = sdoAdapter.importGeometry( point );
  		      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
  		      			{
  							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
  							X = (int)point0.getX();
  							Y = (int)point0.getY();
  							g.fillRect((int)X-7, (int)Y-7, 15, 15);
//  							System.out.print( "(X = " + X + ", Y = " + Y + ")" );
  						}
  		      			
  		      			int radius = rs.getInt(3);
  		      			g.drawOval(X-radius, Y-radius, radius*2, radius*2);
//  			    	    System.out.print( ", " + radius);

//  				        System.out.println();
  		       	    }
  				} catch (Exception e) {
  					// TODO Auto-generated catch block
  					System.out.println( " Error : " + e.toString() );
  				}
  			}
  		}
  		
  		AllAnnouncementPanel panel=new AllAnnouncementPanel();
  		return panel;
  		
  	}
  	
  	public JPanel WholeRegionBuildingQuery()
  	{
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("WholeRegionBuildingQuery");
  		
  		class AllBuildingPanel extends JPanel
  		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1894118100342729642L;

			public AllBuildingPanel(){
  				this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  			}
  			
  			@Override
  			public void paintComponent(Graphics g){
  				super.paintComponent(g);
  				
  				try{

  		  			Statement stmt = mainConnection.createStatement();
  		  			ResultSet rs = stmt.executeQuery("select blocation from building");

  		  			STRUCT point; //Structure to handle Geometry Objects
  		  			oracle.sdoapi.geom.Geometry geom; //Structure to handle Geometry Objects

  		  			@SuppressWarnings("deprecation")
  					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9", STRUCT.class, null, null, mainConnection);

//  		  			int bid = 0;
  		  			g.setColor(Color.yellow);
  		  			
  		  			while (rs.next())
  		  			{
//  		  				System.out.println((bid+1) +":");

  		  				point = (STRUCT)rs.getObject(1);
  		  				geom = sdoAdapter.importGeometry(point);
  		  			
  		  				if(( geom instanceof oracle.sdoapi.geom.Polygon)) // Check whether the retrieved instance is polygon
  		  				{
  		  					oracle.sdoapi.geom.Polygon poly = (oracle.sdoapi.geom.Polygon) geom;

  		  					oracle.sdoapi.geom.CurveString cs = poly.getExteriorRing();
  		  					oracle.sdoapi.geom.LineString line =(oracle.sdoapi.geom.LineString)cs;
  		  					CoordPoint[] coordArray = line.getPointArray();
  		  					
  		  					int bx[]=new int[coordArray.length];
  		  					int by[]=new int[coordArray.length];

  		  					for(int i=0; i<coordArray.length;i++)
  		  					{
  		  						bx[i]=(int)coordArray[i].getX();
  		  						by[i]=(int)coordArray[i].getY();
  		  						
//  		  					System.out.println("X: "+bx[i]);
//  		  	  				System.out.println("Y: "+by[i]);
  		  					}
  		  					g.drawPolygon(bx, by, coordArray.length);
  		  				} //stmt.close();
//  		  				bid++;

  		  			}
  		  		}
  		  		catch(Exception e){
  		  		 	e.printStackTrace();
  		  		}
  			}
  		}
  		AllBuildingPanel panel=new AllBuildingPanel();
  		return panel;
  	}
  	
  	public JPanel PointStudentQuery(int px, int py)
  	{
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("PointStudentQuery");
  	 	
  	 	class PointStudentPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = 8434378968985708622L;
			private int x;
  	 		private int y;
  	 		PointStudentPanel(int a, int b){
  	 			this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  				x = a;
  				y = b;
  	 		}
  	 		
  	 		@Override
  			public void paintComponent(Graphics g){
  				super.paintComponent(g);
  				try {
			    	String query=null;
			    	query = "SELECT s.plocation FROM student s WHERE sdo_within_distance(s.plocation, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+x+","+y+",NULL),NULL,NULL),'distance=50') = 'TRUE'";
			  		String querynn = "SELECT s.plocation.sdo_point.x,s.plocation.sdo_point.y FROM student s WHERE "
			  				+ "SDO_NN(s.plocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
			    	Statement stmt=mainConnection.createStatement();

					ResultSet rs=null;
					ResultSet rsnn=stmt.executeQuery(querynn);
					int xnn=0;
					int ynn=0;
					while(rsnn.next()){
						xnn=rsnn.getInt(1);
						ynn=rsnn.getInt(2);
					}
//					ShowMetaData(rs);
					rs = stmt.executeQuery(query);
					
//					int tupleCount=1;
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					
				  	@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			 	    while( rs.next() )
			    	{
//			 	    	System.out.print( "Tuple " + (tupleCount++) + " : " );

				    	point = (STRUCT)rs.getObject(1);

						geom = sdoAdapter.importGeometry( point );
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
							if (X ==xnn && Y == ynn){
								g.setColor(Color.yellow);
							}
							else{
								g.setColor(Color.green);
							}
							g.fillRect(X-5,  Y-5, 10, 10);
//							System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
						}
//			      		System.out.println();
			       	}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println( " Error : " + e.toString() );
				}
  	 		}
  	 	}
  	 	
  	 	PointStudentPanel panel = new PointStudentPanel(px, py);
  	 	return panel;
  	}
  	
  	public JPanel PointAnnouncementQuery(int px, int py){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("pointAnnouncementQuery");
  	 	
  	 	class PointAnnouncementPanel extends JPanel{

			/**
			 * 
			 */
			private static final long serialVersionUID = 361554669387682001L;
			private int x;
  	 		private int y;
  	 		PointAnnouncementPanel(int a, int b){
  	 			this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  				x = a;
  				y = b;
  	 		}
  	 		
  	 		@Override
  			public void paintComponent(Graphics g){
  				super.paintComponent(g);
  				try {
			    	String query=null;
			    	query = "SELECT a.aslocation, a.radius FROM announcement a WHERE sdo_within_distance(a.aslocation, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+x+","+y+",NULL),NULL,NULL),'distance=150') = 'TRUE'";
			  		String querynn = "SELECT a.aslocation.sdo_point.x,a.aslocation.sdo_point.y,a.radius FROM announcement a WHERE "
			  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
			    	Statement stmt=mainConnection.createStatement();

					ResultSet rs=null;
					ResultSet rsnn=stmt.executeQuery(querynn);
					int xnn=0;
					int ynn=0;
					int rnn=0;
					while(rsnn.next()){
						xnn=rsnn.getInt(1);
						ynn=rsnn.getInt(2);
						rnn=rsnn.getInt(3);
//						System.out.println("nn:"+xnn+","+ynn+","+rnn+",");
					}
//					ShowMetaData(rs);
					rs = stmt.executeQuery(query);
					
//					int tupleCount=1;
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					
				  	@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			 	    while( rs.next() )
			    	{
//			 	    	System.out.print( "Tuple " + tupleCount++ + " : " );

				    	point = (STRUCT)rs.getObject(1);
				    	int radius = rs.getInt(2);
				    	g.setColor(Color.green);

						geom = sdoAdapter.importGeometry( point );
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
							double dist = (Math.sqrt((X-x)*(X-x) + (Y-y)*(Y-y)));
							if (dist > (50 + radius)){
								continue;
							}
							if (X ==xnn && Y == ynn & radius == rnn){
								g.setColor(Color.yellow);
							}
							else{
								g.setColor(Color.green);
							}
							g.fillRect(X-7,  Y-7, 15, 15);
							g.drawOval(X-radius, Y-radius, radius*2, radius*2);
//							System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
						}
			      		
//			      		System.out.println();
			       	}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println( " Error : " + e.toString() );
				}
  	 		}
  	 	}
  	 	PointAnnouncementPanel panel = new PointAnnouncementPanel(px, py);
  	 	return panel;
  	}
  	
  	public JPanel PointBuildingQuery(int px, int py)
  	{
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("PointBuildingQuery");
  	 	
  	 	class PointBuildingPanel extends JPanel{
			/**
			 * 
			 */
			private static final long serialVersionUID = -6034907322541039442L;
			private int x;
  	 		private int y;
  	 		private CoordPoint[] nncoordArray;
  	 		PointBuildingPanel(int px, int py){
  	 			this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  	 			x=px;
  	 			y=py;
  	 		}
  	 		
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			
  	 			try{
  	 				String query=null;
  	 				query="SELECT b.blocation from building b WHERE SDO_RELATE(b.blocation, sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,4), mdsys.sdo_ordinate_array("+x+","+(y-50)+","+(x+50)+","+y+","+x+","+(y+50)+")), 'mask=ANYINTERACT') = 'TRUE'";
  	 				String querynn="SELECT b.blocation from building b WHERE SDO_NN(b.blocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
  	 				Statement stmt = mainConnection.createStatement();
  	 				ResultSet rsnn=stmt.executeQuery(querynn);
  	 				
  	 				@SuppressWarnings("deprecation")
  					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9", STRUCT.class, null, null, mainConnection);
  	 				
  	 				while (rsnn.next()){
  	 					STRUCT nnstruct=(STRUCT)rsnn.getObject(1);
  	 					oracle.sdoapi.geom.Geometry nnbuilding = sdoAdapter.importGeometry(nnstruct);
  	 					if(( nnbuilding instanceof oracle.sdoapi.geom.Polygon)){
  	 						oracle.sdoapi.geom.Polygon nnpoly = (oracle.sdoapi.geom.Polygon) nnbuilding;
  		  					oracle.sdoapi.geom.CurveString nncs = nnpoly.getExteriorRing();
  		  					oracle.sdoapi.geom.LineString nnline =(oracle.sdoapi.geom.LineString)nncs;
  		  					nncoordArray = nnline.getPointArray();
  	 					}
  	 				}
  	 				
  		  			ResultSet rs = stmt.executeQuery(query);

  		  			STRUCT point; //Structure to handle Geometry Objects
  		  			oracle.sdoapi.geom.Geometry geom; //Structure to handle Geometry Objects

  		  			while (rs.next())
  		  			{
//  		  				System.out.println((bid+1) +":");

  		  				point = (STRUCT)rs.getObject(1);
  		  				geom = sdoAdapter.importGeometry(point);

  		  			
  		  				if(( geom instanceof oracle.sdoapi.geom.Polygon)) // Check whether the retrieved instance is polygon
  		  				{
  		  					oracle.sdoapi.geom.Polygon poly = (oracle.sdoapi.geom.Polygon) geom;

  		  					oracle.sdoapi.geom.CurveString cs = poly.getExteriorRing();
  		  					oracle.sdoapi.geom.LineString line =(oracle.sdoapi.geom.LineString)cs;
  		  					CoordPoint[] coordArray = line.getPointArray();
  		  					
  		  					if (coordArray[0].getX()==nncoordArray[0].getX() && coordArray[0].getY()==nncoordArray[0].getY()){
  		  						g.setColor(Color.yellow);
  		  					}
  		  					else{
  		  						g.setColor(Color.green);
  		  					}
  		  					
  		  					int bx[]=new int[coordArray.length];
  		  					int by[]=new int[coordArray.length];

  		  					for(int i=0; i<coordArray.length;i++)
  		  					{
  		  						bx[i]=(int)coordArray[i].getX();
  		  						by[i]=(int)coordArray[i].getY();
//  		  						System.out.print(" "+bx[i]+","+by[i]);
  		  					}
  		  					g.drawPolygon(bx, by, coordArray.length);
  		  				} //stmt.close();
  		  			}
  	 			}catch (Exception e){
  	 				System.out.println( " Error : " + e.toString());
  	 			}
  	 		}
  	 	}
  	 	PointBuildingPanel panel = new PointBuildingPanel(px, py);
  	 	return panel;
  	}
  	
  	public JPanel RangeStudentQuery(String q){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("RangeStudentQuery");
  	 	
  	 	class RangeStudentPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = -3457386809056724391L;
//			private int []px;
//  	 	private int []py;
  	 		private String query;
//  	 		RangeStudentPanel(int []ax, int []ay){
//  	 			this.setBounds(0, 0, 820, 580);
//  	 			this.setOpaque(false);
//  	 			px=ax;
//  	 			py=ay;
//  	 		}
  	 		
  	 		RangeStudentPanel(String s){
  	 			this.setBounds(0, 0, 820, 580);
  	 			this.setOpaque(false);
  	 			query=s;
  	 		}
  	 		
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			try{
//  	 				String query1 =  "SELECT s.plocation FROM student s WHERE SDO_RELATE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(";
//  	 				String query2 = "";
//  	 				for(int i=0; i < px.length; i++)
//  	 			  	{
//  	 					if(i==0){
//  	 			    		query2 = query2 + px[i];
//  	 					}
//  	 			    	else{
//  	 			    		query2 = query2 +","+ px[i];
//  	 					}
//  	 					query2 = query2 +","+ py[i];
//  	 			    }
//  	 				query2 = query2 + "," + px[0] + "," + py[0];
//  	 				String query3 = ")),'mask=ANYINTERACT') = 'TRUE'";
//  	 				String query = query1 + query2 + query3;
  	 				
  	 				Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					rs = stmt.executeQuery(query);
//					ShowMetaData(rs);
//					int tupleCount=1;

				  	@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			 	    while( rs.next() )
			    	{
//			 	    	System.out.print( "Tuple " + (tupleCount++) + " : " );
				    	point = (STRUCT)rs.getObject(1);

						geom = sdoAdapter.importGeometry( point );
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							double X = point0.getX();
							double Y = point0.getY();
							g.setColor(Color.green);
							g.fillRect((int) X-5, (int) Y-5, 10, 10);
//							System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
						}
//			      		System.out.println();
			    	}
  	 			}catch (Exception e){
  	 				e.printStackTrace();
  	 			}
  	 		}
  	 	}
  	 	RangeStudentPanel panel = new RangeStudentPanel(q);
  	 	return panel;
  	}
  	
  	public JPanel RangeBuildingQuery(String q){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("RangeBuildingQuery");
  	 	
  	 	class RangeBuildingPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = 7882613223191930324L;
			private String query;
  	 		RangeBuildingPanel(String s){
  	 			this.setBounds(0, 0, 820, 580);
  	 			this.setOpaque(false);
  	 			query=s;
  	 		}
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			try{
  	 				Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					rs = stmt.executeQuery(query);
					@SuppressWarnings("deprecation")
  					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9", STRUCT.class, null, null, mainConnection);

  		  			g.setColor(Color.yellow);
  		  			
  		  			while (rs.next())
  		  			{
  		  				point = (STRUCT)rs.getObject(1);
  		  				geom = sdoAdapter.importGeometry(point);
  		  			
  		  				if(( geom instanceof oracle.sdoapi.geom.Polygon)) // Check whether the retrieved instance is polygon
  		  				{
  		  					oracle.sdoapi.geom.Polygon poly = (oracle.sdoapi.geom.Polygon) geom;

  		  					oracle.sdoapi.geom.CurveString cs = poly.getExteriorRing();
  		  					oracle.sdoapi.geom.LineString line =(oracle.sdoapi.geom.LineString)cs;
  		  					CoordPoint[] coordArray = line.getPointArray();
  		  					
  		  					int bx[]=new int[coordArray.length];
  		  					int by[]=new int[coordArray.length];

  		  					for(int i=0; i<coordArray.length;i++)
  		  					{
  		  						bx[i]=(int)coordArray[i].getX();
  		  						by[i]=(int)coordArray[i].getY();
  		  					}
  		  					g.drawPolygon(bx, by, coordArray.length);
  		  				} 
  		  			}
  	 			}catch(Exception e){
  	 				e.printStackTrace();
  	 			}
  	 		}
  	 	}
  	 		
  	 	RangeBuildingPanel panel = new RangeBuildingPanel(q);
  	 	return panel;
  	}
  	
  	public JPanel RangeAnnouncementQuery(String q){
 		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("RangeAnnouncementQuery");
  	 	
  	 	class RangeAnnouncementPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = -3167870695578038439L;
			private String query;
  	 		RangeAnnouncementPanel(String s){
  	 			this.setBounds(0, 0, 820, 580);
  	 			this.setOpaque(false);
  	 			query=s;
  	 		}
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			try{
  	 				Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					rs = stmt.executeQuery(query);
					@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			    	int X=0;
			    	int Y=0;
			 	    while( rs.next() )
			    	{
				    	point = (STRUCT)rs.getObject(1);

				    	g.setColor(Color.red);
						geom = sdoAdapter.importGeometry( point );
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
			      			geom = sdoAdapter.importGeometry( point );
	  		      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	  		      			{
	  							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
	  							X = (int)point0.getX();
	  							Y = (int)point0.getY();
	  							g.fillRect((int)X-7, (int)Y-7, 15, 15);
	  						}
	  		      			
	  		      			int radius = rs.getInt(2);
	  		      			double distance = rs.getDouble(3);
	  		      			if (radius>=distance){
	  		      				g.drawOval(X-radius, Y-radius, radius*2, radius*2);
	  		      			}
						}
			    	}
  	 			}catch(Exception e){
  	 				e.printStackTrace();
  	 			}
  	 		}
  	 	}
  	 	
  	 	RangeAnnouncementPanel panel = new RangeAnnouncementPanel(q);
  	 	return panel;
  	}
  	
  	public JPanel NearestAnnouncementQuery(int px, int py){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("NearestAnnouncementQuery");
  	 	
  	 	class NearestAnnouncementPanel extends JPanel{
			/**
			 * 
			 */
			private static final long serialVersionUID = -7825406171340570737L;
			private int x;
  	 		private int y;
  	 		NearestAnnouncementPanel(int a, int b){
  	 			this.setBounds(0, 0, 820, 580);
  				this.setOpaque(false);
  				x = a;
  				y = b;
  	 		}
  	 		
  	 		@Override
  			public void paintComponent(Graphics g){
  				super.paintComponent(g);
  				try {
			    	String query = "SELECT a.aslocation,a.radius FROM announcement a WHERE "
			  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
			    	Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					rs = stmt.executeQuery(query);
					
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					
				  	@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			 	    while( rs.next() )
			    	{
				    	point = (STRUCT)rs.getObject(1);
				    	int radius = rs.getInt(2);
				    	g.setColor(Color.white);

						geom = sdoAdapter.importGeometry( point );
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
//							double dist = (Math.sqrt((X-x)*(X-x) + (Y-y)*(Y-y)));
							g.fillRect(X-7,  Y-7, 15, 15);
							g.drawOval(X-radius, Y-radius, radius*2, radius*2);
//							System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
						}
			      		
//			      		System.out.println();
			       	}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println( " Error : " + e.toString() );
				}
  	 		}
  	 	}
  	 	NearestAnnouncementPanel panel = new NearestAnnouncementPanel(px, py);
  	 	return panel;
  	}
  	
  	public JPanel SurroundingStudentQuery(int px, int py){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("SurroundingStudentQuery");
  	 	
  	 	class SurroundingStudentPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = 4494378753234352620L;
			private int x;
  	 		private int y;
  	 		SurroundingStudentPanel(int a, int b){
  	 			this.setBounds(0,0,820,580);
  	 			this.setOpaque(false);
  	 			x=a;
  	 			y=b;
  	 		}
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			try{
  	 				String query1 = "SELECT a.aslocation.sdo_point.x,a.aslocation.sdo_point.y,a.radius FROM announcement a WHERE "
			  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
			    	Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					rs = stmt.executeQuery(query1);
					int ax=0;
					int ay=0;
					int aradius=0;
					while (rs.next()){
						ax=rs.getInt(1);
						ay=rs.getInt(2);
						aradius=rs.getInt(3);
					}
					String query2 = "SELECT s.plocation FROM student s WHERE SDO_INSIDE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),"
									+"SDO_ORDINATE_ARRAY("+ax+","+(ay-aradius)+","+(ax+aradius)+","+ay+","+ax+","+(ay+aradius)+")))='TRUE'";
//					System.out.println(query2);
					ResultSet rs1 = stmt.executeQuery(query2);
					
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
					
				  	@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

			 	    while( rs1.next() )
			    	{
				    	point = (STRUCT)rs.getObject(1);
						geom = sdoAdapter.importGeometry( point );
//						System.out.println(geom.toString());
			      		if ( (geom instanceof oracle.sdoapi.geom.Point) )
			      		{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
							g.setColor(Color.green);
							g.fillRect(X-5, Y-5, 10, 10);
			      		}
			    	}
  	 			}catch(Exception e){
  	 				e.printStackTrace();
  	 			}
  	 		}
  	 	}
  	 	SurroundingStudentPanel panel = new SurroundingStudentPanel(px, py);
  	 	return panel;
  	}
  	
  	public JPanel EmergencyQuery(int px, int py){
  		if (mainConnection==null){
  			System.out.println("Error: Connection Null");
  			return null;
  		}
  	 	System.out.println("EmergencyQuery");
  	 	
  	 	class EmergencyPanel extends JPanel{
  	 		/**
			 * 
			 */
			private static final long serialVersionUID = 6223702862088226219L;
			private int x;
  	 		private int y;
  	 		private ArrayList<int[]> as;
  	 		private ArrayList<int[]> stu;
  	 		EmergencyPanel(int a, int b){
  	 			this.setBounds(0, 0, 820, 580);
  	 			this.setOpaque(false);
  	 			x=a;
  	 			y=b;
  	 			as=new ArrayList<int[]>();
  	 			stu=new ArrayList<int[]>();
  	 		}
  	 		/*
  	 		 * (non-Javadoc)
  	 		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
  	 		 * 0:blue
  	 		 * 1:cyan
  	 		 * 2:orange
  	 		 * 3:pink
  	 		 * 4:green
  	 		 * 5:yellow
  	 		 * 6:red
  	 		 */
  	 		@Override
  	 		public void paintComponent(Graphics g){
  	 			super.paintComponent(g);
  	 			try{
  	 				String query1 = "SELECT a.aslocation.sdo_point.x,a.aslocation.sdo_point.y,a.radius FROM announcement a WHERE "
			  				+ "SDO_NN(a.aslocation, sdo_geometry(2001,NULL,sdo_point_type("+x+","+y+",NULL),NULL,NULL),'sdo_num_res=1',1) = 'TRUE'";
			    	Statement stmt=mainConnection.createStatement();
					ResultSet rs=null;
					rs = stmt.executeQuery(query1);
					int ax=0;
					int ay=0;
					int aradius=0;
					while (rs.next()){
						ax=rs.getInt(1);
						ay=rs.getInt(2);
						aradius=rs.getInt(3);
					}
					String query2 = "SELECT s.plocation FROM student s WHERE SDO_INSIDE(s.plocation, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),"
							+"SDO_ORDINATE_ARRAY("+ax+","+(ay-aradius)+","+(ax+aradius)+","+ay+","+ax+","+(ay+aradius)+")))='TRUE'";
//					System.out.println(query2);
					ResultSet rs1 = stmt.executeQuery(query2);
			
					STRUCT point;		//Structure to handle Geometry Objects
					Geometry geom;     	//Structure to handle Geometry Objects
			
					@SuppressWarnings("deprecation")
					GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

					while( rs1.next() )
					{
						point = (STRUCT)rs.getObject(1);
						geom = sdoAdapter.importGeometry( point );
//						System.out.println(geom.toString());
						if ( (geom instanceof oracle.sdoapi.geom.Point) )
						{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
							Statement stmt2=mainConnection.createStatement();
							String query3 = "SELECT a.aslocation.sdo_point.x,a.aslocation.sdo_point.y,a.radius,SDO_NN_DISTANCE(1) dist FROM announcement a WHERE SDO_NN(a.aslocation,SDO_GEOMETRY"+
											"(2001,NULL,sdo_point_type("+X+","+Y+",NULL),NULL,NULL),'sdo_num_res=4',1) = 'TRUE' ORDER BY dist ASC";
							ResultSet rs2 = stmt2.executeQuery(query3);
							double mindis=99999;
							int[] asinfo=new int[3];
							while (rs2.next()){
								int tempx = rs2.getInt(1);
								int tempy = rs2.getInt(2);
								int tempr = rs2.getInt(3);
								double dis = rs2.getDouble(4);
								if (tempx==ax && tempy==ay){
									continue;
								}
								if (dis-tempr < 0){
									mindis=0;
									asinfo[0] = tempx;
									asinfo[1] = tempy;
									asinfo[2] = tempr;
								}
								else if (dis-tempr < mindis){
									mindis=dis-tempr;
									asinfo[0] = tempx;
									asinfo[1] = tempy;
									asinfo[2] = tempr;
								}
							}
							boolean found=false;
							int indexa=0;
							for (int i=0; i < as.size(); i++){
								int []temp = as.get(i);
								if (temp[0]==asinfo[0] && temp[1]==asinfo[1]){
									found=true;
									indexa=i;
								}
							}
							if (found){
								int color = indexa;
								int []temp = new int[3];
								temp[0] = X;
								temp[1] = Y;
								temp[2] = color;
								stu.add(temp);
//								System.out.println("colorf:"+temp[2]);
							}
							else{
								int color = as.size();
								int []temp = new int[3];
								temp[0] = X;
								temp[1] = Y;
								temp[2] = color;
								stu.add(temp);
//								System.out.println("colorn:"+temp[2]);
								as.add(asinfo);
							}
//							System.out.println(as.size());
//							System.out.println(stu.size());
						}
					}
					for (int i=0; i<as.size(); i++){
						int []asinfo = as.get(i);
						if (i == 0){
							g.setColor(Color.blue);
						}
						if (i == 1){
							g.setColor(Color.cyan);
						}
						if (i == 2){
							g.setColor(Color.orange);
						}
						if (i == 3){
							g.setColor(Color.pink);
						}
						if (i == 4){
							g.setColor(Color.green);
						}
						if (i == 5){
							g.setColor(Color.yellow);
						}
						if (i == 6){
							g.setColor(Color.red);
						}
						g.fillRect(asinfo[0]-7, asinfo[1]-7, 15, 15);
						g.drawOval(asinfo[0]-asinfo[2], asinfo[1]-asinfo[2], asinfo[2]*2, asinfo[2]*2);
					}
					for (int j=0; j < stu.size(); j++){
						int []stuinfo=stu.get(j);
						int i=stuinfo[2];
						if (i == 0){
							g.setColor(Color.blue);
						}
						if (i == 1){
							g.setColor(Color.cyan);
						}
						if (i == 2){
							g.setColor(Color.orange);
						}
						if (i == 3){
							g.setColor(Color.pink);
						}
						if (i == 4){
							g.setColor(Color.green);
						}
						if (i == 5){
							g.setColor(Color.yellow);
						}
						if (i == 6){
							g.setColor(Color.red);
						}
						g.fillRect(stuinfo[0]-5, stuinfo[1]-5, 10, 10);
					}
  	 			}catch(Exception e){
  	 				e.printStackTrace();
  	 			}
  	 		}
  	 	}
  	 	EmergencyPanel panel = new EmergencyPanel(px, py);
  	 	return panel;
  	}
}
