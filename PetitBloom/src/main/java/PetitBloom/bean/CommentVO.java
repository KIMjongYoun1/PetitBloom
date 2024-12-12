package PetitBloom.bean;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentVO {
	
    private Long id;              // 댓글 ID
    private Long postId;          // 게시글 ID
    private String userId;        // 작성자 ID
    private String content;       // 댓글 내용
    private LocalDateTime createdAt; // 작성 시간
    private LocalDateTime updatedAt; // 수정 시간
	
}
