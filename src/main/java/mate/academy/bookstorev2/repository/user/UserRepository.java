package mate.academy.bookstorev2.repository.user;

import mate.academy.bookstorev2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
