package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.exception.customexception.InvalidPageNumberException;
import com.b1.exception.errorcode.PageErrorCode;
import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSearchCondRequest;
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
    public PageResponseDto<RoundSimpleUserResponseDto> getAllRounds(
            RoundSearchCondRequest request) {

        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()),
                request.getSortProperty());

        if (request.getPage() <= 0) {
            log.error("페이지 값이 0이하 request : {}", request.getPage());
            throw new InvalidPageNumberException(PageErrorCode.INVALID_PAGE_NUMBER);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1, 10, sort);

        Page<RoundSimpleUserResponseDto> pageResponseDto = roundHelper.getAllSimpleRoundsForUser(
                request, pageable);

        return PageResponseDto.of(pageResponseDto);
    }
}
