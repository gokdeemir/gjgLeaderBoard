package com.emir.gjg.mapper;

import com.emir.gjg.common.Converter;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.MinimalUserResource;
import com.emir.gjg.util.MailUtil;
import com.emir.gjg.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtil.class})
public interface MinimalUserMapper extends Converter<RegisterDto, User, MinimalUserResource> {

    @Override
    @Mapping(source = "countryIsoCode", target = "country", qualifiedByName = "toLocale")
    User toEntity(RegisterDto registerDto);

}
