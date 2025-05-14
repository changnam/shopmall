package com.honsoft.shopmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
