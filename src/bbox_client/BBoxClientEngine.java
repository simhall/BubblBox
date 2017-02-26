package bbox_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import shared.BBoxDBSQLLite;
import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class BBoxClientEngine {

	private static BBoxClientEngine instance = null;

	public static Collection<ConfigurationArtifact> configurationArtifacts = new ArrayList<ConfigurationArtifact>();
	public static BBoxDBSQLLite LogDB = new BBoxDBSQLLite(true);
	public static BBoxClientConfig Config = new BBoxClientConfig();

	protected BBoxClientEngine() {
		// Exists only to defeat instantiation.
	}

	public static BBoxClientEngine getInstance() {
		if (instance == null) {
			instance = new BBoxClientEngine();
		}
		return instance;
	}

	public Boolean ServerConnectionHealthy()
	{
		try {
			String restURL = Config.getApiBase() + "/IsHealthy";
			System.out.println("Calling: " + restURL);
			ClientResource resource = new ClientResource(restURL);

			Representation response = resource.get();
			String responseText = response.getText();
			if (responseText != null && !responseText.equals("")) {
				System.out.println("Success! " + responseText);
					
				if(responseText.contains("true"))
				{
					return true;
				}
				else
				{
					return false;
				}

			} else {
				System.out.println(responseText);
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public Boolean Sync()
	{
		// Download new artifacts
		GetArtifacts();
		// See if there are updates in existing artifacts

		return false;
	}

	public Boolean AddArtifact()
	{
		return false;
	}
	
	public List<ConfigurationArtifact> GetArtifacts()
	{
		List<ConfigurationArtifact> ret = new ArrayList<ConfigurationArtifact>();
		try {
			String restURL = Config.getApiBase() + "/GetAllArtifacts";
			System.out.println("Calling: " + restURL);
			ClientResource resource = new ClientResource(restURL);

			Representation response = resource.get();
			String responseText = response.getText();
			
			if (responseText != null && !responseText.equals("")) {
				JSONObject jsonObject = new JSONObject(responseText);
				String artifactText = jsonObject.get("greetings").toString();
				ObjectMapper mapper = new ObjectMapper();
				ConfigurationArtifact[] artifacts = mapper.readValue(artifactText, ConfigurationArtifact[].class);
				for(ConfigurationArtifact artifact : artifacts)
				{
					ret.add(artifact);
				}
				return ret;
			} else {
				System.out.println(responseText);
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
