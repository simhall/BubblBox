package databaseAdapter;

import java.util.ArrayList;
import java.util.List;

import shared.AuditLog;

public class AuditLogList {
	//private final long id;
    //private final String content;
	private ArrayList<AuditLog> auditLogs;
	
    public AuditLogList() {
        this.auditLogs = new ArrayList<AuditLog>();
    }
    
    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

}
