package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

// PostMapping
    // 댓글 쓰기
    @PostMapping("/api/replies")
    public String save(ReplyRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글쓰기(reqDTO, sessionUser);

        return "redirect:/board/" + reqDTO.getBoardId();
    }

// DeleteMapping
    // 댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public String delete(@PathVariable Integer boardId, @PathVariable Integer replyId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(replyId, sessionUser.getId());

        return "redirect:/board/" + boardId;
    }
}
