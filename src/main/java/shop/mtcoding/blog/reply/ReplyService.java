package shop.mtcoding.blog.reply;

import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJpaRepository;
import shop.mtcoding.blog.user.User;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final BoardJpaRepository boardJpaRepository;
    private final ReplyJpaRepository replyJpaRepository;

    // 댓글쓰기
    @Transactional
    public void 댓글쓰기(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = boardJpaRepository.findById(reqDTO.getBoardId())
                .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없습니다."));

        Reply reply = reqDTO.toEntity(sessionUser, board);

        replyJpaRepository.save(reply);
    }

    // 댓글삭제
    @Transactional
    public void 댓글삭제(Integer replyId, Integer sessionUserId) {
        Reply reply = replyJpaRepository.findById(replyId)
                .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없어요."));

        if (reply.getUser().getId() != sessionUserId) {
            throw new Exception403("댓글을 삭제할 권한이 없어요.");
        }

        replyJpaRepository.deleteById(replyId);
    }
}
