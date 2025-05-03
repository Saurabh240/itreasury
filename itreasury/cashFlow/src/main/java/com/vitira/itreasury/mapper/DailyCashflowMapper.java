package com.vitira.itreasury.mapper;


import com.vitira.itreasury.dto.CreateDailyCashflowRequest;
import com.vitira.itreasury.dto.DailyCashFlowDto;
import com.vitira.itreasury.entity.DailyCashFlowEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyCashflowMapper {
    DailyCashFlowEntity toEntity(CreateDailyCashflowRequest req);

    DailyCashFlowDto toDto(DailyCashFlowEntity entity);
}