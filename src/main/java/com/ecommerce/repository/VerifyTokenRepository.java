package com.ecommerce.repository;

import com.ecommerce.model.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyTokenRepository extends JpaRepository<VerifyToken, Long> {
    VerifyToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM VerifyToken v WHERE v.user.id = :userId")
    void deleteByUserId(Long userId);
}
