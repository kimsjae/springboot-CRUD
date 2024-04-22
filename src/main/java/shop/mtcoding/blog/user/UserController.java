package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.blog._core.utils.ApiUtil;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final HttpSession session;
    private final UserService userService;

// GetMapping
    // 회원 수정 폼으로 이동
    @GetMapping("/api/users")
    public ResponseEntity<?> updateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        UserResponse.DTO respDTO = userService.회원조회(sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null));
    }

// PostMapping
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.로그인(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        return ResponseEntity.ok(new ApiUtil(null));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(UserRequest.JoinDTO reqDTO) {
        User user = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil(user));
    }

// PutMapping
    // 회원수정
    @PutMapping("/api/users")
    public ResponseEntity<?> update(UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.회원수정(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        return ResponseEntity.ok(new ApiUtil(newSessionUser));
    }
}
