package com.b1.content;

import com.b1.S3.S3Uploader;
import com.b1.category.CategoryAdapter;
import com.b1.category.entity.Category;
import com.b1.content.dto.ContentAddRequestDto;
import com.b1.content.dto.RoundInfoDto;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentDetailImage;
import com.b1.content.entity.Round;
import com.b1.place.PlaceAdapter;
import com.b1.place.entity.Place;
import com.b1.seat.SeatAdapter;
import com.b1.seat.SeatGradeAdapter;
import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatGrade;
import com.b1.seat.entity.SeatGradeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "Content Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentAdapter contentAdapter;
    private final CategoryAdapter categoryAdapter;
    private final PlaceAdapter placeAdapter;
    private final SeatAdapter seatAdapter;
    private final SeatGradeAdapter seatGradeAdapter;
    private final S3Uploader s3Uploader;

    public void addContent(ContentAddRequestDto requestDto, MultipartFile mainImage,
            MultipartFile[] detailImages) {

        Category category = categoryAdapter.findById(requestDto.categoryId());

        Content content = Content.addContent(
                requestDto.title(),
                requestDto.description(),
                category
        );

        List<Round> roundList = new ArrayList<>();
        List<SeatGrade> seatGradeList = new ArrayList<>();

        for (RoundInfoDto infoDto : requestDto.roundInfoDtoList()) {
            Place place = placeAdapter.getPlace(infoDto.placeId());

            Round round = Round.addRound(
                    infoDto.sequence(),
                    infoDto.startDate(),
                    infoDto.startTime(),
                    infoDto.endTime(),
                    infoDto.status(),
                    content,
                    place
            );

            roundList.add(round);

            Set<Seat> seatSet = seatAdapter.getAllSeatByPlaceId(place.getId());

            if (infoDto.vipSeatList() != null) {
                createAndGetSeatGradeList(infoDto.vipSeatList(), infoDto.vipPrice(), seatSet, round,
                        seatGradeList, SeatGradeType.VIP);
            }

            if (infoDto.royalList() != null) {
                createAndGetSeatGradeList(infoDto.royalList(), infoDto.royalPrice(), seatSet, round,
                        seatGradeList, SeatGradeType.ROYAL);
            }

            if (infoDto.superiorList() != null) {
                createAndGetSeatGradeList(infoDto.superiorList(), infoDto.royalPrice(), seatSet,
                        round, seatGradeList, SeatGradeType.SUPERIOR);
            }

            if (infoDto.aGradeList() != null) {
                createAndGetSeatGradeList(infoDto.aGradeList(), infoDto.aGradePrice(), seatSet,
                        round, seatGradeList, SeatGradeType.A_GRADE);
            }
        }

        content.addRoundList(roundList);

        String mainImagePath = s3Uploader.saveMainImage(mainImage);

        content.addMainImagePath(mainImagePath);

        if (detailImages == null) { //TODO 프론트에서 초기화 시켜줄경우 isEmpty 로 변경 가능
            contentAdapter.saveContent(content);
            seatGradeAdapter.saveAllSeatGrade(seatGradeList);
            return;
        }

        List<String> detailImageList = s3Uploader.saveDetailImage(detailImages);

        List<ContentDetailImage> contentDetailImageList = new ArrayList<>();

        for (String detailImagePath : detailImageList) {
            ContentDetailImage contentDetailImage = ContentDetailImage.addContentDetailImage(
                    detailImagePath,
                    content
            );

            contentDetailImageList.add(contentDetailImage);
        }

        content.addContentDetailImageList(contentDetailImageList);

        contentAdapter.saveContent(content);
        seatGradeAdapter.saveAllSeatGrade(seatGradeList);
    }

    private void createAndGetSeatGradeList(List<Long> idList, Integer price, Set<Seat> seatSet,
            Round round, List<SeatGrade> seatGradeList, SeatGradeType type) {

        Set<Long> idSet = new HashSet<>(idList);

        for (Seat seat : seatSet) {
            if (idSet.contains(seat.getId())) {
                SeatGrade seatGrade = SeatGrade.addSeatGrade(
                        type,
                        price,
                        seat,
                        round
                );

                seatGradeList.add(seatGrade);
            }
        }
    }
}
