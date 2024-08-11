package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.round.dto.ContentAndRoundGetResponseDto;
import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSimpleUserResponseDto;
import com.b1.s3.S3Util;
import com.b1.util.PageUtil;
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

    /**
     * 회차 단일 상세 조회
     */
    @Transactional(readOnly = true)
    public RoundDetailResponseDto getRoundDetail(final Long roundId) {

        RoundDetailInfoUserResponseDto responseDto = roundHelper.getRoundDetail(roundId);

        if (responseDto == null) {
            return null;
        }

        responseDto.updateMainImagePath(
                S3Util.makeResponseImageDir(responseDto.getMainImagePath()));

        return RoundDetailResponseDto.of(responseDto);
    }

    /**
     * 회차 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<RoundSimpleUserResponseDto> getAllRounds(final Long contentId,
            final int page, final String sortProperty, final String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);

        PageUtil.checkPageNumber(page);

        Pageable pageable = PageRequest.of(page - 1, 10, sort);

        Page<RoundSimpleUserResponseDto> pageResponseDto = roundHelper.getAllSimpleRoundsForUser(
                contentId, pageable);

        return PageResponseDto.of(pageResponseDto);
    }

    /**
     * 회차 단일 간단 조회
     */
    @Transactional(readOnly = true)
    public ContentAndRoundGetResponseDto getRoundSimple(final Long roundId) {
        return roundHelper.getRoundSimple(roundId);
    }
}
