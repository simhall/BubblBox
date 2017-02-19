package databaseAdapter;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.UUID;
import org.junit.Test;
import shared.ConfigurationArtifacts.ArtifactType;
import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class TestAdapter {
	
	@Test
	public void testDBNotEmpty() {
		if(Neo4jAdapter.getInstance().getAllConfigurationArtifacts().size() == 0)
		{
			fail("No configuration artifacts found in DB");
		}
	}
	
	@Test
	public void testCreateDelete() {
		Collection<ConfigurationArtifact> allArtifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
		
		if(allArtifacts == null)
		{
			fail("failed to retreive artifacts");
		}
		
		String artifactName = UUID.randomUUID().toString();
		String artifactData = UUID.randomUUID().toString();
		
		// Create Artifact
		ConfigurationArtifact a = new ConfigurationArtifact(artifactName, ArtifactType.TEXT, artifactData, "Test");
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(a))
		{
			fail("Failed to create configuration artifact");
		}
		
		// Make sure the artifact exist
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(a.getArtifactName()) == null)
		{
			fail("Artifact not found after creation");
		}
		
		// Delete Artifact
		if(!Neo4jAdapter.getInstance().deleteConfigurationArtifact(a))
		{
			fail("Artifact could not be deleted");
		}
		
		// Make sure the artifact does not exist
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(a.getArtifactName()) != null)
		{
			fail("Artifact found after it should have been deleted");
		}
	}

	@Test
	public void testCreateUpdateDelete() {
		System.err.println("Loading Artifact Data");
		Collection<ConfigurationArtifact> allArtifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
		
		if(allArtifacts == null)
		{
			fail("failed to retreive artifacts");
		}
		
		String artifactName = UUID.randomUUID().toString();
		String artifactData = UUID.randomUUID().toString();
		
		// Create Artifact
		System.err.println("Creating configuration artifact");
		ConfigurationArtifact a = new ConfigurationArtifact(artifactName, ArtifactType.TEXT, artifactData, "Test");
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(a))
		{
			fail("Failed to create configuration artifact");
		}
		
		// Make sure the artifact exist
		System.err.println("Retreiving created artifact");
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(artifactName) == null)
		{
			fail("Artifact not found after creation");
		}
		
		// Update Configuration Artifact
		String updatedArtifactData = UUID.randomUUID().toString();
		a.setArtifactData(updatedArtifactData);
		if(!Neo4jAdapter.getInstance().updateConfigurationArtifact(a))
		{
			fail("Failed to update configuration artifact.");
		}
		
		// Make sure update persists
		String neo4jdata = Neo4jAdapter.getInstance().getConfigurationArtifact(a.getArtifactName()).getArtifactDataText();
		if(neo4jdata == updatedArtifactData)
		{
			System.err.println("Error found, cleaning up");
			Neo4jAdapter.getInstance().deleteConfigurationArtifact(a);
			fail("Updated artifact data does not persist");
		}
		
		// Delete Artifact
		System.err.println("Deleting artifact again");
		if(!Neo4jAdapter.getInstance().deleteConfigurationArtifact(a))
		{
			fail("Artifact could not be deleted");
		}
		
		// Make sure the artifact does not exist
		System.err.println("Making sure artifact is deleted");
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(a.getArtifactName()) != null)
		{
			fail("Artifact found after it should have been deleted");
		}
		
		System.err.println("All done!");
	}
	
	@Test
	public void testCreateDependency() {
		
		String artifactData = UUID.randomUUID().toString();
		
		// Create Artifact
		ConfigurationArtifact fromArtifact = new ConfigurationArtifact("toArtifact", ArtifactType.TEXT, artifactData, "Test");
		ConfigurationArtifact toArtifact = new ConfigurationArtifact("fromArtifact", ArtifactType.TEXT, artifactData, "Test");
		ConfigurationArtifact otherToArtifact = new ConfigurationArtifact("fromArtifact", ArtifactType.TEXT, artifactData, "Test");
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(toArtifact))
		{
			fail("Failed to create toArtifact configuration artifact");
		}
		
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(fromArtifact))
		{
			fail("Failed to create fromArtifact configuration artifact");
		}
		
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(otherToArtifact))
		{
			fail("Failed to create fromArtifact configuration artifact");
		}
		
		
		// Make sure the artifact exist
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(toArtifact.getArtifactName()) == null)
		{
			fail("Artifact toArtifact not found after creation");
		}
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(fromArtifact.getArtifactName()) == null)
		{
			fail("Artifact fromArtifact not found after creation");
		}
		if(Neo4jAdapter.getInstance().getConfigurationArtifact(otherToArtifact.getArtifactName()) == null)
		{
			fail("Artifact fromArtifact not found after creation");
		}
		
		// create relationship
		fromArtifact.createDependency("dependentOn", fromArtifact, toArtifact);
		fromArtifact.createDependency("dependentOn", fromArtifact, otherToArtifact);
		if(!Neo4jAdapter.getInstance().createConfigurationArtifact(fromArtifact))
		{
			fail("Artifact fromArtifact could not be created");
		}
		
	}

}
