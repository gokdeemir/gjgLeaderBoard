package com.emir.gjg.service;

import com.emir.gjg.exception.BadRequestException;
import com.emir.gjg.mapper.MinimalUserMapper;
import com.emir.gjg.mapper.UserMapper;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.dto.UpdatePasswordDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.CompleteUserResource;
import com.emir.gjg.model.resource.MinimalUserResource;
import com.emir.gjg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.emir.gjg.constant.ErrorConstants.*;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MinimalUserMapper minimalUserMapper;

    public CompleteUserResource getSelfProfile(UUID selfId) {
        User selfUser = userService.fromIdToEntity(selfId);
        if (selfUser == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        } else {
            return userMapper.toResource(selfUser);
        }
    }

    public MinimalUserResource getProfile(UUID userId) {
        return minimalUserMapper.toResource(userService.fromIdToEntity(userId));
    }

    @Transactional
    public CompleteUserResource updateProfile(UUID userId, RegisterDto dto) {
        User oldUser = userService.fromIdToEntity(userId);
        if (oldUser == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        User updatedUser = userMapper.toEntity(dto);
        updatedUser.setId(oldUser.getId());
        updatedUser.setPassword(oldUser.getPassword());
        updatedUser.setCreatedDate(oldUser.getCreatedDate());
        userRepository.save(updatedUser);
        return userMapper.toResource(updatedUser);
    }

    @Transactional
    public CompleteUserResource updatePassword(UUID userId, UpdatePasswordDto dto) {
        User user = userService.fromIdToEntity(userId);
        if (user == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if (!encoder.matches(dto.getOldPassword(),user.getPassword())){
            throw new BadRequestException(OLD_PASSWORD_IS_WRONG);
        }
        String newPassword = dto.getNewPassword();
        if (encoder.matches(newPassword,user.getPassword())){
            throw new BadRequestException(NEW_PASSWORD_CANNOT_BE_SAME_AS_OLD);
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.toResource(user);
    }

}
