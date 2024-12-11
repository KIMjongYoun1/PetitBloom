package PetitBloom.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProfileVO {

    private String userId;      // 사용자 ID
    private String username;    // 사용자 이름
    private String email;       // 이메일
    private String password;    // 비밀번호
    private String createdAt;   // 계정 생성 일시
    private String profileImageUrl; // 프로필 이미지 URL (선택 사항)
    private List<PostVO> posts = new ArrayList<>(); // 사용자의 게시글 목록
    private int postCount;      // 작성한 게시글 수
    private Long totalLikes;    // 총 좋아요 수
}
