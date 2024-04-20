package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardNativeRepositoryTest {
    @Autowired
    BoardNativeRepository boardNativeRepository;

    @Test
    void findAll_test() {
        boardNativeRepository.findAll();
    }

    @Test
    void findById_test() {
        // given
        Integer id = 2;
        //when
        Board board = boardNativeRepository.findById(id);
        //then
        System.out.println("제목_test : " + board.getTitle());
        System.out.println("내용 : " + board.getContent());
    }

    @Test
    void deleteById_test() {
        // given
        Integer id = 4;
        // when
        boardNativeRepository.deleteById(id);
        //then
        List<Board> boardList = boardNativeRepository.findAll();
        Assertions.assertThat(boardList.size()).isEqualTo(3);
    }
}