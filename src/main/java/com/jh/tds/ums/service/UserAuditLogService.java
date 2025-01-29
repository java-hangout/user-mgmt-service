package com.jh.tds.ums.service;

import com.jh.tds.ums.model.UserAuditLog;
import com.jh.tds.ums.model.User;
import com.jh.tds.ums.repository.UserAuditLogRepository;
import com.jh.tds.ums.util.UserAuditLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Veeresh N
 * @version 1.0
 */
@Service
public class UserAuditLogService {
    @Autowired
    private UserAuditLogRepository userAuditLogRepository;

    public void logChangesForAudit(User user, String action) {
        String documentDetails = UserAuditLogUtil.convertUserToJson(user);
        UserAuditLog userAuditLog = new UserAuditLog(
                action,
                user.getUserName(),
                documentDetails
        );
        userAuditLogRepository.save(userAuditLog);
    }
}
