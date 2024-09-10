package mate.academy.bookstorev2.repository.order;

import mate.academy.bookstorev2.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllById(Long id);

    Optional<OrderItem> findByIdAndOrder_Id(Long itemId, Long orderId);
}
