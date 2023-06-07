package backend.plugfinder.services.models;

import java.time.LocalDateTime;

public class MessageModel {
    String message;
    long source_id;
    long target_id;
    String source_email;
    String target_email;
    String time;

    public MessageModel() {
    }

    public MessageModel(String message, String source_email, String target_email, String time) {
        this.message = message;
        this.source_email = source_email;
        this.target_email = target_email;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
    }

    public String getSource_email() {
        return source_email;
    }

    public void setSource_email(String source_email) {
        this.source_email = source_email;
    }

    public String getTarget_email() {
        return target_email;
    }

    public void setTarget_email(String target_email) {
        this.target_email = target_email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
