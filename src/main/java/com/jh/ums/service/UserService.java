package com.jh.ums.service;


import com.jh.ums.model.User;
import com.jh.ums.registry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private com.jh.ums.registry.UserRepository userRepository;

    public com.jh.ums.model.User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }
}
