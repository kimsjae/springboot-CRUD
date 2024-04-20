package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardPersistRepository {
    private final EntityManager em;

    // 글쓰기
    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    // 글목록보기
    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    // 글상세보기
    public Board findById(Integer id) {
        Board board = em.find(Board.class, id);
        return board;
    }
}