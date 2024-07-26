package com.b1.round;

import com.b1.round.entity.Round;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {

    Optional<Round> findByIdAndContentId(Long roundId, Long contentId);
}
