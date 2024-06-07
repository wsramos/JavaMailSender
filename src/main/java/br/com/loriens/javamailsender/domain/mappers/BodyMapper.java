package br.com.loriens.javamailsender.domain.mappers;

import br.com.loriens.javamailsender.domain.dtos.BodyDTO;
import br.com.loriens.javamailsender.domain.entities.Body;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BodyMapper {

    @Mapping(target = "imageCode", ignore = true)
    Body toEntity(BodyDTO bodyDTO);

}
