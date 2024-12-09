package PetitBloom.mapper;

import org.apache.ibatis.annotations.Mapper;

import PetitBloom.bean.ProfileVO;

@Mapper
public interface ProfileMapper {
	
   
    // 사용자 정보 조회
    public ProfileVO getUserById(String id);

    // 새 사용자 등록
    public void insertUser(ProfileVO profile);
	
}
