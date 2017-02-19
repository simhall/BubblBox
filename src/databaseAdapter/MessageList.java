package databaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
	//private final long id;
    //private final String content;
	private ArrayList<Message> messages;
	
    public MessageList() {
        this.messages = new ArrayList<Message>();
    }
    
    public List<Message> getMessages() {
        return messages;
    }

}
