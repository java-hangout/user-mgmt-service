package com.jh.tds.ums.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Document(collection = "rewards")
public class Reward {
    @Id
    private String id;
    private String userName;
    private String departmentName;
    private String rewardType;
    private String reason;
    private String rewardedBy;
    private LocalDate rewardDate;
    private List<String> tags;
    private Metadata metadata;
    private List<Comment> comments;

    @Setter
    @Getter
    public static class Metadata { // Static inner class
        private String certificateUrl;
        private int points;
    }

    @Setter
    @Getter
    public static class Comment { // Static inner class
        private String commentedBy;
        private String comment;
        private LocalDate commentDate;
    }
}
