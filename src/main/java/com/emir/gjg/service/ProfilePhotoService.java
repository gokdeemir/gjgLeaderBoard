package com.emir.gjg.service;

import com.emir.gjg.constant.ErrorConstants;
import com.emir.gjg.constant.HttpSuccessMessagesConstants;
import com.emir.gjg.exception.UnauthorizedException;
import com.emir.gjg.model.entity.ProfilePhoto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.repository.ProfilePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */
@Service
public class ProfilePhotoService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilePhotoRepository repository;

    public String uploadImage(MultipartFile file, UUID idFromToken) {
        User user = userService.fromIdToEntity(idFromToken);
        if (user == null) {
            throw new UnauthorizedException(ErrorConstants.USER_NOT_EXIST);
        }
        ProfilePhoto photo = new ProfilePhoto();
        photo.setUser(user);


        try {
            photo.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(photo);
        return HttpSuccessMessagesConstants.PHOTO_SUCCESSFULLY_UPLOADED;
    }

    @Transactional
    public byte[] getPhoto(UUID userId) {
        ProfilePhoto photo = repository.findProfilePhotoByUserId(userId);
        if (photo == null) {
            return null;
        }
        return photo.getFile();
    }
}
