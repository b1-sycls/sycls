package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSimpleUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Round Service")
@Service
@RequiredArgsConstructor
@Transactional
public class RoundService {

    private final RoundHelper roundHelper;

    @Transactional(readOnly = true)
    public RoundDetailResponseDto getRound(Long roundId) {

        RoundDetailInfoUserResponseDto responseDto = roundHelper.getRoundDetail(roundId);

        if (responseDto == null) {
            return null;
        }

        return RoundDetailResponseDto.of(responseDto);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<RoundSimpleUserResponseDto> getAllRounds(Long contentId, int page,
            String sortProperty, String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);

        Pageable pageable = PageRequest.of(page - 1, 10, sort);

        Page<RoundSimpleUserResponseDto> pageResponseDto = roundHelper.getAllSimpleRoundsForUser(
                contentId, pageable);

        return PageResponseDto.of(pageResponseDto);
    }
}
