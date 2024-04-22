package shop.mtcoding.blog.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserJpaRepositoryTest {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    void findByUsernameAndPassword_test() {
        // given
        String username = "ssar";
        String password = "1234";

        // when
        Optional<User> user = userJpaRepository.findByUsernameAndPassword(username, password);

        // then
        Assertions.assertThat(user.get().getUsername()).isEqualTo("ssar");
    }

    @Test
    void findByUsername() {
        // given
        String username = "ssar";

        // when
        Optional<User> user = userJpaRepository.findByUsername(username);

        // then
        Assertions.assertThat(user.get().getUsername()).isEqualTo("ssar");
    }
}