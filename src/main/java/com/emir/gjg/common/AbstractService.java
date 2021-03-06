package com.emir.gjg.common;

import com.emir.gjg.exception.BadRequestException;
import com.emir.gjg.exception.NotFoundException;
import com.emir.gjg.repository.GjgRepository;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

import static com.emir.gjg.constant.ErrorConstants.*;
import static com.emir.gjg.constant.GeneralConstants.PAGE_SIZE;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
public abstract class AbstractService<T extends AbstractEntity<ID>, ID extends Serializable, DTO, RES> {

    public abstract GjgRepository<T, ID> getRepository();

    public abstract Converter<DTO, T, RES> getConverter();

    @Transactional
    public RES save(DTO dto, UUID userId) {
        try {
            T entity = getConverter().toEntity(dto);
            return getConverter().toResource(afterSaveOperations(getRepository().save(saveOperations(entity, dto, userId))));
        } catch (Exception e) {
            throw new BadRequestException(ID_IS_NOT_EXIST);
            //todo düzgün handle et.
        }
    }

    @Named("resourceFromId")
    public RES get(ID id) {
        T entity;
        try {
            entity = getRepository().findOneById(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
        return getConverter().toResource(entity);
    }

    public List<RES> getMultiple(List<ID> ids) {
        List<T> entities;
        try {
            entities = getRepository().findAllByIdIn(ids);
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
        return getConverter().toResources(entities);
    }

    public List<RES> getAll() {
        return getConverter().toResources(getRepository().findAll());
    }

    @Modifying
    @Transactional
    public RES put(ID id, DTO updatedDto, UUID userId) {
        if (id == null) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        T oldEntity;
        Optional<T> oldEntityOpt = getRepository().findById(id);
        if (!oldEntityOpt.isPresent()) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        } else {
            oldEntity = oldEntityOpt.get();
        }
        if (updatedDto == null) {
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        try {
            T updated = getConverter().toEntity(updatedDto);
            updated.setId(oldEntity.getId());
            updated.setCreatedDate(oldEntity.getCreatedDate());
            updated.setVersion(oldEntity.getVersion());
            updated = putOperations(oldEntity, updated, userId);
            return getConverter().toResource(getRepository().save(updated));
        } catch (Exception e) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
    }

    @Transactional
    @Modifying
    public void delete(ID id, UUID userId) {
        if (id == null) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        try {
            deleteOperations(id, userId);
            getRepository().deleteById(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
    }

    public Page<RES> getAllWithPage(int pageNumber, String sortBy, Boolean desc) {
        PageRequest pageable;
        if (desc == null) {
            desc = true;
        }
        try {
            if (desc) {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.DESC, sortBy);
            } else {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.ASC, sortBy);
            }
            return getRepository().findAll(pageable).map(getConverter()::toResource);
        } catch (Exception e) {
            pageable = PageRequest.of(pageNumber, PAGE_SIZE);
            return getRepository().findAll(pageable).map(getConverter()::toResource);
        }
    }

    protected T putOperations(T oldEntity, T newEntity, UUID userId) {
        return newEntity;
    }

    protected T saveOperations(T entity, DTO dto, UUID userId) {
        return entity;
    }

    protected T afterSaveOperations(T entity) {
        return entity;
    }

    protected void deleteOperations(ID id, UUID userId) {
    }

    @Named("fromIdToEntity")
    public T fromIdToEntity(ID id) {
        try {
            return getRepository().findById(id).orElseThrow(() -> new NotFoundException(ID_IS_NOT_EXIST));
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
    }

    @Named("fromIdsToEntities")
    public Set<T> toDefect(Set<ID> ids) {
        if (ids == null) {
            return new HashSet<>();
        } else {
            return getRepository().findByIdIn(ids);
        }
    }
}
