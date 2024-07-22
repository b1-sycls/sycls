package com.b1.place;

import static com.b1.place.entity.QPlace.place;

import com.b1.place.dto.PlaceGetResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PlaceGetResponseDto> getAllPlaces(final String location, final String name,
            final Integer maxSeat, final Pageable pageable) {

        List<PlaceGetResponseDto> placeList = jpaQueryFactory
                .select(Projections.constructor
                        (PlaceGetResponseDto.class,
                                place.id,
                                place.location,
                                place.name,
                                place.maxSeat
                        )
                )
                .from(place)
                .where(
                        locationLike(location),
                        nameLike(name),
                        maxSeatEq(maxSeat)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(place.count())
                .from(place)
                .where()
                .fetch().get(0);

        return PageableExecutionUtils.getPage(placeList, pageable, () -> total);
    }

    private BooleanExpression locationLike(final String location) {
        return StringUtils.hasText(location) ? place.location.like(location) : null;
    }

    private BooleanExpression nameLike(final String name) {
        return StringUtils.hasText(name) ? place.name.like(name) : null;
    }

    private BooleanExpression maxSeatEq(final Integer maxSeat) {
        if (maxSeat != null) {
            return place.maxSeat.eq(maxSeat);
        } else {
            return null;
        }
    }

}
