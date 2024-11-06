package com.ecommerce.repository;

import com.ecommerce.model.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyTokenRepository extends JpaRepository<VerifyToken, Long> {
    VerifyToken findByToken(String token);
}
