package PetitBloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import PetitBloom.bean.PostVO;

@Mapper
public interface PostDAO {

    // 게시글 ID로 게시글 가져오기
    PostVO findPostById(Long id);

    // 사용자 ID로 게시글 목록을 조회
    List<PostVO> findPostsByUserId(String userId);

    // 게시글 저장
    void savePost(PostVO post);

    // 게시글 수정
    void updatePost(PostVO post);

    // 게시글 삭제
    void deletePost(Long id);
    
    // 특정 게시글의 좋아요 개수 조회
    Long countLikes(Long postId);
    
    void incrementLikeCount(Long postId); // 좋아요 수 증가
    void decrementLikeCount(Long postId); // 좋아요 수 감소

    

    
}
