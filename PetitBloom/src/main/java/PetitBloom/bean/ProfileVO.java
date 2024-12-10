package PetitBloom.bean;

import lombok.Data;

@Data
public class ProfileVO {

    private String userId;      // 사용자 ID
    private String username;    // 사용자 이름
    private String email;       // 이메일
    private String password;    // 비밀번호
    private String createdAt;   // 계정 생성 일시
    private String profileImageUrl; // 프로필 이미지 URL (선택 사항)
    private int followersCount; // 팔로워 수 (선택 사항)
    private int followingCount; // 팔로잉 수 (선택 사항)
}
