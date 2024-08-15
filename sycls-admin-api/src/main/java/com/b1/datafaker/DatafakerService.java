package com.b1.datafaker;

import com.b1.content.entity.Content;
import com.b1.place.entity.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "datafaker service")
@Service
@Transactional
@RequiredArgsConstructor
public class DatafakerService {

    private final DatafakerHelper datafakerHelper;

    /**
     * 더미데이터 생성
     *
     * @return
     */
    public Long addDummyDataPlace() {
        //공연장 생성
        log.info("공연장 생성");
        Place place = datafakerHelper.addDummyPlace();
        log.info("공연장 완료");
        return place.getId();
    }

    public void addDummyDataSeat(Long placeId) {

        Place dummyPlace = datafakerHelper.getDummyPlace(placeId);
        log.info("seat 생성 완료");
        datafakerHelper.addDummySeat(dummyPlace);
        log.info("seat 생성 완료");
    }

    public void addDummyDataRound(Long placeId) {
        log.info("round 생성 준비");
        List<Content> allDummyContents = datafakerHelper.getAllDummyContents();
        datafakerHelper.addDummyRound(placeId, allDummyContents);
        log.info("round 생성 완료");
    }

    public void addDummyDataSeatGrade(Long placeId) {
        log.info("addDummyDataSeatGrade 생성 시작");
        datafakerHelper.addDummySeatGrade(placeId);
        log.info("addDummyDataSeatGrade 생성 완료");
    }
}
