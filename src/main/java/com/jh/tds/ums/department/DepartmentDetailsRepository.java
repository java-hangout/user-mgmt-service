package com.jh.tds.ums.department;


import com.jh.tds.ums.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentDetailsRepository extends MongoRepository<Department, String> {
    Department findByDepartmentName(String departmentName);

    // Custom query to check if a department with a name already exists
    boolean existsByDepartmentName(String departmentName);

}
