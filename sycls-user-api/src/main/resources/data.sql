-- 카테고리
insert into categories(name, status, created_at, updated_at)
values ('카테고리1', 'ENABLE', now(), now());

-- 공연
insert into contents (description, main_image_path, title,
                      category_id, created_at, updated_at)
values ('공연1', '/mainImagePath', 'title1', 1, now(), now());
-- 공연장
insert into places(location, max_seat, name, status, created_at, updated_at)
values ('locationPlace1', 30, '공연장1', 'ENABLE', now(), now());

-- 회차
insert into rounds(sequence, status, content_id, place_id, end_time, start_date, start_time)
values (1, 'AVAILABLE', 1, 1, now(), now(), now());

-- 좌석
insert into seats(code, status, place_id, created_at, updated_at)
values ('A1', 'ENABLE', 1, now(), now());
insert into seats(code, status, place_id, created_at, updated_at)
values ('A2', 'ENABLE', 1, now(), now());

-- 좌석 등급
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 1);
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 2);

-- 유저
insert into users(email, username, nickname, password, phone_number, status, type, role, created_at,
                  updated_at)
values ('test@gmail.com', '홍길동', '길동이', 'Abcdefg123!', '01012345678', 'ACTIVE', 'COMMON', 'USER',
        now(), now());

-- 리뷰
insert into reviews(comment, rating, status, user_id, content_id, created_at, updated_at)
values ('댓글', 5, 'ENABLE', 1, 1, now(), now());