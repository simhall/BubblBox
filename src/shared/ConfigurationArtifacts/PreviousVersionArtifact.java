package shared.ConfigurationArtifacts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import shared.Environment;

@NodeEntity
public class PreviousVersionArtifact {

	/**
	 * 
	 */
	private static final long serialVersionUID = 646311151269546055L;
	
	@GraphId
	protected Long id;
	
	private Long originalArtifactId;
	
	public PreviousVersionArtifact() {}
	
	public PreviousVersionArtifact(ConfigurationArtifact artifact)
	{
		this.setArtifactData(artifact.getArtifactData());
		this.setArtifactEnvironment(artifact.getEnvironment());
		this.setArtifactName(artifact.getArtifactName());
		this.setArtifactType(artifact.getArtifactType());
		this.setContentPath(artifact.getContentPath());
		this.setContentSpecification(artifact.getContentSpecification());
		this.setDocumentationURL(artifact.getDocumentationURL());
		this.setSourceComponentName(artifact.getSourceComponentName());
		this.setUpdatedBy(artifact.getUpdatedBy());
		this.setOriginalArtifactId(artifact.getArtifactID());
	}
	
	public long getOriginalArtifactID() { return this.originalArtifactId.longValue(); }
	public long getArtifactID() { return this.id.longValue(); }
	
	public void setOriginalArtifactId(long id) { this.originalArtifactId = Long.valueOf(id); }
	
	private String name = "DefaultName";
	
    @Relationship
    private Set<ArtifactDependency> dependencies = new HashSet<ArtifactDependency>();
    @JsonIgnore
    private Collection<PreviousVersionArtifact> previousVersions = new ArrayList<PreviousVersionArtifact>();
    private String sourceComponentName = "Unknown";
    private Environment environment = Environment.PROD; //new Environment("Default Environment");
    private ArtifactType artifactType = ArtifactType.UNDEFINED;
    private List<String> tags = new ArrayList<String>();
    private Date lastUpdate = new Date();
    private Collection<ContentSpecification> contentSpecification = new ArrayList<ContentSpecification>();
    private String documentationURL;
    private Object artifactData = null;
    private String contentPath = null;
    private String updatedBy = "";
    
	//================================================================================
    // UGLY HACK BELOW TO SEPARATE OLD/CURRENT ARTIFACTS IN NEO4J
    //================================================================================

	
	//================================================================================
    // Custom Methods
    //================================================================================
	
	public ArtifactDependency createDependency(String dependencyName, ConfigurationArtifact fromArtifact, ConfigurationArtifact toArtifact)
    {
    	ArtifactDependency artifactDependency = new ArtifactDependency(dependencyName, fromArtifact, toArtifact);
    	dependencies.add(artifactDependency);
    	return artifactDependency;
    }
	
	public boolean deleteSpecificationWithPath(String path)
	{
		for(ContentSpecification spec: contentSpecification)
		{
			if(spec.getXPath() == path)
			{
				contentSpecification.remove(spec);
				return true;
			}
		}
		return false;
	}
	
	//================================================================================
    // Override Methods
    //================================================================================
	
	public String toString()
	{
		return this.name;
	}
	
	//================================================================================
    // Getters
    //================================================================================
	
	public String getArtifactName() { return name; }
	public String getSourceComponentName() { return sourceComponentName; }
	public Environment getEnvironment() { return environment; }
	public ArtifactType getArtifactType() { return artifactType; }
	public List<String> getTags() { return tags; }
	public Date getLastUpdate() { return lastUpdate; }
	@JsonIgnore
	public Collection<ContentSpecification> getContentSpecification() { return contentSpecification; }
	@JsonIgnore
	public Collection<PreviousVersionArtifact> getPreviousVersions()
	{
		if(previousVersions == null) previousVersions = new ArrayList<PreviousVersionArtifact>();
		return previousVersions;
	}
	//public ContentSpecification getContentSpecification() { return contentSpecification; }
	public String getDocumentationURL() { return documentationURL; }
	public String getContentPath() { return this.contentPath; }
	@JsonIgnore
	public Object getArtifactData() { return artifactData; }
	public String getUpdatedBy() { return updatedBy; }
	
	// Custom getters
	
	public String getArtifactDataText() {
		switch(this.artifactType) {
			case TEXT: return (String)this.artifactData;
			default: return (String)this.artifactData;
		}
	}
	
	//================================================================================
    // Setters
    //================================================================================
	
	public void setArtifactID(long artifactId) { this.id = Long.valueOf(artifactId); }
	public void setArtifactName(String artifactName) { this.name = artifactName; }
	public void setArtifactType(ArtifactType artifactType) { this.artifactType = artifactType; }
	public void setArtifactData(Object newArtifactData) { this.artifactData = newArtifactData; }
	public void setArtifactDataText(String newArtifactData) { this.artifactData = newArtifactData; }
	public void setArtifactEnvironment(Environment newEnvironment) { this.environment = newEnvironment; }
	public void setContentPath(String newContentPath) { this.contentPath = newContentPath; }
	public void setDocumentationURL(String documentationURL) { this.documentationURL = documentationURL; }
	public void setSourceComponentName(String sourceComponentName) { this.sourceComponentName = sourceComponentName; }
	@JsonIgnore
	public void setPreviousVersions(Collection<PreviousVersionArtifact> previousVersions2) { this.previousVersions = previousVersions2; }
	public void setContentSpecification(Collection<ContentSpecification> contentSpecification) { this.contentSpecification = contentSpecification; }
	//public void setContentSpecification(ContentSpecification contentSpecification) { this.contentSpecification = contentSpecification; }
	public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

}
