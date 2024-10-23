INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-01-01", "2024-10-21 10:00:00", "one@example.com", 0, "M", "서울시 성동구", "김하나", "하나", "root1234", "010-1234-5678");
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-02-01", "2024-10-21 10:00:00", "two@example.com", 0, "M", "서울시 중구", "김은행", "은행", "root5678", "010-5678-1234");
INSERT INTO customer (birth_date, created_at, email, experience_points, gender, location, name, nickname, password, phone) values ("2000-03-01", "2024-10-21 10:00:00", "three@example.com", 0, "M", "서울시 마포구", "김우주", "우주", "root1234", "010-3456-7890");

INSERT INTO category (name) values ("예금");
INSERT INTO category (name) values ("적금");
INSERT INTO category (name) values ("대출");

INSERT INTO banker (branch_name, email, name, password) values ("성수역지점", "banker1@example.com", "문보경", "root1234!");
INSERT INTO banker (branch_name, email, name, password) values ("서울숲지점", "banker2@example.com", "홍창기", "root1234!");
INSERT INTO banker (branch_name, email, name, password) values ("구의역지점", "banker3@example.com", "박해민", "root1234!");
