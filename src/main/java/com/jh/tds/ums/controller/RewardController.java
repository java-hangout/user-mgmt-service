package com.jh.tds.ums.controller;

import com.jh.tds.ums.model.Reward;
import com.jh.tds.ums.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000,http://localhost:3001,http://172.19.10.137:3000") // Allow only your frontend URL
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/register")
    public Reward createReward(@RequestBody Reward reward) {
        return rewardService.addReward(reward);
    }

    @GetMapping("/fetch/all")
    public List<Reward> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @GetMapping("/fetch/id/{id}")
    public ResponseEntity<Reward> getRewardById(@PathVariable String id) {
        Reward reward = rewardService.getRewardById(id);
        if (reward != null) {
            return ResponseEntity.ok(reward);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/fetch/user/{userName}")
    public List<Reward> getRewardsByUser(@PathVariable String userName) {
        return rewardService.getRewardsByUser(userName);
    }


    /*@GetMapping("/team/{teamId}")
    public List<Reward> getRewardsByTeam(@PathVariable String teamId) {
        return rewardService.getRewardsByTeam(teamId);
    }*/

    @GetMapping("/fetch/department/{departmentName}")
    public List<Reward> getRewardsByDepartment(@PathVariable String departmentName) {
        return rewardService.getRewardsByDepartment(departmentName);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Reward> updateReward(@PathVariable String id, @RequestBody Reward reward) {
        Reward updatedReward = rewardService.updateReward(id, reward);
        if (updatedReward != null) {
            return ResponseEntity.ok(updatedReward);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable String id) {
        rewardService.deleteReward(id);
        return ResponseEntity.noContent().build();
    }
}
