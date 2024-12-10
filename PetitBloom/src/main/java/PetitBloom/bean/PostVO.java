package PetitBloom.bean;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostVO {

    private Long id;           // 게시글 ID (자동 증가)
    private String userId;     // 사용자 ID
    private String title;      // 게시글 제목
    private String content;    // 게시글 내용
    private String imageUrl;   // 이미지 URL
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간

}
