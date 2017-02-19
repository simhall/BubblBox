package shared.ConfigurationArtifacts;

import java.util.Date;

import org.neo4j.ogm.annotation.*;

import shared.JsonEntity;

@RelationshipEntity(type = "DEPENDENCY")
public class ArtifactDependency extends JsonEntity {
	
		@GraphId
        private Long id;
		private String dependencyName = "DefaultName";
		private Date lastUpdated;
		

        @StartNode
        private ConfigurationArtifact fromArtifact;

        @EndNode
        private ConfigurationArtifact toArtifact;
        
    	//================================================================================
        // Constructors
        //================================================================================
        
        public ArtifactDependency(){}
        
        public ArtifactDependency(String dependencyName, ConfigurationArtifact fromArtifact, ConfigurationArtifact toArtifact)
        {
        	this.dependencyName = dependencyName;
        	this.fromArtifact = fromArtifact;
        	this.toArtifact = toArtifact;
        	
        	Date currentDate = new Date();
        	this.lastUpdated = currentDate;        
        }
        
        //================================================================================
        // Getters
        //================================================================================
        
        public long getDependencyID() { return id; }
    	public String getDependencyName() { return dependencyName; }
    	public ConfigurationArtifact getFromArtifact() { return fromArtifact; }
    	public ConfigurationArtifact getToArtifact() { return toArtifact; }
    	public Date getLastUpdatedDate() { return lastUpdated; }
    	
    	//================================================================================
        // Setters
        //================================================================================
    	
    	public void setDependencyID(long id) { this.id = id; }
    	public void setDependencyName(String dependendencyName) { this.dependencyName = dependendencyName; }
    	public void setFromArtifact(ConfigurationArtifact fromArtifact) { this.fromArtifact = fromArtifact; }
    	public void setToArtifact(ConfigurationArtifact toArtifact) { this.toArtifact = toArtifact; }
    	
    	public void setLastUpdatedDate() 
    	{ 
    		Date currentDate = new Date();
    		this.lastUpdated = currentDate; 
    		
    	}
        

}

