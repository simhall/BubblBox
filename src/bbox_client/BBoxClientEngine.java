package bbox_client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import shared.ConfigurationArtifacts.ArtifactType;
import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class BBoxClientEngine {

	private static BBoxClientEngine instance = null;

	public static Collection<ConfigurationArtifact> configurationArtifacts = new ArrayList<ConfigurationArtifact>();
	public static BBoxDBSQLLite LogDB = new BBoxDBSQLLite(true);
	public static BBoxClientConfig Config = new BBoxClientConfig();
	private List<ConfigurationArtifact> serverArtifacts = new ArrayList<ConfigurationArtifact>();

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
		serverArtifacts = GetArtifacts();
		
		// See if there are updates in existing artifacts
		for(ConfigurationArtifact artifact: serverArtifacts)
		{
			String contentPath = artifact.getContentPath();
			if(artifact.getArtifactType() == ArtifactType.DELETED)
			{
				// Is it really deleted?
				File f = new File(contentPath);
				if(f.exists()) { 
				    // artifact exists. Delete file or change status to undefined?
					artifact.setArtifactType(ArtifactType.UNDEFINED);
					UpdateArtifact(artifact);
				}
			}
			else if(artifact.getArtifactType() == ArtifactType.IGNORED)
			{
				// Do nothing
			}
			else
			{
				// Does file exist? 
				File f = new File(contentPath);
				if(f.exists()) { 
					// If it exists. Has the content changed?
					Path path = Paths.get(contentPath);
					try {
						String currentContent = new String(Files.readAllBytes(path));
						String serverContent = artifact.getArtifactDataText();
						if(currentContent.equals(serverContent))
						{
							// Content is the same, no need to update
						}
						else
						{
							// Content changed -> Update server
							artifact.setArtifactDataText(currentContent);
							artifact.setLastUpdate(new Date());
							UpdateArtifact(artifact);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					// If not exists -> Change status to deleted
					artifact.setArtifactType(ArtifactType.DELETED);
					UpdateArtifact(artifact);
				}
			}
		}
		
		return true;
	}

	public Boolean UpdateArtifact(ConfigurationArtifact artifact)
	{
		System.out.println("Updating artifact: " + artifact.getArtifactName());
		System.out.println("Artifact Type: " + artifact.getArtifactType());
		try {
			String restURL = Config.getApiBase() + "/SetArtifactData";
			System.out.println("Calling: " + restURL);
			ClientResource resource = new ClientResource(restURL);

			Representation response = resource.post(artifact);
			String responseText = response.getText();
			System.out.println("Server Response: " + responseText);
			if (responseText != null && !responseText.toLowerCase().contains("false")) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
