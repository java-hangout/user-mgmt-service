package com.jh.tds.ums.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.tds.ums.model.User;

public class UserAuditLogUtil {

    public static String convertUserToJson(User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
