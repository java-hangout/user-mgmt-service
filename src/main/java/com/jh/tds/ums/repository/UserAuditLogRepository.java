package com.jh.tds.ums.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jh.tds.ums.model.UserAuditLog;

public interface UserAuditLogRepository extends MongoRepository<UserAuditLog, String> {
}
