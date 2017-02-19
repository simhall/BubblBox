package bbox_client;

import java.util.ArrayList;
import java.util.Collection;

import shared.BBoxDBSQLLite;
import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class BBoxClientEngine {

	private static BBoxClientEngine instance = null;
		
	public static Collection<ConfigurationArtifact> configurationArtifacts = new ArrayList<ConfigurationArtifact>();
	public static BBoxDBSQLLite LogDB = new BBoxDBSQLLite(true);
	
	protected BBoxClientEngine() {
		// Exists only to defeat instantiation.
	}

	public static BBoxClientEngine getInstance() {
		if (instance == null) {
			instance = new BBoxClientEngine();
		}
		return instance;
	}
	
	public Boolean Sync()
	{
		
		// Download new artifacts
		
		// See if there are updates in existing artifacts
		
		return false;
	}
	
	public Boolean AddArtifact()
	{
		return false;
	}	
}
