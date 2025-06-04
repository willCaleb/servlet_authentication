package org.example.converter;

import org.example.model.dto.AbstractDTO;
import org.example.model.entity.AbstractEntity;

public class Converter {

    public static <E extends AbstractEntity> E toEntity(AbstractDTO dto, Class<E> entityClass) {
        return ModelMapperBuilder.getModelMapper().map(dto, entityClass);
    }

    public static <DTO extends AbstractDTO> DTO toDto(AbstractEntity entity, Class<DTO> dtoClass) {
        return ModelMapperBuilder.getModelMapper().map(entity, dtoClass);
    }

}
