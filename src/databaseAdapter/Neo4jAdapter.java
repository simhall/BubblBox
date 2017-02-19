package databaseAdapter;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.transaction.Transaction;

import shared.AuditLog;
import shared.Neo4jSessionFactory;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.PreviousVersionArtifact;

public class Neo4jAdapter {

	private static volatile Neo4jAdapter instance;
	private static Session neo4j;
	
	private Neo4jAdapter() { }
	
	
	public static Neo4jAdapter getInstance() {
		if (instance == null ) {
			synchronized (Neo4jAdapter.class) {
				if (instance == null) {
					instance = new Neo4jAdapter();
					Neo4jSessionFactory neo4jfactory = new Neo4jSessionFactory();
					neo4j = neo4jfactory.getNeo4jSession();
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
		for(ConfigurationArtifact a : Neo4jAdapter.getInstance().getAllConfigurationArtifacts())
		{
			if(a.getArtifactName().equals(artifactName))
			{
				return a;
			}
		}
		// Nothing found. Return null!
		return null;
	}
	
	public ConfigurationArtifact getConfigurationArtifact(long artifactId)
	{
		// TODO: Make this method effective!
		for(ConfigurationArtifact a : Neo4jAdapter.getInstance().getAllConfigurationArtifacts())
		{
			if(a != null)
			{
				System.err.println("Trying artifact: " + a.getArtifactID());
				if(a.getArtifactID() == artifactId)
				{
					return a;
				}
			}
			else
			{
				System.err.println("Found artifact with null id");

			}
		}
		// Nothing found. Return null!
		return null;
	}
	
	public Collection<ConfigurationArtifact> getAllConfigurationArtifacts()
	{
		return neo4j.loadAll(ConfigurationArtifact.class);
	}
	
	public Collection<PreviousVersionArtifact> getAllPreviousVersionArtifacts()
	{
		return neo4j.loadAll(PreviousVersionArtifact.class);
	}
	
	public Collection<AuditLog> getAllAuditLogs()
	{
		Collection<AuditLog> logs = neo4j.loadAll(AuditLog.class);
		if(logs != null)
		{
			return logs;
		}
		else
		{
			return new ArrayList<AuditLog>();
		}
	}
	
	/////////////////////////////////////////////////
	/// Create Objects
	/////////////////////////////////////////////////
	
	public boolean createConfigurationArtifact(ConfigurationArtifact newArtifact)
	{
		try
		{
			Transaction tx = neo4j.beginTransaction();
			neo4j.save(newArtifact);
			tx.commit();
			tx.close();
			return true;
		}
		catch(Exception e1)
		{
			return false;
		}
	}
	
	public boolean createArtifactDependency(ConfigurationArtifact updatedArtifact)
	{
		try
		{
			Transaction tx = neo4j.beginTransaction();
			neo4j.save(updatedArtifact);
			tx.commit();
			tx.close();
			return true;
		}
		catch(Exception e1)
		{
			return false;
		}
	}
	
	/////////////////////////////////////////////////
	/// Delete Objects
	/////////////////////////////////////////////////
	
	public boolean deleteConfigurationArtifact(ConfigurationArtifact artifactDelete)
	{
		try
		{
			Transaction tx = neo4j.beginTransaction();
			neo4j.delete(artifactDelete);
			tx.commit();
			tx.close();
			return true;
		}
		catch(Exception e1)
		{
			return false;
		}
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
		// See if artifact does not exist.
		
		// Note: For some reason this modifies the configuration artifact.
		//if(getConfigurationArtifact(updatedArtifact.getArtifactName()) == null)
		//{
		//	return false;
		//}
		
		try
		{
			Transaction tx = neo4j.beginTransaction();
			neo4j.save(updatedArtifact);
			tx.commit();
			tx.close();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong when updating the config artifact");
			return false;
		}		
	}
	
	public boolean insertAuditLog(AuditLog auditLog)
	{		
		try
		{
			System.err.println("Inserting audit log");
			Transaction tx = neo4j.beginTransaction();
			neo4j.save(auditLog);
			tx.commit();
			tx.close();
			System.err.println("Audit log inserted");
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong when inserting the audit log");
			return false;
		}		
	}
}
