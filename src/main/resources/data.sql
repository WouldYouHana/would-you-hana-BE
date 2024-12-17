INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-01-01", "2024-10-21 10:00:00", "one@example.com", 0, "M", "서울시 광진구", "김하나", "하나", "root1234", "010-1234-5678");
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-02-01", "2024-10-21 10:00:00", "two@example.com", 0, "M", "서울시 중구", "김은행", "은행", "root5678", "010-5678-1234");
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-03-01", "2024-10-21 10:00:00", "three@example.com", 0, "M", "서울시 마포구", "김우주", "우주", "root1234", "010-3456-7890");

INSERT INTO category (name) values ("예금");
INSERT INTO category (name) values ("적금");
INSERT INTO category (name) values ("대출");

INSERT INTO banker (branch_name, email, name, password, content, location) values ("성수역지점", "banker1@example.com", "문보경", "root1234!", "이것은 행원 소개", "서울시 광진구");
INSERT INTO banker (branch_name, email, name, password, content, location) values ("서울숲지점", "banker2@example.com", "홍창기", "root1234!","이것은 행원 소개", "서울시 성동구");
INSERT INTO banker (branch_name, email, name, password, content, location) values ("구의역지점", "banker3@example.com", "박해민", "root1234!","이것은 행원 소개", "서울시 광진구");

INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (1, 1, "안녕하세요!", "안녕하세요, 처음 가입했습니다. 반가워요!!", "서울시 성동구", "2024-12-17 10:00:00", null, 0, 0, 0);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 2, "주택청약종합저축에 대해 여쭈어볼게 있습니다.", "이번에 주택청약종합저축 월 납입 한도가 25만원으로 올랐잖아요? ... ", "서울시 성동구", "2024-12-17 14:00:00", null, 0, 0, 5);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (3, 3, "제 대략적인 대출 한도가 궁금합니다.", "재직중인 26살 남자입니다. ...", "서울시 성동구", "2024-10-23 15:00:00", null, 0, 0, 3);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 2, "주택청약종합저축에 대해 여쭈어볼게 있습니다.2", "이번에 주택청약종합저축 월 납입 한도가 25만원으로 올랐잖아요? ... ", "서울시 성동구", "2024-12-17 14:00:00", null, 0, 0, 5);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 2, "주택청약종합저축에 대해 여쭈어볼게 있습니다.3", "이번에 주택청약종합저축 월 납입 한도가 25만원으로 올랐잖아요? ... ", "서울시 광진구", "2024-12-17 14:00:00", null, 0, 0, 8);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 2, "주택청약종합저축에 대해 여쭈어볼게 있습니다.4", "이번에 주택청약종합저축 월 납입 한도가 25만원으로 올랐잖아요? ... ", "서울시 성동구", "2024-12-17 14:00:00", null, 0, 0, 1);


INSERT INTO answer (banker_id, question_id, content, created_at, updated_at) VALUES (1, 1, 'This is an answer', "2024-10-21 10:00:00", "2024-10-21 10:00:00");

INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 1,"반갑습니다!","2024-10-21 10:05:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 1,"어서오세요!","2024-10-21 10:07:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 2,"저도 헷갈리네요...","2024-10-21 14:05:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 2,"자세히 알려주실 분 계신가요?","2024-10-21 14:15:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 3,"실례지만 대략적인 연봉이 어떻게 되시나요?","2024-10-21 15:23:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 3,"저도 궁금합니다!","2024-10-21 15:31:00" );

INSERT INTO customer_roles (customer_id, roles)VALUES (1, 'USER');
INSERT INTO customer_roles (customer_id, roles)VALUES (2, 'USER');
INSERT INTO customer_roles (customer_id, roles)VALUES (3, 'USER');

INSERT INTO banker_roles (banker_id, roles)VALUES (1, 'BANKER');
INSERT INTO banker_roles (banker_id, roles)VALUES (2, 'BANKER');
INSERT INTO banker_roles (banker_id, roles)VALUES (3, 'BANKER');