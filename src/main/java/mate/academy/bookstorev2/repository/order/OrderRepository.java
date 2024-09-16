package mate.academy.bookstorev2.repository.order;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o "
            + "LEFT JOIN FETCH o.orderItems oi "
            + "LEFT JOIN FETCH oi.book b "
            + "WHERE o.user.id=:userId")
    List<Order> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT o FROM Order o "
            + "LEFT JOIN FETCH o.orderItems oi "
            + "WHERE o.id=:orderId")
    Optional<Order> findById(@Param("orderId") Long orderId);
}
