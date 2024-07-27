package com.b1.cast;

import com.b1.cast.dto.CastAddRequestDto;
import com.b1.cast.entity.Cast;
import com.b1.cast.entity.CastStatus;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.s3.S3Uploader;
import com.b1.s3.S3UrlPathType;
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

        castHelper.save(cast);
    }
}
