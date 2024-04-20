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
}
