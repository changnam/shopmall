package com.honsoft.shopmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honsoft.shopmall.entity.BizExceptionMessage;
import com.honsoft.shopmall.entity.BizExceptionMessageId;

public interface BizExceptionMessageRepository extends JpaRepository<BizExceptionMessage, BizExceptionMessageId> {
	Optional<BizExceptionMessage> findByCodeAndLocale(String code, String locale);
}
