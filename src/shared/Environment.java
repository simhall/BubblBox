package shared;

/*public class Environment {
	
	private String environmentName = "";
	
	public Environment(String environmentName)
	{
		this.environmentName = environmentName;
	}
	
	public String getEnvironmentName() { return environmentName; }
	public void setEnvironmentName(String environmentName) { this.environmentName = environmentName; } 
}*/

// TODO: Create own class for Environment and save all environments to DB where they can be modified
public enum Environment
{
	UNDEFINED, DEV, TEST, UAT, PROD
};
