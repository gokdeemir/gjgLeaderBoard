package com.emir.gjg.service;

import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.ScoreResource;
import com.emir.gjg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2020
 */
@Service
public class ScoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    @Modifying
    public ScoreResource submitScore(Double scoreWorth, UUID userId) {
        User user = userService.fromIdToEntity(userId);
        user.setScore(user.getScore() + scoreWorth);
        user = userRepository.saveAndFlush(user);
        return new ScoreResource(userId, user.getLastModifiedDate().toEpochSecond(), scoreWorth, user.getScore());
    }
}
