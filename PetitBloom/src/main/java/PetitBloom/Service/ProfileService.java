package PetitBloom.Service;

import org.springframework.stereotype.Service;

import PetitBloom.bean.ProfileVO;
import PetitBloom.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;

    // 사용자 정보 조회
    public ProfileVO getUserById(String id) {
        return profileMapper.getUserById(id);  // String 타입으로 처리
    }

    // 새 사용자 등록
    public void createUser(ProfileVO profile) {
        profileMapper.insertUser(profile);
    }

    // 로그인 처리
    public ProfileVO authenticate(String id, String password) {
        ProfileVO user = profileMapper.getUserById(id);  // id는 String
        if (user != null && user.getPassword().equals(password)) {
            return user;  // 비밀번호 일치하면 사용자 정보 반환
        }
        return null;  // 비밀번호 불일치하거나 사용자가 없으면 null 반환
    }
}
