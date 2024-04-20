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

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id) {
        return "board/detail";
    }

// PostMapping
    // 글쓰기
    @PostMapping("/board/save")
    public String save(String title, String content) {
        boardRepository.save(title, content);
        return "board/detail";
    }
}
