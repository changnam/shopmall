package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.User;

//And for your subclass:
@Mapper(componentModel = "spring", uses = { ProductDetailMapper.class, ProductImageMapper.class })
public interface CleanerMapper {
	CleanerDto toDto(Cleaner cleaner);

	Cleaner toEntity(CleanerDto dto);

	List<CleanerDto> toDtoList(List<Cleaner> cleaners);

	List<Cleaner> toEntityList(List<CleanerDto> cleanerDtos);

	default Page<CleanerDto> toPage(Page<Cleaner> cleaners) {
		return cleaners.map(this::toDto);
	}
}