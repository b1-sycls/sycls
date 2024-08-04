package com.b1.place;

import static com.b1.place.entity.QPlace.place;
import static com.b1.seat.entity.QSeat.seat;

import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.entity.PlaceStatus;
import com.b1.seat.entity.SeatStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
public class PlaceQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<PlaceGetResponseDto> getAllPlaces(
            final String location,
            final String name,
            final PlaceStatus status,
            final Pageable pageable
    ) {

        List<PlaceGetResponseDto> placeList = jpaQueryFactory
                .select(Projections.constructor
                        (PlaceGetResponseDto.class,
                                place.id,
                                place.location,
                                place.name,
                                place.maxSeat,
                                place.status
                        )
                )
                .from(place)
                .where(
                        locationLike(location),
                        nameLike(name),
                        statusEq(status)
                )
                .orderBy(place.status.desc(), place.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(place.count())
                .from(place)
                .where(
                        locationLike(location),
                        nameLike(name)
                );

        return PageableExecutionUtils.getPage(placeList, pageable, total::fetchOne);
    }

    /**
     * 총 좌석수 불러오기
     */
    public Long getSeatCount(final Long placeId) {
        return jpaQueryFactory
                .select(seat.count().coalesce(0L))
                .from(seat)
                .leftJoin(place).on(seat.place.id.eq(place.id))
                .where(
                        place.id.eq(placeId),
                        seat.status.eq(SeatStatus.ENABLE)
                )
                .fetchOne();
    }

    private BooleanExpression locationLike(final String location) {
        return StringUtils.hasText(location) ? place.location.contains(location) : null;
    }

    private BooleanExpression nameLike(final String name) {
        return StringUtils.hasText(name) ? place.name.contains(name) : null;
    }

    private BooleanExpression statusEq(final PlaceStatus status) {
        return status != null ? place.status.eq(status) : null;
    }
}
