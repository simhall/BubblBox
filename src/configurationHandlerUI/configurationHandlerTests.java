package configurationHandlerUI;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class configurationHandlerTests {

	@Test
	public void sampleDataFilesExist() {
		// 
		Collection<ConfigurationArtifact> sampleArtifacts = SampleDataLoader.getSampleData();
		if(sampleArtifacts.size() <= 0)
		{
			fail("No sample artifacts loaded.");
		}
	}

}
