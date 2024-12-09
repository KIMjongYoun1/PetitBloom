CREATE TABLE users (
    id VARCHAR(50) NOT NULL,           -- 사용자 아이디 (사용자가 입력)
    username VARCHAR(50) NOT NULL,     -- 사용자 이름
    email VARCHAR(100) NOT NULL,       -- 이메일
    password VARCHAR(100) NOT NULL,    -- 비밀번호
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 계정 생성일
    PRIMARY KEY (id)                  -- id를 Primary Key로 설정 (사용자가 입력한 값이 유일해야 함)
);



SELECT * FROM users u ;

