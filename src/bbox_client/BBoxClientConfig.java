package bbox_client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import shared.BBoxLog;

public class BBoxClientConfig {
	
	public static String BuBBlBoxDirectory = System.getenv("APPDATA") + "\\BuBBlBox\\Client\\";
	public static String configurationFilePath = BuBBlBoxDirectory + "clientconfiguration.config";
	
	public BBoxClientConfig()
	{
		File f = new File(configurationFilePath);
		if(f.exists() && !f.isDirectory()) { 
			
		}
		else
		{
			PrintWriter out = null;
			try {
				out = new PrintWriter(configurationFilePath);
				out.println("version=1.0");
				out.println("showNotifications=true");
				out.println("syncDelay=30");
				out.println("apiBase=localhost");
				out.println("username=xxx");
				out.println("password=xxx");
			} catch (FileNotFoundException e) {
				BBoxLog.LogException("Failed to instansiate TimeyConfig", e);
			} finally
			{
				out.close();
			}
			
		}
	}
	String result = "";
	InputStream inputStream;
	FileOutputStream out;
	
	
	public String getVersion() throws IOException {
		return getProperty("version");
	}
	
	public String getShowNotifications() throws IOException {
		return getProperty("showNotifications");
	}
	
	public int getSyncDelay() {
		try {
			return Integer.parseInt(getProperty("syncDelay"));
		} catch (NumberFormatException e) {
			BBoxLog.LogException("TimeyConfig: failed to get syncDelay", e);
			return 30;
		} catch (IOException e) {
			BBoxLog.LogException("TimeyConfig: failed to get syncDelay", e);
			return 30;
		}
	}
	
	
	
	public String getApiBase() throws IOException {
		return getProperty("apibase");
	}
	
	public String getUsername() throws IOException {
		return getProperty("username");
	}
	
	public String getPassword() throws IOException {
		return getProperty("password");
	}
	
	
	public boolean setShowNotifications(Boolean showNotifications) throws IOException
	{
		return setProperty("showNotifications", Boolean.toString(showNotifications));
	}
	
	public boolean setApiBase(String apiBase) throws IOException
	{
		return setProperty("apiBase", apiBase);
	}
	
	public boolean setUsername(String username) throws IOException {
		return setProperty("username", username);
	}
	
	public boolean setPassword(String password) throws IOException {
		return setProperty("password", password);
	}
	
	public boolean setSyncDelay(int syncDelay) throws IOException {
		return setProperty("syncDelay", Integer.toString(syncDelay));
	}
	
	public String getProperty(String propertyName) throws IOException {
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = new FileInputStream(configurationFilePath);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			// get the property value and print it out
			return prop.getProperty(propertyName);
		} catch (Exception e) {
			BBoxLog.LogException("Failed to get configuration property", e);
		} finally {
			inputStream.close();
		}
		return null;
	}
	
	public boolean setProperty(String propertyName, String propertyValue) throws IOException {
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = new FileInputStream(configurationFilePath);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			inputStream.close();
			
			// get the property value and print it out
			FileOutputStream out = new FileOutputStream(configurationFilePath);
			prop.setProperty(propertyName, propertyValue);
			prop.store(out, null);
			out.close();
			return true;
		} catch (Exception e) {
			BBoxLog.LogException("Failed to set configuration property", e);
		} finally {
			
		}
		return false;
	}
}
