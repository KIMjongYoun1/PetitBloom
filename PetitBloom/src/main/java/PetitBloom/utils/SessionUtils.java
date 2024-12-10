package PetitBloom.utils;

import PetitBloom.bean.ProfileVO;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

	public static ProfileVO getLoggedInUser(HttpSession session) {
		return (ProfileVO) session.getAttribute("loggedInUser");
	}
	
	 // 세션에서 로그인된 사용자 ID만 가져오는 메서드
    public static String getLoggedInUserId(HttpSession session) {
        ProfileVO user = getLoggedInUser(session); // 세션에서 ProfileVO 객체 가져오기
        if (user != null) {
            return user.getUserId(); // ProfileVO에서 userId 추출
        }
        return null; // 로그인되지 않은 경우
    }

}
