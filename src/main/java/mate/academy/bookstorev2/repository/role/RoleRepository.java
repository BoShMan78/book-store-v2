package mate.academy.bookstorev2.repository.role;

import java.util.Optional;
import mate.academy.bookstorev2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}
