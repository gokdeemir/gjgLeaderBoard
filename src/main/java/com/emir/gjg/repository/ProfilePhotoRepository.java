package com.emir.gjg.repository;

import com.emir.gjg.model.entity.ProfilePhoto;
import com.emir.gjg.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfilePhotoRepository extends GjgRepository<ProfilePhoto, UUID> {

    ProfilePhoto findProfilePhotoByUserId(UUID userId);

    Boolean existsByUser(User user);
}
