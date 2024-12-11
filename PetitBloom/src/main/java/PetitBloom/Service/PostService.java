package PetitBloom.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PetitBloom.bean.PostVO;
import PetitBloom.mapper.PostDAO;

@Service
public class PostService {

	private final PostDAO postDAO;

	@Autowired
	public PostService(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	// 게시글 ID로 게시글 가져오기
	public PostVO getPostById(Long id) {
		return postDAO.findPostById(id); // PostDAO에서 findPostById 메서드를 호출하여 게시글을 가져옴
	}

	// 사용자 ID로 게시글 목록을 조회
	public List<PostVO> getPostsByUser(String userId) {
		return postDAO.findPostsByUserId(userId);
	}

	// 게시글 저장
	public void createPost(PostVO post) {
		postDAO.savePost(post);
	}

	// 게시글 수정
	public void updatePost(PostVO post) {
		postDAO.updatePost(post);
	}

	// 게시글 삭제
	public void deletePost(Long id) {
		postDAO.deletePost(id);
	}

	// 특정 게시글의 좋아요 개수 조회
	public Long getLikeCount(Long postId) {
		return postDAO.countLikes(postId);
	}

}
