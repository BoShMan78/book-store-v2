package mate.academy.bookstorev2.repository.order;

import mate.academy.bookstorev2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id);

    Optional<Order> findById(Long id);
}
