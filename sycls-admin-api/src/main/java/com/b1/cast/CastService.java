package com.b1.cast;

import com.b1.cast.dto.CastAddRequestDto;
import com.b1.cast.dto.CastUpdateRequestDto;
import com.b1.cast.entity.Cast;
import com.b1.cast.entity.CastStatus;
import com.b1.cast.entity.dto.CastGetAdminResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.s3.S3Uploader;
import com.b1.s3.S3UrlPathType;
import com.b1.s3.S3Util;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "Cast Service")
@Service
@RequiredArgsConstructor
@Transactional
public class CastService {

    private final CastHelper castHelper;
    private final RoundHelper roundHelper;
    private final S3Uploader s3Uploader;

    /**
     * 출연진 저장 로직
     */
    public void addCast(final CastAddRequestDto requestDto, final MultipartFile image) {

        Round round = roundHelper.findById(requestDto.roundId());

        String imagePath = s3Uploader.saveMainImage(image, S3UrlPathType.CONTENT_CAST_IMAGE_PATH);

        Cast cast = Cast.addCast(
                requestDto.name(),
                imagePath,
                CastStatus.SCHEDULED,
                round);

        castHelper.saveCast(cast);
    }

    /**
     * 출연진 정보 수정
     */
    public void updateCast(final Long castId, final CastUpdateRequestDto requestDto,
            final MultipartFile image) {

        Cast cast = castHelper.getCast(castId);

        if (!Objects.equals(cast.getRound().getId(), requestDto.roundId())) {
            Round round = roundHelper.findById(requestDto.roundId());
            cast.updateRoundForCast(round);
        }

        String imagePath = cast.getImagePath();

        if (image != null) {
            imagePath = s3Uploader.saveMainImage(image, S3UrlPathType.CONTENT_CAST_IMAGE_PATH);
        }

        cast.updateCast(requestDto.name(), imagePath, requestDto.status());
    }

    /**
     * 출연진 조회 기능
     */
    @Transactional(readOnly = true)
    public List<CastGetAdminResponseDto> getAllCastsByRoundId(final Long roundId) {

        List<CastGetAdminResponseDto> responseDto = castHelper.getAllCastsByRoundId(roundId);

        for (CastGetAdminResponseDto dto : responseDto) {
            dto.updateImagePath(S3Util.makeResponseImageDir(dto.getImagePath()));
        }

        return responseDto;
    }

    /**
     * 출연진 삭제 기능
     */
    public void deleteCast(final Long castId) {

        Cast cast = castHelper.getCast(castId);

        CastStatus.checkCanceled(cast.getStatus());

        cast.deleteCast();
    }
}
