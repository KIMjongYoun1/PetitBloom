package PetitBloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import PetitBloom.bean.PostVO;
import PetitBloom.bean.ProfileVO;

@Mapper
public interface ProfileMapper {

	// 사용자 정보 조회
	public ProfileVO getUserById(String id);

	// 사용자 ID로 게시글 목록을 조회
	List<PostVO> findPostsByUserId(String userId);

	// 새 사용자 등록
	public void insertUser(ProfileVO profile);

}
