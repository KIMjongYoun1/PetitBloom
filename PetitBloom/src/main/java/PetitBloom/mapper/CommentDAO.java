package PetitBloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import PetitBloom.bean.CommentVO;

@Mapper
public interface CommentDAO {

	 // 특정 게시글의 댓글 목록 가져오기
    List<CommentVO> findCommentsByPostId(@Param("postId") Long postId);

	
    // 댓글 추가
    void insertComment(CommentVO comment);

    // 댓글 삭제
    Long deleteComment(Long id);
    
}
