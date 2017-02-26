package shared;

import java.io.Serializable;
import java.util.Date;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class AuditLog implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841963221271395616L;

	@GraphId
    private Long id;
	
	private Long relatedObjectId;
	//private Object relatedObject;
	private String logMessage;
	private Date logTimestamp;
	private String userId;
	
	public AuditLog() {}
	
	public long getId() { return id.longValue(); }
	public String getUserId() { return userId; }
	public long getRelatedObjectId() { return relatedObjectId.longValue(); }
	//public Object getRelatedObject() { return relatedObject; }
	public String getLogMessage() { return logMessage; }
	public Date getLogTimestamp() {	return logTimestamp; }
	
	public void setId(long id) { this.id = Long.valueOf(id); }
	public void setRelatedObjectId(long relatedObjectId) { this.relatedObjectId = Long.valueOf(relatedObjectId); }
	//public void setRelatedObject(Object relatedObject) { this.relatedObject = relatedObject; }
	public void setLogMessage(String logMessage) { this.logMessage = logMessage; }
	public void setLogTimestamp(Date logTimestamp) { this.logTimestamp = logTimestamp; }
	public void setUserId(String userId) { this.userId = userId;}
}
