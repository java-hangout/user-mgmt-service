package com.jh.tds.ums.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "user_audit_logs")
public class UserAuditLog {

    @Id
    private String id;
    private String action;           // Action type: insert, update, delete
    private String userName;       // The UserName
    private String documentDetails;  // The details of the changed document (JSON string)
    private Date timestamp;          // The timestamp of when the change occurred

    public UserAuditLog(String action, String userName, String documentDetails) {
        this.action = action;
        this.userName = userName;
        this.documentDetails = documentDetails;
        this.timestamp = new Date();
    }
}
