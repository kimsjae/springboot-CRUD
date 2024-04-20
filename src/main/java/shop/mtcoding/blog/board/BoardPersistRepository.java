package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BoardPersistRepository {
    private final EntityManager em;

    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }
}
