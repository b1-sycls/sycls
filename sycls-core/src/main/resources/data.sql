-- 카테고리
insert into categories(name, status, created_at, updated_at)
values ('카테고리1', 'ENABLE', now(), now());

-- 공연
insert into contents (description, main_image_path, title,
                      category_id, created_at, updated_at, status)
values ('공연설명1', '/mainImagePath', 'title1', 1, now(), now(), 'HIDDEN');
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
insert into seats(code, status, place_id, created_at, updated_at)
values ('A3', 'ENABLE', 1, now(), now());
insert into seats(code, status, place_id, created_at, updated_at)
values ('A4', 'ENABLE', 1, now(), now());

-- 좌석 등급
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 1);
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 2);
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 3);
insert into seats_grade(grade, price, status, ticket_id, round_id, seat_id)
values ('VIP', 10000, 'ENABLE', null, 1, 4);

insert into users (nickname, phone_number, username, email, password, role, status, type,
                   created_at, updated_at)
values ('홍길동1', '01011111111', '홍길동1', 'test1@gmail.com',
        '$2a$10$aS.sVc714cphZeDH4781hO8Ak3b3TW43k3HpJe5Q.FoIziK1IxDvm',
        'USER', 'ACTIVE', 'COMMON', now(), now());

insert into users (nickname, phone_number, username, email, password, role, status, type,
                   created_at, updated_at)
values ('홍길동2', '01011111111', '홍길동2', 'test2@gmail.com',
        '$2a$10$aS.sVc714cphZeDH4781hO8Ak3b3TW43k3HpJe5Q.FoIziK1IxDvm',
        'USER', 'ACTIVE', 'COMMON', now(), now());