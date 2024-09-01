package mate.academy.bookstorev2.dto.shopping.cart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstorev2.dto.cart.item.CartItemDto;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
