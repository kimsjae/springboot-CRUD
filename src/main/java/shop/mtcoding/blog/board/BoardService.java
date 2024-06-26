package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardJpaRepository;

    // 글조회
    public Board 글조회(Integer boardId) {
        Board board = boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        return board;
    }

    // 글수정
    @Transactional
    public Board 글수정(Integer boardId, Integer sessionUserId, BoardRequest.UpdateDTO reqDTO) {
        // 조회 및 예외처리
        Board board = boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한처리
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // 글수정
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());

        return board;
    }

    // 글쓰기
    @Transactional
    public Board 글쓰기(BoardRequest.SaveDTO reqDTO, User sessionUser) {
        return boardJpaRepository.save(reqDTO.toEntity(sessionUser));
    }

    // 글삭제
    @Transactional
    public void 글삭제(Integer boardId, Integer sessionUserId) {
        Board board = boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다,"));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        boardJpaRepository.deleteById(boardId);
    }

    // 글목록조회
    public List<BoardResponse.MainDTO> 글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJpaRepository.findAll(sort);

        return boardList.stream().map(board -> new BoardResponse.MainDTO(board)).toList();
    }

    // 글상세보기
    public BoardResponse.DetailDTO 글상세보기(int boardId, User sessionUser) {
        Board board = boardJpaRepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        return new BoardResponse.DetailDTO(board, sessionUser);
    }
}
