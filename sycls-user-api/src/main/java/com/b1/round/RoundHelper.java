package com.b1.round;

import com.b1.round.dto.RoundInfoGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Round Helper")
@Component
@RequiredArgsConstructor
public class RoundHelper {

    private final RoundQueryRepository queryRepository;

    public List<RoundInfoGetUserResponseDto> getAllRoundsInfoByContentId(Long contentId) {
        return queryRepository.getAllRoundsInfoByContentIdForUser(contentId);
    }
}
