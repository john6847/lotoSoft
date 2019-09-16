package com.b.r.loteriab.r.Model.Filter.Mapper;

import com.b.r.loteriab.r.Model.Filter.Dto.PosDto;
import com.b.r.loteriab.r.Model.Pos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PosMapper {
    PosDto map(Pos pos);
}
