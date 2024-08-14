package com.b1.datafaker;

import com.b1.category.entity.Category;
import com.b1.content.entity.Content;
import com.b1.place.entity.Place;
import com.b1.round.entity.Round;
import com.b1.seat.entity.Seat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DatafakerService {

    private final DatafakerHelper datafakerHelper;

    /**
     * 더미데이터 생성
     */
    public void addDummyData() {
        Category category = datafakerHelper.addDummyCategory();
        Place place = datafakerHelper.addDummyPlace();
        List<Seat> seatList = datafakerHelper.addDummySeat(place);
        Content content = datafakerHelper.addDummyContent(category);
        List<Round> roundList = datafakerHelper.addDummyRound(content, place);
        datafakerHelper.addDummySeatGrade(seatList, roundList);
    }
}
