package com.jh.tds.ums.service;

import com.jh.tds.ums.model.Reward;
import com.jh.tds.ums.model.User;
import com.jh.tds.ums.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardSequenceGeneratorService generatorService;

    @Autowired
    private UserService userService;

    /*public List<Reward> getAllRewards() {
        List<Reward> updateRewards = new ArrayList<>();
        List<Reward> rewards = rewardRepository.findAll();
        for (Reward reward : rewards) {
            User user = userService.findByUserName(reward.getUserName());
            reward.setUserName(user.getFirstName() + " " + user.getLastName());
            updateRewards.add(reward);
        }
        return updateRewards;
    }*/
    public List<Reward> getAllRewards() {
        // Fetch all rewards at once
        List<Reward> rewards = rewardRepository.findAll();
        return getRewardsWithFullName(rewards);

    }


    public Reward getRewardById(String id) {
        return rewardRepository.findById(id).orElse(null);
    }

    public List<Reward> getRewardsByUser(String userName) {
        return rewardRepository.findByUserName(userName);
    }

    /*public List<Reward> getRewardsByTeam(String teamId) {
        return rewardRepository.findByTeamId(teamId);
    }*/

    public List<Reward> getRewardsByDepartment(String departmentName) {
        List<Reward> rewards = rewardRepository.findByDepartmentName(departmentName);
        return getRewardsWithFullName(rewards);
    }

    public Reward addReward(Reward reward) {
        reward.setId(generatorService.generateRewardId());
        return rewardRepository.save(reward);
    }

    public Reward updateReward(String id, Reward updatedReward) {
        Optional<Reward> existingReward = rewardRepository.findById(id);
        if (existingReward.isPresent()) {
            updatedReward.setId(id);
            return rewardRepository.save(updatedReward);
        }
        return null;
    }

    public void deleteReward(String id) {
        rewardRepository.deleteById(id);
    }

    private List<Reward> getRewardsWithFullName(List<Reward> rewards) {
        // Fetch all users that match the usernames in rewards in a single query (assuming findByUserName can handle batch queries)
        Set<String> usernames = rewards.stream()
                .map(Reward::getUserName)
                .collect(Collectors.toSet());

        // Retrieve all users by usernames at once
        List<User> users = userService.findByUserNameIn(usernames);

        // Create a map for fast lookup by username
        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getUserName, user -> user));

        // Update rewards with full names from the user data
        return rewards.stream()
                .map(reward -> {
                    User user = userMap.get(reward.getUserName());
                    if (user != null) {
                        reward.setUserName(user.getFirstName() + " " + user.getLastName());
                    }
                    return reward;
                })
                .collect(Collectors.toList());
    }
}
