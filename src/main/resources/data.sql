INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2000-01-01", "2024-10-21 10:00:00", "one@example.com", 0, "M", "성동구", "김하나", "달달하나", "root1234", "010-1234-5678", null);
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2000-02-01", "2024-10-21 10:00:00", "two@example.com", 0, "M", "광진구", "김우주", "별돌이", "root5678", "010-5678-1234", null);
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2000-03-01", "2024-10-21 10:00:00", "three@example.com", 0, "M", "마포구", "김은행", "별송이", "root1234", "010-3456-7890", null);
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2001-05-04", "2024-10-21 10:00:00", "four@example.com", 0, "M", "성동구", "안유진", "안유진", "root1234", "010-1234-5678", null);
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2001-09-13", "2024-10-21 10:00:00", "five@example.com", 0, "M", "성동구", "손흥민", "국가대표7번", "root1234", "010-1234-5678", null);
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone, filepath) values ("2001-09-13", "2024-10-21 10:00:00", "six@example.com", 0, "M", "광진구", "김가을", "가을선배", "root1234", "010-1234-5678", null);

-- Location
insert into location(customer_id, location) values (1, "성동구");
insert into location(customer_id, location) values (1, "광진구");

-- specialization 테이블에 데이터 삽입
INSERT INTO specialization (name) VALUES ('예금/적금');
INSERT INTO specialization (name) VALUES ('이체');
INSERT INTO specialization (name) VALUES ('자산관리');
INSERT INTO specialization (name) VALUES ('퇴직연금');
INSERT INTO specialization (name) VALUES ('펀드');
INSERT INTO specialization (name) VALUES ('신탁');
INSERT INTO specialization (name) VALUES ('ISA');
INSERT INTO specialization (name) VALUES ('전자금융');
INSERT INTO specialization (name) VALUES ('대출');
INSERT INTO specialization (name) VALUES ('외환');
INSERT INTO specialization (name) VALUES ('보험');
INSERT INTO specialization (name) VALUES ('카드');
INSERT INTO specialization (name) VALUES ('기타');

-- QnACategories
INSERT INTO category (name) values ("예금/적금"); -- 1
INSERT INTO category (name) values ("이체");
INSERT INTO category (name) values ("자산관리");
INSERT INTO category (name) values ("퇴직연금");
INSERT INTO category (name) values ("펀드");
INSERT INTO category (name) values ("신탁");
INSERT INTO category (name) values ("ISA");
INSERT INTO category (name) values ("전자금융"); -- 8
INSERT INTO category (name) values ("대출");
INSERT INTO category (name) values ("외환");
INSERT INTO category (name) values ("보험");
INSERT INTO category (name) values ("카드");
INSERT INTO category (name) values ("기타");
-- CommunityCategories
INSERT INTO category (name) values ("저축"); -- 14
INSERT INTO category (name) values ("소비");
INSERT INTO category (name) values ("주식");
INSERT INTO category (name) values ("청약");
INSERT INTO category (name) values ("연말정산"); -- 18
INSERT INTO category (name) values ("절약");
INSERT INTO category (name) values ("노후 대비");
INSERT INTO category (name) values ("신용점수 올리기"); --21
INSERT INTO category (name) values ("세금/납부");
INSERT INTO category (name) values ("학자금대출");

INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("성수역지점", "banker1@example.com", "문보경", "root1234!", "이것은 행원 소개", "광진구", 12);
INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("서울숲지점", "banker2@example.com", "홍창기", "root1234!","이것은 행원 소개", "성동구", 0);
INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("구의역지점", "banker3@example.com", "박해민", "root1234!","이것은 행원 소개", "광진구", 0);
INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("구의역지점", "banker4@example.com", "박해민2", "root1234!","이것은 행원 소개", "광진구", 0);
INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("구의역지점", "banker5@example.com", "박해민3", "root1234!","이것은 행원 소개", "광진구", 0);
INSERT INTO banker (branch_name, email, name, password, content, location, view_count) values ("구의역지점", "banker6@example.com", "박해민4", "root1234!","이것은 행원 소개", "광진구", 0);


INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (1, 1);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (1, 2);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (1, 4);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (2, 3);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (2, 5);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (2, 2);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (3, 6);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (4, 1);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (4, 2);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (4, 4);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (5, 1);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (5, 2);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (5, 4);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (6, 1);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (6, 2);
INSERT INTO banker_specialization (banker_id, specialization_id) VALUES (6, 4);


INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (1, 1, "안녕하세요!", "안녕하세요, 처음 가입했습니다. 반가워요!!", "성동구", "2024-12-26 10:00:00", null, 3, 0, 5524);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 1, "주택청약종합저축에 대해 여쭈어볼게 있습니다.", "이번에 주택청약종합저축 월 납입 한도가 25만원으로 올랐잖아요? ... ", "성동구", "2024-12-26 14:00:00", null, 243, 62, 2314);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (3, 9, "제 대략적인 대출 한도가 궁금합니다.", "재직중인 26살 남자입니다. ...", "성동구", "2024-12-26 15:00:00", null, 132, 0, 3551);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 4, "ISA가 무엇인가요?", "그냥 저축 상품과는 어떤 점이 다른가요?? 주변에서 추천하던데 무엇인지 몰라서 여쭙습니다!!", "성동구", "2024-12-26 16:25:00", null, 255, 75, 6854);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (3, 5, "미성년자 펀드 가입 관련", "미성년자는 인터넷이나 하나원큐 앱으로 펀드 가입이 가능한지 궁금합니다.", "성동구", "2024-12-22 16:25:00", null, 315, 24, 4426);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 6, "분할매수형 ETF", "투자 시에 기준을 정한다고 하는데, 그 기준에는 어떤 것들이 있고 기준을 달성하면 바로 환매가 가능한가요? 자세히 설명 부탁드립니다.", "성동구", "2024-12-25 13:49:00", null, 201, 36, 5225);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (3, 8, "마이데이터에 연결된 자산 관련", "마이데이터에 다른 금융 자산도 연결하여 조회하고 있는데요, 갑자기 타 은행 계좌 하나가 조회가 되지 않습니다. 이렇게 갑자기 사라지기도 하나요??", "성동구", "2024-12-22 16:25:00", null, 1265, 102, 12885);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (1, 10, "하나은행 환전 수수료 우대 자격", "하나은행 회원의 어느 등급부터 우대가 들어가는지 궁금합니다!", "성동구", "2024-12-23 09:03:00", null, 521, 42, 5651);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (1, 10, "외국인이 인터넷뱅킹 통해 해외 송금", "외국인도 인터넷뱅킹으로 해외 송금 가능하나요?", "성동구", "2024-12-24 19:03:00", null, 309, 12, 2655);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (1, 10, "하나은행 환전 수수료 우대 자격", "하나은행 회원의 어느 등급부터 우대가 들어가는지 궁금합니다!", "성동구", "2024-12-23 09:03:00", null, 521, 42, 5651);

INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 1, "계좌 비밀번호 변경", "비밀번호 변경 희망하는데, 하나원큐 앱에서도 가능한가요? 아니면 직접 지점 방문이 필요할까요?", "광진구", "2024-12-17 14:00:00", null, 1205,87, 7270);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (2, 9, "대출 원금/이자 상환 날짜", "대출 원금이나 이자를 상환해야 하는 날짜가 은행 영업일이 아닌 경우에는 어떻게 되나요? 상환 기일이 다음 영업일로 미루어지나요??", "광진구", "2024-12-19 18:00:00", null, 775, 80, 6201);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (3, 8, "장기 미사용 이체 제한 거래 정지??", "이게 뭐죠? 정지를 풀려면 어떻게 해야 되는지 알려주세요.", "광진구", "2024-12-23 08:21:00", null, 775, 80, 6201);
INSERT INTO question (customer_id, category_id, title, content, location, created_at, updated_at, like_count, scrap_count, view_count) values (6, 2, "예약이체 설정", "예약이체를 설정했는데, 이체가 처리되는 시간대가 궁금합니다.", "광진구", "2024-12-26 08:02:00", null, 142, 18, 1285);

INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (1, "다들 적금 얼마나 넣으셨나요?", 14, "성동구", "궁금합니다!", "2024-12-18 15:30:00", null, 335, 0, 4522 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (1, "여러분들 올해 연말정산 어떨 것 같으신가요?", 18, "성동구", "저는 오히려 토해내야 될지도 모르겠는데요 ㅠㅠ", "2024-12-24 17:21:15", null, 461, 43, 5391 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (2, "이번달 외식비가 너무 많이 나온 것 같은데요..", 19, "성동구", "수입의 20%은 식비로 나가는 것 같습니다. 제가 좀 많이 나오는 편인가요?? 궁금합니다.", "2024-12-25 11:47:30", null, 775, 51, 8336 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (2, "하나은행에서 IRP 가입하신분??", 20, "성동구", "여기도 많이 계시겠죠??", "2024-12-20 19:25:00", null, 247, 21, 3376 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (3, "이번에 영화 하얼빈 나왔던데 볼만한가요", 15, "성동구", "보러가볼까 고민중입니다", "2024-12-24 18:52:00", null, 321, 20, 1632 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (2, "이율 센 정기예금 하나 추천 부탁드립니다", 14, "성동구", "가입하고 계신거 있으면 추천해주세요!!", "2024-12-25 20:11:00", null, 442, 50, 6220 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (3, "다들 예금 얼마나 하고 계시나요?", 16, "서초구", "궁금합니다!", "2024-12-20 15:30:00", null, 8, 0, 6 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (5, "자양동에서 가장 가까운 세무서가 어디죠?", 22, "광진구", "성동세무서인가요??", "2024-12-19 15:30:00", null, 7, 0, 0 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (4, "삼성전자 물리신 분 ㅠㅠ", 16, "성동구", "언제쯤 다시 나아질까요? ㅠㅠ 우울하네요", "2024-12-23 15:30:00", null, 574, 29, 5882 );
INSERT INTO post (customer_id, title, category_id, location, content, created_at, updated_at, like_count, scrap_count, view_count) values (2, "다들 예금 얼마나 하고 계시나요?", 15, "서초구", "궁금합니다!", "2024-12-19 15:30:00", null, 7, 0, 4 );



INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (2, 1, '반갑습니다! 달달하나 님!', "2024-12-21 10:00:00", "2024-12-21 10:00:00", 12);
INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (2, 2, '안녕하세요! 별돌이 님, 기존 주택청약종합저축 계좌에서도 상향된 한도가 적용됩니다! 감사합니다.', "2024-12-21 20:00:00", "2024-12-21 21:00:00", 15);
INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (2, 5, '안녕하세요! 별송이 님, 미성년자는 하나원큐 앱이나 인터넷으로 펀드 가입이 불가합니다. 가까운 지점 직접 방문하시면 친절히 도와드리겠습니다.', "2024-12-23 16:12:00", "2024-12-23 17:53:00", 10);
INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (3, 12, '안녕하세요! 별송이 님, 상환 기일이 은행 영업일이 아닌 경우 다음 영업일로 이연됩니다.', "2024-12-21 16:12:00", "2024-12-21 17:53:00", 25);
INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (1, 9, '안녕하세요! 달달하나 님, 사전에 영업점 방문하셔서 거래외국환은행을 지정 등록하신 경우에 인터넷뱅킹으로 해외 송금이 가능합니다. 가까운 지점 직접 방문하시면 친절히 도와드리겠습니다. - 송금한도 : 국내보수임을 입증할 수 있는 금액 범위내에서 1회 미화 5만불 상당액 이내에서 송금 가능', "2024-12-23 16:12:00", "2024-12-23 17:53:00", 30);
--INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (2, 5, '안녕하세요! 별송이 님, 미성년자는 하나원큐 앱이나 인터넷으로 펀드 가입이 불가합니다. 가까운 지점 직접 방문하시면 친절히 도와드리겠습니다.', "2024-12-23 16:12:00", "2024-12-23 17:53:00", 10);
INSERT INTO answer (banker_id, question_id, content, created_at, updated_at, good_count) VALUES (2, 14, '안녕하세요! 가을선배 님, 일반 예약이체의 경우 9-10시, 12-13시, 15-16시, 18-19시에 이체가 수행되도록 지정하실 수 있습니다. 요청하신 날짜 및 시간에 1회만 이체 의뢰가 진행되므로 출금계좌의 잔액이 충분한지 확인해 주시면 감사하겠습니다!', "2024-12-26 10:09:00", "2024-12-26 10:12:00", 8);

INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 1,"반갑습니다!","2024-12-26 10:05:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 1,"어서오세요!","2024-12-26 19:07:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 2,"저도 헷갈리네요...","2024-12-26 14:05:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 2,"자세히 알려주실 분 계신가요?","2024-12-21 14:15:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (1, 3,"실례지만 대략적인 연봉이 어떻게 되시나요?","2024-12-21 15:23:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (2, 3,"저도 궁금합니다!","2024-12-21 15:31:00" );
INSERT INTO comment (customer_id, question_id, content, created_at) values (4, 4,"그니까요~ 저도 알고 싶습니다!!","2024-12-22 18:13:00" );

INSERT INTO comment (customer_id, post_id, content, created_at) values (2, 1,"달에 20만원씩 하려고 노력중입니다..ㅎㅎ","2024-12-18 10:05:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (4, 1,"저는 저축이 너무 어렵네요 ㅠㅠ","2024-12-19 10:07:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (3, 2,"망했습니다 저도 ㅠㅠ","2024-12-24 14:05:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (2, 2,"돌려받으면 다행이죠~","2024-12-25 09:15:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (4, 3,"저는 더 나와요 ㅋㅋㅋㅋ 배달음식이 너무 좋아요","2024-12-26 10:23:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (5, 3,"저도 그정도인것 같은데요...?","2024-12-26 10:25:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (1, 9,"75층입니다.. 슬픕니다..","2024-12-24 15:06:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (2, 9,"힘내세요~ ㅎㅎ","2024-12-24 21:36:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (5, 9,"답이 없어요!!","2024-12-25 17:31:00" );
INSERT INTO comment (customer_id, post_id, content, created_at) values (4, 8,"그럴거에요!","2024-12-25 17:31:00" );

INSERT INTO customer_roles (customer_id, roles)VALUES (1, 'USER');
INSERT INTO customer_roles (customer_id, roles)VALUES (2, 'USER');
INSERT INTO customer_roles (customer_id, roles)VALUES (3, 'USER');

INSERT INTO banker_roles (banker_id, roles)VALUES (1, 'BANKER');
INSERT INTO banker_roles (banker_id, roles)VALUES (2, 'BANKER');
INSERT INTO banker_roles (banker_id, roles)VALUES (3, 'BANKER');

INSERT INTO branch_location_mapping(branch_name, location)VALUES ("성수역지점", "성동구");
INSERT INTO branch_location_mapping(branch_name, location)VALUES ("서울숲지점", "성동구");
INSERT INTO branch_location_mapping(branch_name, location)VALUES ("구의역지점", "광진구");

insert into scrap_question(customer_id, question_id) values (1, 1);
insert into scrap_question(customer_id, question_id) values (1, 3);