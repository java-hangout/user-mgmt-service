package com.jh.tds.ums.service;


import com.jh.tds.ums.department.DepartmentDetailsRepository;
import com.jh.tds.ums.exception.*;
import com.jh.tds.ums.model.Department;
import com.jh.tds.ums.model.User;
import com.jh.tds.ums.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private UserAuditLogService userAuditLogService;

    @Autowired
    DepartmentDetailsRepository departmentRepository;

    @Autowired
    private RestTemplate restTemplate;  // RestTemplate for communication with Department Service

    @Value("${department.service.base.url}")  // URL for Department Service
    private String departmentServiceUrl;

    @Override
    public User registerUser(User user) {
        //user.setPassword(PWHashingUtil.hashPassword(user.getPassword()));
        Department department = checkDepartmentExists(user);
        User existingUser = userRepository.findByUserName(user.getUserName());
        // Check if user with the same name already exists
        if (existingUser != null) {
            // Throw an exception with a dynamic message
            throw new DuplicateUserNameException(user.getUserName());
        }
        // Generate a unique ID for the user
        String userId = sequenceGeneratorService.generateUserId();
        user.setId(userId);  // Set the generated ID
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // Password encoder to hash passwords
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User user1 = userRepository.save(user);
        updateDepartmentWithUserDetails(user1, department);
        return user1;
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());  // Find user by ID (user_001, user_002, etc.)
        User updateUser = null;
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            updateUser = userRepository.save(user);
            userAuditLogService.logChangesForAudit(existingUser,"Update");
        }else {
            throw new UserNotFoundException(user.getId());  // Handle if user not found
        }
        return updateUser;

    }

    @Override
    public void deleteById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);  // Find user by ID (user_001, user_002, etc.)
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);  // Delete the user if found
            userAuditLogService.logChangesForAudit(user,"Delete");
        } else {
            throw new UserNotFoundException(userId);  // Handle if user not found
        }
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findByUserNameIn(Set<String> usernames){
        return userRepository.findByUserNameIn(usernames);
    }
    // Method to get all departments
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private Department checkDepartmentExists(User user) throws DepartmentNotFoundException {
        // Build the URL for the Department Service API to check if the department exists
        String departmentFindUrl = departmentServiceUrl + "/api/departments/" + user.getDepartmentId();
        System.out.println("department Find Url : "+departmentFindUrl);

        try {
            // Make a GET request to check if the department exists
            Optional<Department> department = departmentRepository.findById(user.getDepartmentId());
            System.out.println("department : "+department);
            if (department.isEmpty()) {
                // If the department doesn't exist, throw an exception
                throw new DepartmentNotFoundException("Department with ID " + user.getDepartmentId() + " not found.");
            }
            return department.get();
        } catch (Exception e) {
            // Handle potential errors such as network issues or unexpected failures
            System.out.println("Error occurred while checking the department details : " + e.getMessage());
            throw new DownStreamSystemNotAvailableException("Error occurred while checking the department details");
        }
    }

    private void updateDepartmentWithUserDetails(User user, Department department) throws ManagerAlreadyExistException {
        // Handle the case based on the user's role
//        if ("USER".equalsIgnoreCase(user.getRole())) {
            System.out.println("user.getRole(): "+user.getRole());
            // Add the user ID to the department
        if(department.getUserIds()==null){
            List<String> users = new ArrayList<>();
            users.add(user.getId());
            department.setUserIds(users);
        }else{department.getUserIds().add(user.getId());}

//            department.getUserIds().add(user.getUserName());
//            System.out.println(" department.getUserIds(): "+ department.getUserIds());
//        }
        /*else if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            System.out.println("MANAGER--->"+"user.getRole(): "+user.getRole());
            // Check if a manager already exists in the department
            String managerId = department.getManagerId();
            // Debugging: print the value of managerId
            System.out.println("Manager ID: '" + managerId + "'");
            if (managerId == null || managerId.trim().isEmpty()) {
                // Update the manager ID
                department.setManagerId(user.getId());
            } else {
                // If a manager already exists, throw an exception
                String message = "Manager already exists in the Department. Department Id: " + user.getDepartmentId() + " and Manager Id: " + managerId;
                System.out.println("Error: " + message);
                throw new ManagerAlreadyExistException(message);
            }*/
//        }
        // After making changes, update the department
        departmentRepository.save(department);
        System.out.println("Department updated successfully.");
    }


    /*private Department checkDepartmentExists(User user) throws DepartmentNotFoundException {
        // Build the URL for the Department Service API to check if the department exists
        String departmentFindUrl = departmentServiceUrl + "/api/departments/" + user.getDepartmentId();
        System.out.println("department Find Url : "+departmentFindUrl);

        try {
            // Make a GET request to check if the department exists
            Department department = restTemplate.getForObject(departmentFindUrl, Department.class);
            System.out.println("department : "+department);
            if (department == null) {
                // If the department doesn't exist, throw an exception
                throw new DepartmentNotFoundException("Department with ID " + user.getDepartmentId() + " not found.");
            }
            return department;
        } catch (Exception e) {
            // Handle potential errors such as network issues or unexpected failures
            System.out.println("Error occurred while checking the department details : " + e.getMessage());
            throw new DownStreamSystemNotAvailableException("Error occurred while checking the department details");
        }
    }

    private void updateDepartmentWithUserDetails(User user, Department department) throws ManagerAlreadyExistException {
        // Handle the case based on the user's role
        if ("USER".equalsIgnoreCase(user.getRole())) {
            System.out.println("user.getRole(): "+user.getRole());
            // Add the user ID to the department
            department.getUserIds().add(user.getId());
            System.out.println(" department.getUserIds(): "+ department.getUserIds());
        } else if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            System.out.println("MANAGER--->"+"user.getRole(): "+user.getRole());
            // Check if a manager already exists in the department
            String managerId = department.getManagerId();
            // Debugging: print the value of managerId
            System.out.println("Manager ID: '" + managerId + "'");
            if (managerId == null || managerId.trim().isEmpty()) {
                // Update the manager ID
                department.setManagerId(user.getId());
            } else {
                // If a manager already exists, throw an exception
                String message = "Manager already exists in the Department. Department Id: " + user.getDepartmentId() + " and Manager Id: " + managerId;
                System.out.println("Error: " + message);
                throw new ManagerAlreadyExistException(message);
            }
        }
        // After making changes, update the department
        String departmentUpdateUrl = departmentServiceUrl + "/api/departments/update/" + user.getDepartmentId();
        System.out.println("departmentUpdateUrl : "+departmentUpdateUrl);
        restTemplate.put(departmentUpdateUrl, department, Void.class);
        System.out.println("Department updated successfully.");
    }
*/


    /*private void updateDepartmentWithUser(User user) {
        // Build the URL for the Department Service API
        String departmentFindUrl = departmentServiceUrl + "/api/departments/" + user.getDepartmentId();
        String departmentUpdateUrl = departmentServiceUrl + "/api/departments/update/" + user.getDepartmentId();


        try {
            // Make a GET request to check if the department exists
            Department department = restTemplate.getForObject(departmentFindUrl, Department.class);

            if (department == null) {
                // If the department doesn't exist, return an error message or throw an exception
                System.out.println("Error: Department with ID " + user.getDepartmentId() + " not found.");
                // Optionally, throw a custom exception if you prefer
                 throw new DepartmentNotFoundException(user.getDepartmentId());
            } else {
                if("USER".equalsIgnoreCase(user.getRole())) {
                    department.getUserIds().add(user.getId());
                }else if("MANAGER".equalsIgnoreCase(user.getRole())) {
                    String managerId = department.getManagerId();
                    if(!managerId.isBlank()){
                        department.setManagerId(user.getId());
                    }else{
                        String message = "Manger already exist in the Department. Department Id :  " + user.getDepartmentId() + " and Manager Id : "+department.getManagerId();
                        System.out.println("Error: "+message);
                        throw new ManagerAlreadyExistException(message);
                    }
                }
                // If the department exists, update it with the user
                restTemplate.postForObject(departmentUpdateUrl, user, Void.class);
                System.out.println("Department updated successfully.");
            }
        } catch (Exception e) {
            // Handle potential errors such as network issues or unexpected failures
            System.out.println("Error occurred while checking the department: " + e.getMessage());
        }
    }*/
}
