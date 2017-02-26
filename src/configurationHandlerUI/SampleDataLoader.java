package configurationHandlerUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import shared.ComparisonOperator;
import shared.ContentSpecificationType;
import shared.Environment;
import shared.ConfigurationArtifacts.ArtifactType;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.ContentSpecification;

// This class contains hard-coded information to load sample data files

public class SampleDataLoader {
	// Array of all sample configuration files to load
	public static String[] sampleFiles = {
			"config.ini",
			"FileZillaServer.xml",
			"VCA_Calibration.xml",
			"VirtualBox.xml",
			"zenmap.conf",
			"json_sample.json"};
	public static String sampleDataPath =  System.getProperty("user.dir") + "\\sampledata\\";
	
	public static Collection<ConfigurationArtifact> getSampleData()
	{
		//Get sample file directory
		System.out.println(sampleDataPath);
		
		ArrayList<ConfigurationArtifact> sampleArtifacts = new ArrayList<ConfigurationArtifact>();
		for(String filename: sampleFiles)
		{
			//Try to read file
			String fullpath = sampleDataPath + filename;
			File f = new File(fullpath);
			if(f.exists() && !f.isDirectory()) { 
				
				ConfigurationArtifact artifact = new ConfigurationArtifact();
				
				artifact.setContentPath(f.toURI().toString());
				System.out.println("Loaded file: " + fullpath);
				
				String artifactData = "";
				
				//Read file content
				Scanner sc;
				try {
					sc = new Scanner(f);
					while(sc.hasNext())
					{
						artifactData += sc.nextLine() + "\n";
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				artifact.setArtifactData(artifactData);
				
				//Parse file and create artifact
				switch(filename)
				{
					case("config.ini"):
						artifact.setSourceComponentName("Eclipse");
						artifact.setArtifactType(ArtifactType.TEXT);
						artifact.setArtifactName("config.ini");
						artifact.setArtifactEnvironment(Environment.PROD);
						artifact.setDocumentationURL("http://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.pde.doc.user%2Fguide%2Ftools%2Feditors%2Fproduct_editor%2Fconfiguration.htm");
						sampleArtifacts.add(artifact);
						break;
					case("FileZillaServer.xml"):
						artifact.setSourceComponentName("FileZilla");
						artifact.setArtifactType(ArtifactType.XML);
						artifact.setArtifactName("FileZillaServer.xml");
						artifact.setArtifactEnvironment(Environment.PROD);
						artifact.setDocumentationURL("https://wiki.filezilla-project.org/Network_Configuration");
						sampleArtifacts.add(artifact);
						break;
					case("VCA_Calibration.xml"):
						artifact.setSourceComponentName("VCA");
						artifact.setArtifactType(ArtifactType.XML);
						artifact.setArtifactName("VCA_Calibration.xml");
						artifact.setArtifactEnvironment(Environment.PROD);
						artifact.setDocumentationURL("http://www.videonext.com/docs/en-US/Stratus/3.3/html/Administration_Manual/analytics_vca_calibration.html");
						sampleArtifacts.add(artifact);
						break;
					case("VirtualBox.xml"):
						artifact.setSourceComponentName("VirtualBox");
						artifact.setArtifactType(ArtifactType.XML);
						artifact.setArtifactName("VirtualBox.xml");
						artifact.setArtifactEnvironment(Environment.PROD);
						artifact.setDocumentationURL("https://www.virtualbox.org/manual/ch10.html");
						
						//Add content Specification
						Collection<ContentSpecification> content = artifact.getContentSpecification();
						content.add(new ContentSpecification("/VirtualBox/Global/SystemProperties/@defaultMachineFolder", ContentSpecificationType.STRING, ComparisonOperator.NOT_EQUAL, true, "", "n/a"));
						content.add(new ContentSpecification("/VirtualBox/Global/SystemProperties/@LogHistoryCount", ContentSpecificationType.INT, ComparisonOperator.GREATER, true, "0", "n/a"));
						content.add(new ContentSpecification("/VirtualBox/Global/SystemProperties/@defaultHardDiskFormat", ContentSpecificationType.STRING, ComparisonOperator.NONE, true, "n/a", "n/a"));
						content.add(new ContentSpecification("/VirtualBox/@shouldFail", ContentSpecificationType.STRING, ComparisonOperator.NONE, true, "n/a", "n/a"));
						content.add(new ContentSpecification("/VirtualBox/@shouldWarn", ContentSpecificationType.STRING, ComparisonOperator.NONE, false, "n/a", "n/a"));
						artifact.setContentSpecification(content);
						sampleArtifacts.add(artifact);
						break;
					case("zenmap.conf"):
						artifact.setSourceComponentName("Zenmap");
						artifact.setArtifactType(ArtifactType.TEXT);
						artifact.setArtifactName("zenmap.conf");
						artifact.setDocumentationURL("https://nmap.org/book/zenmap-conf.html");
						artifact.setArtifactEnvironment(Environment.PROD);
						sampleArtifacts.add(artifact);
						break;
					case("json_sample.json"):
						artifact.setSourceComponentName("Unknown");
						artifact.setArtifactType(ArtifactType.JSON);
						artifact.setArtifactName("Sample: json_sample.json");
						artifact.setArtifactEnvironment(Environment.PROD);
						sampleArtifacts.add(artifact);
						
						break;
					default: 
						// Do nothing
				}
			}
			else
			{
				System.out.println("Failed to load file: " + sampleDataPath + filename);
			}
		}
		return sampleArtifacts;
	}

	public static Collection<ConfigurationArtifact> getChefSampleData()
	{
		String sampleDataPathChef = sampleDataPath + "DevOps\\Chef\\Sample Cookbooks\\";
		//Get sample file directory
		System.out.println(sampleDataPathChef);
		
		String[] sampleFilesChef = {
				"git\\recipes\\default.rb",
				"git\\recipes\\package.rb",
				"git\\recipes\\server.rb",
				"git\\recipes\\source.rb",
				"git\\recipes\\windows.rb",
				"java\\recipes\\default.rb",
				"java\\recipes\\homebrew.rb",
				"java\\recipes\\ibm_tar.rb",
				"java\\recipes\\imb.rb",
				"java\\recipes\\openjdk.rb",
				"java\\recipes\\oracle.rb",
				"java\\recipes\\purge_packages.rb",
				"java\\recipes\\set_java_home.rb",
				"java\\recipes\\windows.rb",
				"mysql\\libraries\\helpers.rb",
				"mysql\\libraries\\matchers.rb",
				"mysql\\libraries\\provider_mysql_client.rb",
				"php\\recipes\\default.rb",
				"php\\recipes\\ini.rb",
				"php\\recipes\\module_apc.rb",
				"php\\recipes\\module_curl.rb",
				"php\\recipes\\module_fpdf.rb",
				"php\\recipes\\module_gd.rb",
				"php\\recipes\\module_ldap.rb",
				"php\\recipes\\module_memcache.rb",
				"php\\recipes\\module_mysql.rb",
				"php\\recipes\\module_pgsql.rb",
				"php\\recipes\\module_sqlite3.rb",
				"php\\recipes\\package.rb",
				"php\\recipes\\recompile.rb",
				"php\\recipes\\source.rb"
				};
		
		ArrayList<ConfigurationArtifact> sampleArtifacts = new ArrayList<ConfigurationArtifact>();
		for(String filename: sampleFilesChef)
		{
			//Try to read file
			String fullpath = sampleDataPathChef + filename;
			File f = new File(fullpath);
			if(f.exists() && !f.isDirectory()) { 
				
				ConfigurationArtifact artifact = new ConfigurationArtifact();
				
				artifact.setContentPath(f.toURI().toString());
				System.out.println("Loaded file: " + fullpath);
				
				String artifactData = "";
				
				//Read file content
				Scanner sc;
				try {
					sc = new Scanner(f);
					while(sc.hasNext())
					{
						artifactData += sc.nextLine() + "\n";
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				artifact.setArtifactData(artifactData);
				artifact.setArtifactType(ArtifactType.Ruby);
				artifact.setArtifactEnvironment(Environment.PROD);
				artifact.setDocumentationURL("");
				
				//Parse file and create artifact
				switch(filename)
				{
					case("git\\recipes\\default.rb"):
						artifact.setSourceComponentName("Git");
						artifact.setArtifactName("default.rb");
						sampleArtifacts.add(artifact);
						break;
					case("git\\recipes\\package.rb"):
						artifact.setSourceComponentName("Git");
						artifact.setArtifactName("package.rb");
						sampleArtifacts.add(artifact);
						break;
					case("git\\recipes\\server.rb"):
						artifact.setSourceComponentName("Git");
						artifact.setArtifactName("server.rb");
						sampleArtifacts.add(artifact);
						break;
					case("git\\recipes\\source.rb"):
						artifact.setSourceComponentName("Git");
						artifact.setArtifactName("source.rb");
						sampleArtifacts.add(artifact);
						break;
					case("git\\recipes\\windows.rb"):
						artifact.setSourceComponentName("Git");
						artifact.setArtifactName("windows.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\homebrew.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("homebrew.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\ibm_tar.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("ibm_tar.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\ibm.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("ibm.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\openjdk.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("openjdk.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\oracle.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("oracle.rb");
						sampleArtifacts.add(artifact);
						break;
					case("java\\recipes\\purge_packages.rb"):
						artifact.setSourceComponentName("JAVA");
						artifact.setArtifactName("purge_packages.rb");
						sampleArtifacts.add(artifact);
						break;
					case("mysql\\libraries\\helpers.rb"):
						artifact.setSourceComponentName("mysql");
						artifact.setArtifactName("helpers.rb");
						sampleArtifacts.add(artifact);
						break;
					case("mysql\\libraries\\matchers.rb"):
						artifact.setSourceComponentName("mysql");
						artifact.setArtifactName("matchers.rb");
						sampleArtifacts.add(artifact);
						break;
					case("mysql\\libraries\\provider_mysql_client.rb"):
						artifact.setSourceComponentName("mysql");
						artifact.setArtifactName("provider_mysql_client.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\default.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("default.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\ini.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("ini.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_apc.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_apc.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_curl.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_curl.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_fpdf.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_fpdf.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_gd.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_gd.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_ldap.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_ldap.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_memcache.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_memcache.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_mysql.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_mysql.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_pgsql.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_pgsql.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\module_sqlite3.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("module_sqlite3.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\package.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("package.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\recompile.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("recompile.rb");
						sampleArtifacts.add(artifact);
						break;
					case("php\\recipes\\source.rb"):
						artifact.setSourceComponentName("PHP");
						artifact.setArtifactName("source.rb");
						sampleArtifacts.add(artifact);
						break;
					default: 
						// Do nothing
				}
			}
			else
			{
				System.out.println("Failed to load file: " + sampleDataPathChef + filename);
			}
		}
		return sampleArtifacts;
	}
}
