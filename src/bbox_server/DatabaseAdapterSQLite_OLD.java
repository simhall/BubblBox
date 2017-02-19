package bbox_server;
/*package databaseAdapter;

import java.sql.*;
import java.util.*;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class DatabaseAdapterSQLite_OLD {

	public static String DBNAME = "config.db";
	private static volatile DatabaseAdapterSQLite_OLD instance;
	//private static Session neo4j;

	private DatabaseAdapterSQLite_OLD() { }


	public static DatabaseAdapterSQLite_OLD getInstance() {
		if (instance == null ) {
			synchronized (DatabaseAdapterSQLite_OLD.class) {
				if (instance == null) {
					instance = new DatabaseAdapterSQLite_OLD();
					//Neo4jSessionFactory neo4jfactory = new Neo4jSessionFactory();
					//neo4j = neo4jfactory.getNeo4jSession();
				}
			}
		}

		return instance;
	}

	/////////////////////////////////////////////////
	/// Get Objects
	/////////////////////////////////////////////////

	public ConfigurationArtifact getConfigurationArtifact(String artifactName)
	{
		// TODO: Make this method effective!
		for(ConfigurationArtifact a : this.getAllConfigurationArtifacts())
		{
			if(a.getArtifactName().equals(artifactName))
			{
				return a;
			}
		}
		// Nothing found. Return null!
		return null;
	}

	public Collection<ConfigurationArtifact> getAllConfigurationArtifacts()
	{
		//return neo4j.loadAll(ConfigurationArtifact.class);
		return new ArrayList<ConfigurationArtifact>();
	}

	/////////////////////////////////////////////////
	/// Create Objects
	/////////////////////////////////////////////////

	public int createConfigurationArtifact(ConfigurationArtifact newArtifact)
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DBNAME);
			c.setAutoCommit(false);
			String sql;
			stmt = c.createStatement();
			
			// See if artifact exists
			sql = "SELECT * FROM ARTIFACTS WHERE NAME = '" + newArtifact.getArtifactName() + "';";
			ResultSet rs = stmt.executeQuery( sql );
			
			// TODO: Make this cleaner
			int count = 0;
			while(rs.next()) { count++; }
			
			if(count > 0)
			{
				throw new Exception("Found duplicate entry");
			}
			
			
			sql = "INSERT INTO ARTIFACTS ("
					+ "NAME,"
					+ "SOURCECOMPID,"
					+ "ENVIRONMENT,"
					+ "ARTIFACTTYPE,"
					+ "TAGS,"
					+ "LASTUPDATE,"
					+ "CONTENTSPEC,"
					+ "DOCUMENTATION,"
					+ "DATA) " +
					"VALUES ("
					+ "'" + newArtifact.getArtifactName() + "'" + ", "
					+ newArtifact.getSourceComponentID() + ", "
					+ "'" + newArtifact.getEnvironment().getEnvironmentName() + "'" + ", "
					+ "'" + newArtifact.getArtifactType() + "'" + ", "
					+ "'" + newArtifact.getTags() + "'" + ", "
					+ "'" + newArtifact.getLastUpdate() + "'" + ", "
					+ "'" + newArtifact.getContentSpecification() + "'" + ", "
					+ "'" + newArtifact.getDocumentationURL() + "'" + ", "
					+ "'" + newArtifact.getArtifactDataJSON() + "'"
					+ ");"; 
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			
			// Get ID of new entry
			sql = "SELECT * FROM ARTIFACTS WHERE NAME = '" + newArtifact.getArtifactName() + "';";
			rs = stmt.executeQuery( sql );
			newArtifact.setArtifactID(rs.getInt("ID"));
			c.close();
			return rs.getInt("ID");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return -1;
	}

	public boolean createArtifactDependency()
	{
		return false;
	}

	/////////////////////////////////////////////////
	/// Delete Objects
	/////////////////////////////////////////////////

	public boolean deleteConfigurationArtifact(ConfigurationArtifact artifactDelete)
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DBNAME);
			c.setAutoCommit(false);
			String sql;
			stmt = c.createStatement();
			
			// See if artifact exists
			sql = "FROM FROM ARTIFACTS WHERE ID = " + artifactDelete.getArtifactID() + ";";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			
			return true;
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}

	public boolean deleteConfiguraitonDependency()
	{
		return false;
	}

	/////////////////////////////////////////////////
	/// Update Objects
	/////////////////////////////////////////////////


	public boolean updateConfigurationArtifact(ConfigurationArtifact updatedArtifact)
	{
		//if(getConfigurationArtifact(updatedArtifact.getArtifactName()) != null)
		//{
		//	Transaction tx = neo4j.beginTransaction();
		//	neo4j.save(updatedArtifact,1);
		//	tx.commit();
		//	tx.close();
		//	return true;
		//}
		return false;
	}

	public boolean initializeDB()
	{
		Connection c = null;
		Statement stmt = null;
		String sql;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DBNAME);
			c.setAutoCommit(false);

			stmt = c.createStatement();

			///////////////////////////////////////////
			// INITIALIZE ARTIFACTS TABLE
			///////////////////////////////////////////

			sql = "DROP TABLE IF EXISTS ARTIFACTS";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE ARTIFACTS " +
					"(ID 			INTEGER PRIMARY KEY AUTOINCREMENT ," +
					" NAME			TEXT		NOT NULL, " +
					" SOURCECOMPID	INT, " +
					" ENVIRONMENT   TEXT, " + 
					" ARTIFACTTYPE	TEXT		NOT NULL, " + 
					" TAGS         	TEXT, " +
					" LASTUPDATE	DATETIME	NOT NULL, " + 
					" CONTENTSPEC	TEXT		NOT NULL, " + 
					" DOCUMENTATION TEXT, " +
					" DATA			TEXT		NOT NULL)"; 
			stmt.executeUpdate(sql);

			///////////////////////////////////////////
			// DEPENDENCIES TABLE
			///////////////////////////////////////////

			sql = "DROP TABLE IF EXISTS DEPENDENCIES";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE DEPENDENCIES " +
					"(ID 			INTEGER PRIMARY KEY AUTOINCREMENT ," +
					" SOURCECOMP	INT     	NOT NULL," +
					" TARGETCOMP	INT     	NOT NULL," +
					" NAME			TEXT     	NOT NULL," +
					" TYPE			TEXT     	NOT NULL," +
					" SPECIFICATION	TEXT     	NOT NULL," +
					" SOURCEFIELD	TEXT," +
					" TARGETSFIELD	TEXT," +
					" OPERATOR		TEXT," +
					" LASTUPDATE	DATETIME	NOT NULL, " + 
					" DOCUMENTATION TEXT, " +
					" VERSION    	TEXT     	NOT NULL)"; 
			stmt.executeUpdate(sql);

			///////////////////////////////////////////
			// INITIALIZE SOURCE COMPONENT TABLE
			///////////////////////////////////////////

			sql = "DROP TABLE IF EXISTS SOURCECOMP";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE SOURCECOMP " +
					"(ID 			INTEGER PRIMARY KEY AUTOINCREMENT ," +
					" NAME			TEXT     	NOT NULL," +
					" VERSION    	TEXT)"; 
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
			return true;
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}
}
*/