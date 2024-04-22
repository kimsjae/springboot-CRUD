package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;

// GetMapping
    // 메인페이지, 글목록보기
    @GetMapping("/" )
    public ResponseEntity<?> index() {
        List<BoardResponse.MainDTO> respDTO = boardService.글목록조회();
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 글상세보기 이동
    @GetMapping("/api/boards/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.글상세보기(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 글수정하기 화면 이동
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id) {
        Board board = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil(board));
    }

// PostMapping
    // 글쓰기
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(board));
    }

    // 글수정하기
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil(board));
    }

// DeleteMapping
    // 글삭제하기
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }
}
