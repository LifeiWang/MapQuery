package csci585hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

//import oracle.sdoapi.OraSpatialManager;
//import oracle.sdoapi.geom.*;
//import oracle.sdoapi.adapter.*;
//import oracle.sdoapi.sref.*;

public class Populate {
	static String buildingFileName=null;
	static String studentFileName=null;
	static String announcementFileName=null;
	java.sql.Connection mainConnection = null;

	public static void main(String args[]){
		String studentfn = null;
		String announcementfn = null;
		String buildingfn = null;
		if (args.length < 3){
			System.out.println(args.length);
			System.out.println("Not enough arguments");
			return;
		}
		
		for (int i=0;i<args.length;i++)
	    {
			String fname=args[i];
	        if (fname.equals("students.xy")){ 
	        	studentfn=args[i];
	        }
	        else  if (fname.equals("buildings.xy")){    
	        	buildingfn=args[i];
	        }
	        else  if (fname.equals("announcementSystems.xy")){    
	        	announcementfn=args[i];
	        }
	    }
		Populate p = new Populate();
		p.setStudentFile(studentfn);
		p.setAnnouncementFile(announcementfn);
		p.setBuildingFile(buildingfn);
		p.insertData();
	}
	
	public Populate()
	{
//		buildingFileName="buildings.xy";
//		announcementFileName="announcementSystems.xy";
//		studentFileName="students.xy";
	}
	
	public void setStudentFile(String name){
		studentFileName=name;
	}
	
	public void setBuildingFile(String name){
		buildingFileName=name;
	}
	
	public void setAnnouncementFile(String name){
		announcementFileName=name;
	}
	
	public void insertData(){
		ConnectToDB();
		clearTable();
		readStudentFile(studentFileName);
		readBuildingFile(buildingFileName);
		readAnnouncementFile(announcementFileName);
		CloseConnection();
		System.out.println("DONE");
	}
	
	public void clearTable()
	{
		if (mainConnection==null){
        	System.out.println("Connection nil");
        	return;
        }
		
		//delete table
    	Statement stmt1;
    	Statement stmt2;
    	Statement stmt3;
		try {
			stmt1 = mainConnection.createStatement();
			stmt1.executeUpdate("truncate table student");
	    	stmt2=mainConnection.createStatement();
	    	stmt2.executeUpdate("truncate table announcement");
	    	stmt3=mainConnection.createStatement();
	    	stmt3.executeUpdate("truncate table building");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

	}
	
	public void ConnectToDB()
 	{
		try
		{
			// loading Oracle Driver
    		System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
	    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	    	System.out.println(", Loaded.");
	    	//submit url
	    	String URL = "jdbc:oracle:thin:@localhost:1521:hw2";
	    	String userName = "system";
	    	String password = "hw2";
            //myURL
//			String URL = "jdbc:oracle:thin:@localhost:1521:hw1csci585";
//	    	String userName = "SYSTEM";
//	    	String password = "123456";

	    	System.out.print("Connecting to DB...");
	    	mainConnection = DriverManager.getConnection(URL, userName, password);
	    	System.out.println(", Connected!");
	    	
//    		mainStatement = mainConnection.createStatement();

   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
 	}
	
	 private void CloseConnection()
	   {
		try
			{
			mainConnection.close();
			}
		catch (SQLException e)
			{
			System.err.println("Cannot close connection: " + e.getMessage());
			}
		}
	
	 public void readStudentFile(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("Read Students");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;

	            PreparedStatement pstmt=null;
	            int line = 1;
	            
	            if (mainConnection==null){
	            	System.out.println("Connection nil");
	            	return;
	            }
	            
	            while ((tempString = reader.readLine()) != null){
	            	String [] values=tempString.split(",\\s");
	                System.out.println("line " + line + ":" + values[0]);
	                	              	                	                
	                pstmt=mainConnection.prepareStatement("Insert into student values(?,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(?,?,NULL), NULL , NULL))");
	                pstmt.setString(1, values[0]);
	                pstmt.setInt(2, Integer.parseInt(values[1]));
	                pstmt.setInt(3, Integer.parseInt(values[2]));
	                pstmt.executeUpdate();
	            
	                line++;
	            }
	            reader.close();
	        } catch (IOException| SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	 
	 
	 public void readAnnouncementFile(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("Read Announcement");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            
	            PreparedStatement pstmt=null;
	            
	            int line = 1;
	            
	            if (mainConnection==null){
	            	System.out.println("Connection nil");
	            	return;
	            }
	            
	            while ((tempString = reader.readLine()) != null){
	            	String [] values=tempString.split(",\\s");
	                System.out.println("line " + line + ":" + values[0]);
	                
	                pstmt=mainConnection.prepareStatement("Insert into announcement values(?,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(?,?,NULL), NULL , NULL),?)");
	                pstmt.setString(1, values[0]);
	                pstmt.setInt(2, Integer.parseInt(values[1]));
	                pstmt.setInt(3, Integer.parseInt(values[2]));
	                pstmt.setInt(4, Integer.parseInt(values[3]));
	                pstmt.executeUpdate();
	                
	                line++;
	            }
	            reader.close();
	        } catch (IOException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	 
	 public void readBuildingFile(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("Read Building");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            Statement insertBuildingStmt=null;
	            String stmt = null;
	            int line = 1;
	            
	            if (mainConnection==null){
	            	System.out.println("Connection nil");
	            	return;
	            }
	            insertBuildingStmt = mainConnection.createStatement();
	            
	            while ((tempString = reader.readLine()) != null){
	            	
	            	String [] values=tempString.split(",\\s");
	            	
	            	stmt="insert into building values('"+values[0]+"','"+values[1]+"',SDO_GEOMETRY( 2003, NULL, NULL, MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,1),";
	            	stmt+="MDSYS.SDO_ORDINATE_ARRAY(";
	            	
	            	String polygon=values[2];
	            	int polyNum = Integer.parseInt(polygon);
	            	int indexNum = polyNum * 2;
	            	if (values.length<=indexNum+2){
	            		System.out.println("Invalid input file in Line:" + line);
	            		return;
	            	}
	            	System.out.println(line + ":");
	            	for (int i=3; i<indexNum+2; i++){
	            		stmt+=values[i];
	            		stmt+=",";
	            	}
	            	stmt+=values[indexNum+2];
	            	stmt+=")))";
	            	System.out.println(stmt);
	            	
	            	insertBuildingStmt.executeUpdate(stmt);
	            	line++;
	            }
	            reader.close();
	        } catch (IOException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
}
