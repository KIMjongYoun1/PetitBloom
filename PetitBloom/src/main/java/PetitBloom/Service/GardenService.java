package PetitBloom.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import PetitBloom.bean.GardenVO;
import PetitBloom.mapper.GardenDAO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GardenService {

    private final GardenDAO gardenDAO;
    
    public List<GardenVO> getAllPosts() {
        return gardenDAO.findAllPosts();
    }

    
    // 좋아요 추가
    public void addLike(String userId, Long postId) {
        if (!gardenDAO.checkLike(userId, postId)) {
            gardenDAO.addLike(userId, postId);
        }
    }

    // 좋아요 삭제
    public void removeLike(String userId, Long postId) {
        gardenDAO.removeLike(userId, postId);
    }

    // 특정 게시글의 좋아요 개수 조회
    public Long getLikeCount(Long postId) {
        return gardenDAO.countLikes(postId);
    }

    // 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
    public boolean checkLike(String userId, Long postId) {
        return gardenDAO.checkLike(userId, postId);
    }
}
