package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

// GetMapping
    // 메인페이지, 글목록보기
    @GetMapping("/" )
    public String index(Model model) {
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);
        return "index";
    }

    // 글쓰기 화면 이동
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 글상세보기 이동
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Board board = boardRepository.findById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    // 글삭제
    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Integer id) {
        System.out.println("id : " + id);
        boardRepository.deleteById(id);
        return "redirect:/";
    }

    // 글수정하기 화면 이동
    @GetMapping("/board/update-form/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Board board = boardRepository.findById(id);
        model.addAttribute("board", board);
        return "board/update-form";
    }

// PostMapping
    // 글쓰기
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        boardRepository.save(reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/";
    }

    // 글수정하기
    @PostMapping("/board/update/{id}")
    public String update(@PathVariable Integer id, String title, String content) {
        boardRepository.updateById(title, content, id);
        return "redirect:/board/" + id;
    }
}
