package bbox_client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.util.Assert;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class BBoxClientTest {

	@Test
	public void GetArtifactTest() {
		if(BBoxClientEngine.getInstance().ServerConnectionHealthy())
		{
			List<ConfigurationArtifact> artifacts = BBoxClientEngine.getInstance().GetArtifacts();
			Assert.isTrue(artifacts != null);
			Assert.isTrue(artifacts.size() > 0);
		}
	}

}
