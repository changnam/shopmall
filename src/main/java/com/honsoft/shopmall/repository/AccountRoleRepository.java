package com.honsoft.shopmall.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.AccountRole;
import com.honsoft.shopmall.entity.ERole;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByErole(ERole erole);
    
    @Query("SELECT ar FROM AccountRole ar WHERE ar.erole = :erole")
    Optional<AccountRole> findByEroleName(@Param("erole") String erole);

}