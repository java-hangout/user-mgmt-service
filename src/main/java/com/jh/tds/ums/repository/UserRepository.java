package com.jh.tds.ums.repository;


import com.jh.tds.ums.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);
    User findByEmailId(String emailId);
    List<User> findByUserNameIn(Set<String> userNames);
}
