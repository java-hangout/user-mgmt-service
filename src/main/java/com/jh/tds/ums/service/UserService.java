package com.jh.tds.ums.service;

import com.jh.tds.ums.model.User;

import java.util.List;

/**
 * @author Veeresh N
 * @version 1.0
 */
public interface UserService {
    public User registerUser(User user);
    public User getUserById(String id);
    public User updateUser(User user);
    public void deleteById(String id);
    public User findByUserName(String userName);
    public List<User> getAllUsers();
}