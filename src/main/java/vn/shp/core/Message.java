package vn.shp.core;

import lombok.Data;

@Data
public class Message {
    // type status
    public static final String ERROR = "error";
    public static final String INFO = "info";
    public static final String SUCCESS = "success";
    public static final String WARNING = "warning";

    /** status: danger, info, success, warning */
    private String status;
    private String content;
    private String field;

    /**
     * Constructor params: status, content
     */
    public Message(String status, String content) {
        super();
        this.status = status;
        this.content = content;
    }

    /**
     * Constructor params: status, field, content
     */
    public Message(String status, String field, String content) {
        super();
        this.status = status;
        this.content = content;
        this.field = field;
    }

}
