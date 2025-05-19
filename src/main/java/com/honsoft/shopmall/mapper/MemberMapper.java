package com.honsoft.shopmall.mapper;

import org.mapstruct.Mapper;

import com.honsoft.shopmall.dto.MemberDto;
import com.honsoft.shopmall.entity.Member;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {
	public abstract MemberDto toDto(Member member);
	public abstract Member toEntity(MemberDto memberDto);
	
	
}
