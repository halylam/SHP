package vn.shp.portal.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageList {
    private List<Message> messages;
    private String status;

    /**
     * Constructor default
     */
    public MessageList() {
        this.messages = new ArrayList<Message>();
    }

    /**
     * Constructor params: status
     */
    public MessageList(String status) {
        this.messages = new ArrayList<Message>();
        this.status = status;
    }

    public MessageList(String status, String content) {
        Message message = new Message(status, content);
        messages.add(message);
    }

    public void add(String content) {
        Message message = new Message(status, content);
        messages.add(message);
    }

    public void add(String status, String content) {
        Message message = new Message(status, content);
        messages.add(message);
    }
    
    public void add(MessageList messageList) {
        if( messageList != null ) {
            List<Message> msgLst = messageList.getMessages();
            
            if( msgLst != null && !msgLst.isEmpty() ) {
                for (Message msg : msgLst) {
                    String status = msg.getStatus();
                    String content = msg.getContent();
                    
                    Message message = new Message(status, content);
                    messages.add(message);
                }
            }
        }
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
