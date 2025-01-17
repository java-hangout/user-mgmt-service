package com.jh.tds.ums.repository;

import com.jh.tds.ums.model.Reward;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RewardRepository extends MongoRepository<Reward, String> {
    List<Reward> findByUserName(String userName);
//    List<Reward> findByTeamId(String teamId);
    List<Reward> findByDepartmentName(String departmentName);
}
