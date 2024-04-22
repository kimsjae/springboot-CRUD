package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final UserService userService;

// GetMapping
    // 회원 수정 폼으로 이동
    @GetMapping("/api/users")
    public String updateForm(Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원조회(sessionUser.getId());
        model.addAttribute("user", user);
        return "user/update-form";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

// PostMapping
    // 로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.로그인(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO) {
        userService.회원가입(reqDTO);
        return "redirect:/login-form";
    }

// PutMapping
    // 회원수정
    @PutMapping("/api/users")
    public String update(UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.회원수정(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        return "redirect:/";
    }
}
