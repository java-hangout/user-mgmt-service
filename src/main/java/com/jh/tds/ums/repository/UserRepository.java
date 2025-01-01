package com.jh.tds.ums.repository;


import com.jh.tds.ums.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);
    User findByEmailId(String emailId);
}
