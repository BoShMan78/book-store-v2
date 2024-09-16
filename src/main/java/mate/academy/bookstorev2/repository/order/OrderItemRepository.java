package mate.academy.bookstorev2.repository.order;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder_Id(Long id, Pageable pageable);

    Optional<OrderItem> findByIdAndOrder_Id(Long itemId, Long orderId);
}
