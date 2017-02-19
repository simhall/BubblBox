package bbox_server;

import java.util.ArrayList;
import java.util.List;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class ConfigurationArtifactList {
	//private final long id;
    //private final String content;
	private ArrayList<ConfigurationArtifact> artifacts;
	
    public ConfigurationArtifactList() {
        this.artifacts = new ArrayList<ConfigurationArtifact>();
    }
    
    public List<ConfigurationArtifact> getGreetings() {
        return artifacts;
    }

}
