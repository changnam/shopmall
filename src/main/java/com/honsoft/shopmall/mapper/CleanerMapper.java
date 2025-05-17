package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.entity.Cleaner;

//And for your subclass:
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductDetailMapper.class,ProductImageMapper.class,ReviewMapper.class})
public abstract class CleanerMapper {
	
	public abstract CleanerDto toDto(Cleaner cleaner);

	public abstract Cleaner toEntity(CleanerDto dto);

	public abstract List<CleanerDto> toDtoList(List<Cleaner> cleaners);

	public abstract List<Cleaner> toEntityList(List<CleanerDto> cleanerDtos);

	public Page<CleanerDto> toPage(Page<Cleaner> cleaners) {
		return cleaners.map(this::toDto);
	}
}