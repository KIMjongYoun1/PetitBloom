package PetitBloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import PetitBloom.bean.GardenVO;

@Mapper
public interface GardenDAO {
	
	List<GardenVO> findAllPosts();
	
    // 좋아요 추가
    void addLike(@Param("userId") String userId, @Param("postId") Long postId);

    // 좋아요 삭제
    void removeLike(@Param("userId") String userId, @Param("postId") Long postId);
    
    // 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
    boolean checkLike(@Param("userId") String userId, @Param("postId") Long postId);
    
    // 게시글의 좋아요 수 증가
    void increaseLikeCount(Long postId);

    // 게시글의 좋아요 수 감소
    void decreaseLikeCount(Long postId);
    
    // 특정 게시글의 좋아요 개수 조회
    Long countLikes(@Param("postId") Long postId);
}
