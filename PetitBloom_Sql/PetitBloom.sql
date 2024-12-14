CREATE TABLE users (
    id VARCHAR(50) NOT NULL,           -- 사용자 아이디 (사용자가 입력)
    username VARCHAR(50) NOT NULL,     -- 사용자 이름
    email VARCHAR(100) NOT NULL,       -- 이메일
    password VARCHAR(100) NOT NULL,    -- 비밀번호
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 계정 생성일
    PRIMARY KEY (id)                  -- id를 Primary Key로 설정 (사용자가 입력한 값이 유일해야 함)
);

CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,        -- 게시글 ID (자동 증가)
    user_id VARCHAR(50) NOT NULL,             -- 작성자 ID (사용자 ID 참조)
    title VARCHAR(255) NOT NULL,              -- 게시글 제목
    content TEXT NOT NULL,                    -- 게시글 내용
    image_url VARCHAR(255),                   -- 이미지 URL (선택 사항)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 시간
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 수정 시간
);


CREATE TABLE post_likes (
    user_id VARCHAR(255) NOT NULL,    -- 사용자 ID
    post_id BIGINT NOT NULL,          -- 게시글 ID
    PRIMARY KEY (user_id, post_id)    -- user_id와 post_id의 조합을 Primary Key로 설정
);


CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,           -- 댓글 ID (자동 증가)
    post_id INT NOT NULL,                        -- 게시글 ID (posts 테이블과 연관, 제약 조건 없음)
    user_id VARCHAR(50) NOT NULL,                -- 댓글 작성자 ID
    content TEXT NOT NULL,                       -- 댓글 내용
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 댓글 생성 시간
);



ALTER TABLE users
CHANGE COLUMN id user_id VARCHAR(50) NOT NULL;

ALTER TABLE posts 
add COLUMN like_count INT DEFAULT 0;

INSERT INTO posts (user_id, title, content, image_url)
VALUES ('test_user', 'First Post', 'This is the content of the first post.', 'http://example.com/image.jpg');

SELECT * FROM users u ;

SELECT * FROM posts p ;

SELECT * FROM post_likes pl ;

SELECT * FROM  comments;

ALTER TABLE posts
ADD COLUMN thumbnail VARCHAR(255) AFTER image_url;

ALTER TABLE posts ADD COLUMN is_featured BOOLEAN DEFAULT FALSE;

DELETE FROM post_likes;



