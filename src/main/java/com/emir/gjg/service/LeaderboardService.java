package com.emir.gjg.service;

import com.emir.gjg.constant.GeneralConstants;
import com.emir.gjg.model.resource.LeaderboardResource;
import com.emir.gjg.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by Emir Gökdemir
 * on 23 Kas 2020
 */
@Service
public class LeaderboardService {

    @Autowired
    private UserRepository userRepository;

    public Page<LeaderboardResource> getLeaderBoard(@Nullable String countryIso, @Nullable Integer pageNumber) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        PageRequest pageable = PageRequest.of(pageNumber, GeneralConstants.PAGE_SIZE, Sort.Direction.DESC, "score");
        if (countryIso == null) {
            return userRepository.findByOrderByScoreDesc(pageable);
        } else {
            return userRepository.findByOrderByScoreDescWithLocal(pageable,new Locale(countryIso));
        }
    }
}
