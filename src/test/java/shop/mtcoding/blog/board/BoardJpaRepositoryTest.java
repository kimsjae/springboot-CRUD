package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardJpaRepositoryTest {
    @Autowired
    BoardJpaRepository boardJpaRepository;

    @Test
    void findByIdJoinUser() {
        // given
        Integer boardId = 1;
        // when
        Optional<Board> boardOP = boardJpaRepository.findByIdJoinUser(boardId);
        // then
        Assertions.assertThat(boardOP.get().getTitle()).isEqualTo("제목1");
    }
}