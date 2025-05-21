package com.honsoft.shopmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	Optional<Message> findByCodeAndLocale(String code, String locale);
}
