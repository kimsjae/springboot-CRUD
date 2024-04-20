package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BoardRepository.class)
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;
    EntityManager em;

    @Test
    void findByIdJoinUser_test() {
        // given
        Integer id = 4;
        // when
        Board board = boardRepository.findByIdJoinUser(id);
        // then
        Assertions.assertThat(board.getUser().getId()).isEqualTo(3);
    }

    @Test
    void updateById_test() {
        // given
        Integer id = 1;
        String title = "제목444";
        String content = "내용444";
        // when
        Board board = boardRepository.updateById(id, title, content);
        // then
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
    }
}