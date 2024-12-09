package PetitBloom.bean;

import lombok.Data;

@Data
public class ProfileVO {

	private String id; // 사용자 ID
	private String username; // 사용자 이름
	private String email; // 이메일
	private String password; // 비밀번호
	private String createdAt; // 계정 생성 일시

}
