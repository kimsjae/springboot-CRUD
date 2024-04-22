package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    // 회원수정
    @Transactional
    public User 회원수정(Integer id, UserRequest.UpdateDTO reqDTO) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다."));

        // 비밀번호 변경
        user.setPassword(reqDTO.getPassword());
        // 이메일 변경
        user.setEmail(reqDTO.getEmail());

        return user;
    }

    // 회원조회
    public UserResponse.DTO 회원조회(Integer id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다."));

        return new UserResponse.DTO(user);
    }

    // 로그인
    public User 로그인(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userJpaRepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));

        return sessionUser;
    }

    // 회원가입
    @Transactional
    public User 회원가입(UserRequest.JoinDTO reqDTO) {
        // username 중복검사
        Optional<User> userOP = userJpaRepository.findByUsername(reqDTO.getUsername());

        if (userOP.isPresent()) {
            throw new Exception400("중복된 username입니다.");
        }

        // 회원가입
        return userJpaRepository.save(reqDTO.toEntity());
    }
}
