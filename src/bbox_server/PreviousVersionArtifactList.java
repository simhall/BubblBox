package bbox_server;

import java.util.ArrayList;
import java.util.List;

import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.PreviousVersionArtifact;

public class PreviousVersionArtifactList {
	//private final long id;
    //private final String content;
	private ArrayList<PreviousVersionArtifact> artifacts;
	
    public PreviousVersionArtifactList() {
        this.artifacts = new ArrayList<PreviousVersionArtifact>();
    }
    
    public List<PreviousVersionArtifact> getPreviousVersions() {
        return artifacts;
    }

}
