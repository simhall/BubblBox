package bbox_server;

import java.util.ArrayList;
import java.util.List;

public class NotificationList {
	//private final long id;
    //private final String content;
	private ArrayList<Notification> notifications;
	
    public NotificationList() {
        this.notifications = new ArrayList<Notification>();
    }
    
    public List<Notification> getMessages() {
        return notifications;
    }

}
