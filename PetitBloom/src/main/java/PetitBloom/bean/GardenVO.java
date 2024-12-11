package PetitBloom.bean;

import lombok.Data;

@Data
public class GardenVO {
	private Long id;              // 게시글 ID
    private String userId;        // 작성자 ID
    private String title;         // 게시글 제목
    private String content;       // 게시글 내용
    private String imageUrl;      // 게시글 이미지 URL
    private String thumbnail;     // 썸네일 URL
    private int likeCount;        // 좋아요 수
    private boolean likedByUser;  // 사용자가 좋아요를 눌렀는지 여부
}
