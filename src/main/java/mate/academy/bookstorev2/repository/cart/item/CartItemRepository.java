package mate.academy.bookstorev2.repository.cart.item;

import java.util.Optional;
import mate.academy.bookstorev2.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long>, JpaSpecificationExecutor<CartItem> {
    void deleteById(Long cartItemId);

    @Query("SELECT ci FROM CartItem ci LEFT JOIN FETCH ci.book WHERE ci.id= :id")
    Optional<CartItem> findById(@Param("id") Long id);
}
