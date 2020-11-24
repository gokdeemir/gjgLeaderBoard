package com.emir.gjg.mapper;

import com.emir.gjg.common.Converter;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.CompleteUserResource;
import com.emir.gjg.util.MailUtil;
import com.emir.gjg.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Created by Emir Gökdemir
 * on 11 Kas 2019
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtil.class})
public interface UserMapper extends Converter<RegisterDto, User, CompleteUserResource> {

    @Override
    @Mapping(source = "countryIsoCode", target = "country", qualifiedByName = "toLocale")
    User toEntity(RegisterDto registerDto);
}
