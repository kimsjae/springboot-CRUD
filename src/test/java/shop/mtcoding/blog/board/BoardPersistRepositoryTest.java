package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BoardPersistRepository.class)
class BoardPersistRepositoryTest {
    @Autowired
    BoardPersistRepository boardPersistRepository;

    @Test
    void save() {
        // given
        Board board = new Board("제목5", "내용5");
        // when
        boardPersistRepository.save(board);
        System.out.println("save_test : " + board);
    }
}