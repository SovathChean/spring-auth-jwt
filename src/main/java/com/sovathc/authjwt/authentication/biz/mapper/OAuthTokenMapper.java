package com.sovathc.authjwt.authentication.biz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OAuthTokenMapper {
    public static final OAuthTokenMapper INSTANCE = Mappers.getMapper(OAuthTokenMapper.class);
}
