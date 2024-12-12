package PetitBloom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import PetitBloom.Service.CommentService;
import PetitBloom.bean.CommentVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/post/{postId}/comments")
    @ResponseBody
    public List<CommentVO> getComments(@PathVariable Long postId) {
    	
        return commentService.getCommentsByPostId(postId);
    }

    // 댓글 추가
    @PostMapping("/post/{postId}/comments")
    public String addComment(@PathVariable Long postId, 
                              @RequestParam String content, 
                              HttpSession session) {
        String userId = SessionUtils.getLoggedInUserId(session);
        if (userId == null) {
            return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        CommentVO comment = new CommentVO();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);

        commentService.addComment(comment);
        return "redirect:/post/" + postId; // 게시글 상세 페이지로 리다이렉트
    }


    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpSession session) {
        String userId = SessionUtils.getLoggedInUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        boolean isDeleted = commentService.deleteComment(id);
        if (isDeleted) {
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }
    }
}
