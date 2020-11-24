package com.emir.gjg.service;

import com.emir.gjg.common.AbstractService;
import com.emir.gjg.common.Converter;
import com.emir.gjg.exception.BadRequestException;
import com.emir.gjg.mapper.MinimalUserMapper;
import com.emir.gjg.mapper.UserMapper;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.CompleteUserResource;
import com.emir.gjg.model.resource.MinimalUserResource;
import com.emir.gjg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.emir.gjg.constant.ErrorConstants.ID_IS_NOT_EXIST;

/**
 * Created by Emir Gökdemir
 * on 3 May 2020
 */

@Service
public class UserService extends AbstractService<User, UUID, RegisterDto, MinimalUserResource> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MinimalUserMapper minimalUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public Converter<RegisterDto, User, MinimalUserResource> getConverter() {
        return minimalUserMapper;
    }

    @Override
    protected User putOperations(User oldEntity, User newEntity, UUID userId) {
        newEntity.setConfirmed(oldEntity.isConfirmed());
        newEntity.setScore(oldEntity.getScore());
        return super.putOperations(oldEntity, newEntity, userId);
    }

    @Override
    protected User saveOperations(User entity, RegisterDto registerDto, UUID userId) {
        return super.saveOperations(entity, registerDto, userId);
    }

    @Transactional
    public CompleteUserResource create(RegisterDto dto) {
        try {
            return userMapper.toResource(getRepository().save(userMapper.toEntity(dto)));
        } catch (Exception e) {
            throw new BadRequestException(ID_IS_NOT_EXIST);
        }
    }
}
