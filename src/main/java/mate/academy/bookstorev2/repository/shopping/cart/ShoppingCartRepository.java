package mate.academy.bookstorev2.repository.shopping.cart;

import java.util.Optional;
import mate.academy.bookstorev2.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH sc.user "
            + "LEFT JOIN FETCH ci.book "
            + "WHERE sc.user.id =:userId")
    Optional<ShoppingCart> findByUserId(@Param("userId") Long userId);
}
