package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.CleanerDto;
import com.honsoft.shopmall.dto.ComputerDto;
import com.honsoft.shopmall.entity.Cleaner;
import com.honsoft.shopmall.entity.Computer;

//And for your subclass:
@Mapper(componentModel = "spring", uses = { ProductDetailMapper.class, ProductImageMapper.class })
public interface ComputerMapper {
	ComputerDto toDto(Computer computer);

	Computer toEntity(ComputerDto dto);

	List<ComputerDto> toDtoList(List<Computer> computers);

	List<Computer> toEntityList(List<ComputerDto> computerDtos);
}