package mate.academy.bookstorev2.repository.user;

import java.util.Optional;
import mate.academy.bookstorev2.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findUserByEmail(String email);
}
