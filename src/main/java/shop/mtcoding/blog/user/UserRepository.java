package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    // 로그인
    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    // 회원가입
    @Transactional
    public User save(User user) {
        em.persist(user);
        return user;
    }

    // 회원수정
    public User findById(Integer id) {
        User user = em.find(User.class, id);
        return user;
    }

    @Transactional
    public User updateById(Integer id, String password, String email) {
        User user = findById(id);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
