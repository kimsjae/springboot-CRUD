package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardNativeRepository boardNativeRepository;
    private final BoardPersistRepository boardPersistRepository;
    private final BoardRepository boardRepository;
    private final HttpSession session;
    private final BoardService boardService;

// GetMapping
    // 메인페이지, 글목록보기
    @GetMapping("/" )
    public String index(Model model) {
        List<Board> boardList = boardService.글목록조회();
        model.addAttribute("boardList", boardList);
        return "index";
    }

    // 글상세보기 이동
    @GetMapping("/api/boards/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글상세보기(id, sessionUser);

        model.addAttribute("board", board);
        return "board/detail";
    }

    // 글수정하기 화면 이동
    @GetMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Board board = boardService.글조회(id);
        model.addAttribute("board", board);
        return "board/update-form";
    }

// PostMapping
    // 글쓰기
    @PostMapping("/api/boards")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(reqDTO, sessionUser);
        return "redirect:/";
    }

    // 글수정하기
    @PutMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글조회(id);

        if(sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        boardService.글수정(id, sessionUser.getId(), reqDTO);
        return "redirect:/board/" + id;
    }

// DeleteMapping
    // 글삭제하기
    @DeleteMapping("/api/boards/{id}")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글조회(id);

        if(sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }

        boardService.글삭제(id, sessionUser.getId());
        return "redirect:/";
    }
}
