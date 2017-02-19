package comparators;

import java.util.Comparator;

import shared.AuditLog;

public class CompareAuditLogDates implements Comparator<AuditLog>
{
	@Override
	public int compare(AuditLog o1, AuditLog o2) {
		return o1.getLogTimestamp().compareTo(o2.getLogTimestamp());
	}
}