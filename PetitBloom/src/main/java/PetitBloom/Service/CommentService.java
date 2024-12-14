package PetitBloom.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import PetitBloom.bean.CommentVO;
import PetitBloom.mapper.CommentDAO;

@Service
public class CommentService {

	private final CommentDAO commentDAO;

	public CommentService(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}
	
    // 특정 게시글의 댓글 목록 가져오기
    public List<CommentVO> getCommentsByPostId(Long postId) {
        return commentDAO.findCommentsByPostId(postId);
    }
    
   
	// 댓글 추가
	public void addComment(CommentVO comment) {
		commentDAO.insertComment(comment);
	}



}
