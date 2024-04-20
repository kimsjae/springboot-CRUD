package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    // 글쓰기
    @Transactional
    public void save(String title, String content) {
        Query query = em.createNativeQuery("insert into board_tb (title, content, created_at) values (?, ?, now())");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.executeUpdate();
    }

    // 글목록보기
    public List<Board> findAll() {
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        return query.getResultList();
    }

    // 글상세보기
    public Board findById(Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        return (Board) query.getSingleResult();
    }

    // 글삭제
    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    // 글수정하기
    @Transactional
    public void updateById(String title, String content, Integer id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        query.executeUpdate();
    }
}
