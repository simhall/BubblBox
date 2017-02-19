package shared;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import bbox_client.BBoxClientConfig;
import bbox_server.BBoxServerConfig;

public class BBoxDBSQLLite {

	public static String DBFile = "";
	
	public BBoxDBSQLLite(Boolean useClientConfig)
	{
		if(useClientConfig)
		{
			DBFile = BBoxClientConfig.BuBBlBoxDirectory + "timeydb_sql.db"; 
		}
		else
		{
			// Use server config
			DBFile = BBoxServerConfig.BuBBlBoxDirectory + "timeydb_sql.db"; 
		}
	}
	public boolean Initialize()
	{
		new File(BBoxClientConfig.BuBBlBoxDirectory).mkdirs();
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBFile);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      //public String LogID;
	  	  //public String WorkItem;
	  	  //public long TimeDifference;
	  	  //public String Timestamp;
	  	  //public boolean IsSynced;

	      
	      String sql = "CREATE TABLE IF NOT EXISTS Logs " +
                  "(LogID          INTEGER  PRIMARY KEY AUTOINCREMENT," +
                  " LogLevel       TEXT     NOT NULL, " + 
                  " LogMessage        TEXT     NOT NULL, " + 
                  " Timestamp      DATETIME NOT NULL " + 
                  ")"; 

	      stmt.executeUpdate(sql);
     
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	    return true;
	}
	
	public List<LogEntry> GetLogEntries(String filter) {
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBFile);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Logs " + filter + ";" );
	      while ( rs.next() ) {
	         // Parse SQL Results
	    	 int logID = rs.getInt("LogID");
	         String  logLevel = rs.getString("LogLevel");
	         String  logMessage = rs.getString("LogMessage");
	         DateTime  timestamp = new DateTime(rs.getTimestamp("Timestamp"));
	         // Create TimeLog item
	         LogEntry logEntry = new LogEntry(logID, logLevel, logMessage, timestamp);
	         logEntries.add(logEntry);
	         System.out.println( "LogID = " + logEntry.LogID );
	         System.out.println( "LogLevel = " + logEntry.LogLevel );
	         System.out.println( "LogLevel = " + logEntry.LogLevel );
	         System.out.println( "Timestamp = " + logEntry.Timestamp );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return logEntries;
	    }
	    System.out.println("Operation done successfully");
	    return logEntries;
	}

	public boolean AddLogEntry(LogEntry entry) {
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBFile);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      Timestamp timeStamp = new Timestamp(entry.Timestamp.getMillis());
	      	      
	      String sql = "INSERT INTO Logs (LogLevel,LogMessage,Timestamp) " +
	                   "VALUES (" + 
	                   		"'" + entry.LogLevel + "'" + ", " +
	                   		"'" + entry.LogMessage + "'" + ", " +
	                   		"'" + timeStamp.toString() + "'" +
	                   		");"; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return false;
	    }
	    System.out.println("Records created successfully");
	    return true;
	}
}
