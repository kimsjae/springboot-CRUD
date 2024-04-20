package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    void findAll_test() {
        boardRepository.findAll();
    }

    @Test
    void findById_test() {
        // given
        Integer id = 2;
        //when
        Board board = boardRepository.findById(id);
        //then
        System.out.println("제목_test : " + board.getTitle());
        System.out.println("내용 : " + board.getContent());
    }
}