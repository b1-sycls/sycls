package com.b1.datafaker;

import com.b1.category.CategoryRepository;
import com.b1.category.entity.Category;
import com.b1.content.ContentRepository;
import com.b1.content.entity.Content;
import com.b1.place.PlaceRepository;
import com.b1.place.entity.Place;
import com.b1.round.RoundRepository;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.b1.seat.SeatRepository;
import com.b1.seat.entity.Seat;
import com.b1.seatgrade.SeatGradeRepository;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatafakerHelper {

    private final Faker faker = new Faker();
    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;
    private final SeatRepository seatRepository;
    private final ContentRepository contentRepository;
    private final RoundRepository roundRepository;
    private final SeatGradeRepository seatGradeRepository;

    /**
     * 카테고리 더미데이터 생성
     */
    public Category addDummyCategory() {
        String name = faker.commerce().department();
        return categoryRepository.save(Category.addCategory(name));
    }

    public Place getDummyPlace(Long id) {
        return placeRepository.findById(id).orElse(null);
    }

    /**
     * 공연장 더미데이터 생성
     */
    public Place addDummyPlace() {
        String location = faker.address().fullAddress();
        Integer maxSeat = 10000;
        String name = faker.company().name();
        return placeRepository.save(Place.addPlace(location, maxSeat, name));
    }

    /**
     * 좌석 더미데이터 생성
     */
    public void addDummySeat(final Place place) {
        List<Seat> seatList = new ArrayList<>();

        char letter = 'A';
        int codeIndex = 1;

        for (int i = 0; i < 10000; i++) {
            String code = String.format("%c%d", letter, codeIndex);
            seatList.add(Seat.addSeat(code, place));

            codeIndex++;
            if (codeIndex > 1000) {
                codeIndex = 1;
                letter++;
                log.info("addDummySeat {} ", codeIndex);
            }
        }
        seatRepository.saveAll(seatList);
    }

    /**
     * 공연 더미 데이터 생성
     */
    public Content addDummyContent(final Category category) {
        String title = "dummyTitle";
        String description = "test";
        String path = "/sycls/content/main/17430a32-dd27-4b0e-adfe-acc0b8620123png";
        return contentRepository.save(Content.addContent(title, description, category, path));
    }

    public List<Content> getAllDummyContents() {
        return contentRepository.findAll();
    }

    /**
     * 회차 더미 데이터 생성
     */
    public void addDummyRound(Long placeId, List<Content> allDummyContents) {
        Place place = placeRepository.findById(placeId).orElse(null);

        List<Round> roundList = new ArrayList<>();
        for (Content allDummyContent : allDummyContents) {
            List<Round> allByContentId = roundRepository.findAllByContentId(allDummyContent.getId());
            if (allByContentId.isEmpty()) {
                for (int i = 1; i <= 5; i++) {
                    LocalDate startDate = LocalDate.now().plusDays(faker.number().numberBetween(1, 30));
                    LocalTime startTime = LocalTime
                            .of(faker.number().numberBetween(9, 22), faker.number().numberBetween(0, 59));
                    LocalTime endTime = startTime.plusHours(2);
                    RoundStatus status = RoundStatus.WAITING;
                    roundList.add(Round.addRound(i, startDate, startTime, endTime, status, allDummyContent, place));
                }
            }
        }
        roundRepository.saveAll(roundList);
    }

    /**
     * 좌석등급 더미 데이터 생성
     */
    public void addDummySeatGrade() {
        List<Seat> seats = seatRepository.findAll();
        List<Round> rounds = roundRepository.findAll();
        List<SeatGrade> seatGradeList = new ArrayList<>();

        for (Round round : rounds) {
            for (Seat seat : seats) {
                SeatGradeType grade = SeatGradeType.S;
                Integer price = 10000;
                seatGradeList.add(SeatGrade.addSeatGrade(grade, price, seat, round));
            }
        }
        seatGradeRepository.saveAll(seatGradeList);
    }

}
