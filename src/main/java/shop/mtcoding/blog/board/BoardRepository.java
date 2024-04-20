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

    // 글상세보기
    public Board findByIdJoinUser(Integer id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    // 글목록보기
    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    // 글쓰기
    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    // 글삭제하기
    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createQuery("delete from Board b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 글수정하기
    public Board findById(Integer id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    @Transactional
    public Board updateById(Integer id, String title, String content) {
        Board board = findById(id);
        board.setTitle(title);
        board.setContent(content);
        return board;
    }

}
