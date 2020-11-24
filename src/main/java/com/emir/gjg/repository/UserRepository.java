package com.emir.gjg.repository;

import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.LeaderboardResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.*;

@Repository
public interface UserRepository extends GjgRepository<User, UUID> {

    Set<User> findByIdIn(Collection<UUID> ids);

    User findByEmail(String email);

    User findUserByName(String name);

    Boolean existsByEmail(String email);

    List<User> findAll();

    User findUserByUserName(String userName);

    @Query("select new com.emir.gjg.model.resource.LeaderboardResource(u.score, u.userName, u.country) " +
            " from User u " )
    Page<LeaderboardResource> findByOrderByScoreDesc(Pageable pageable);

    @Query("select new com.emir.gjg.model.resource.LeaderboardResource(u.score, u.userName, u.country) " +
            " from User u " +
            " where u.country = :locale" )
    Page<LeaderboardResource> findByOrderByScoreDescWithLocal(Pageable pageable, Locale locale);

}
