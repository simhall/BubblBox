package bbox_server;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import comparators.CompareAuditLogDates;
import configurationHandlerUI.SampleDataLoader;
import shared.AuditLog;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.PreviousVersionArtifact;

@RestController
public class BuBBlBoxRestController {

    @RequestMapping("/GetAllArtifacts")
    public ConfigurationArtifactList GetAllArtifacts () {
    	Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
    	ConfigurationArtifactList artifactCollection = new ConfigurationArtifactList();
    	for(ConfigurationArtifact artifact : artifacts)
    	{
    		artifactCollection.getGreetings().add(artifact);
    	}
    	return artifactCollection;
    }
    
    @RequestMapping("/GetAllPreviousArtifacts")
    public PreviousVersionArtifactList GetAllPreviousArtifacts () {
    	Collection<PreviousVersionArtifact> artifacts = Neo4jAdapter.getInstance().getAllPreviousVersionArtifacts();
    	PreviousVersionArtifactList artifactCollection = new PreviousVersionArtifactList();
    	for(PreviousVersionArtifact artifact : artifacts)
    	{
    		artifactCollection.getPreviousVersions().add(artifact);
    	}
    	return artifactCollection;
    }
    
    @RequestMapping("/GetArtifactData")
    public ConfigurationArtifact GetArtifactData (String artifactName)
    {	
    	/*ConfigurationArtifactList artifactCollection = new ConfigurationArtifactList();
    	ConfigurationArtifact artifact = Neo4jAdapter.getInstance().getConfigurationArtifact(artifactName);
    	if(artifact != null)
    	{
    		artifactCollection.getGreetings().add(artifact);
    	}
    	return artifactCollection;*/
    	return Neo4jAdapter.getInstance().getConfigurationArtifact(artifactName);
    }
    
    @RequestMapping(value = "/SetArtifactData", method = RequestMethod.POST)
    public ResponseEntity<Boolean> SetArtifactData (@RequestBody ConfigurationArtifact artifact)
    {	
    	System.err.println("Gettin artifact with id: " + artifact.getArtifactID());
    	// Store previous version of artifact
    	ConfigurationArtifact dbArtifact = Neo4jAdapter.getInstance().getConfigurationArtifact(artifact.getArtifactID());
    	
    	System.err.println("Found artifact: " + dbArtifact.getArtifactName());
    	
    	// Update artifact
    	Collection<PreviousVersionArtifact> previousVersions = dbArtifact.getPreviousVersions();
    	
    	previousVersions.add(new PreviousVersionArtifact(dbArtifact));
    	artifact.setPreviousVersions(previousVersions);
    	artifact.setLastUpdate(new Date());
    	
    	// Generate audit log
    	AuditLog log = new AuditLog();
    	log.setLogMessage("Artifact: " + artifact.getArtifactName() + " updated");
    	log.setLogTimestamp(new Date());
    	//log.setRelatedObject(artifact);
    	log.setRelatedObjectId(artifact.getArtifactID());
    	log.setUserId(artifact.getUpdatedBy());
    	Neo4jAdapter.getInstance().insertAuditLog(log);
    	
    	return new ResponseEntity<Boolean>(Neo4jAdapter.getInstance().updateConfigurationArtifact(artifact), HttpStatus.OK);
    }
    
    @RequestMapping("/GetStatistics")
    public StatisticsCollection GetStatistics () {
    	Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
    	StatisticsCollection stats = new StatisticsCollection();
    	stats.NumArtifacts = artifacts.size();
    	return stats;
    }
    
    @RequestMapping("/GetMessages")
    public MessageList GetMessageList () {
    	MessageList messageList = new MessageList();
    	Message msg = new Message();
    	msg.Message = "Messages to be implemented";
    	msg.Sender = "System";
    	msg.Timestamp = new Date();
    	messageList.getMessages().add(msg);
    	return messageList;
    }
    
    @RequestMapping("/GetNotifications")
    public NotificationList GetNotificationList () {
    	NotificationList messageList = new NotificationList();
    	
    	Collection<AuditLog> auditLogs = Neo4jAdapter.getInstance().getAllAuditLogs();
    	if(auditLogs.size() == 0)
    	{
    		Notification msg = new Notification();
        	msg.Message = "No notifications";
        	msg.Type = "";
        	msg.Timestamp = new Date();
        	messageList.getMessages().add(msg);
    	}
    	else
    	{
    		List<AuditLog> sortedLogList = new ArrayList<AuditLog>(auditLogs);
        	Collections.sort(sortedLogList, new CompareAuditLogDates());
        	for(int i = 0; i < Math.min(5, sortedLogList.size()); ++i)
        	{
        		Notification msg = new Notification();
            	msg.Message = sortedLogList.get(i).getLogMessage();
            	msg.Type = "Audit Log";
            	msg.Timestamp = new Date();
            	messageList.getMessages().add(msg);
        	}
    	}
    	
    	return messageList;
    }
    
    @RequestMapping("/GetAuditLogs")
    public AuditLogList GetAuditLogList () {
    	AuditLog log = new AuditLog();
    	log.setLogMessage("test1");
    	log.setLogTimestamp(new Date());
    	//log.setRelatedObject(null);
    	log.setRelatedObjectId(123);
    	log.setUserId("Test");
    	Neo4jAdapter.getInstance().insertAuditLog(log);
    	
    	Collection<AuditLog> auditLogs = Neo4jAdapter.getInstance().getAllAuditLogs();
    	AuditLogList logList = new AuditLogList();
    	for(AuditLog auditLog : auditLogs)
    	{
    		logList.getAuditLogs().add(auditLog);
    	}
    	return logList;
    }
    
    @RequestMapping("/InsertSampleData")
    public String InsertSampleData () {
    	// Delete all existing artifacts
		Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
		for(ConfigurationArtifact art: artifacts)
		{
			Neo4jAdapter.getInstance().deleteConfigurationArtifact(art);
		}
		
		// Load sample data
		Collection<ConfigurationArtifact> sampleArtifacts = SampleDataLoader.getSampleData();
		for(ConfigurationArtifact art: sampleArtifacts)
		{
			Neo4jAdapter.getInstance().createConfigurationArtifact(art);
		}
		
    	return "Success";
    			
    }
}
