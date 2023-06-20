package ru.pasteshare.serviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pasteshare.serviceapi.model.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    @Query("SELECT t FROM RefreshToken t WHERE t.expiredAt <= CURRENT_TIMESTAMP AND t.status = 'ACTIVE'")
    List<RefreshToken> findExpiredTokens();
}
