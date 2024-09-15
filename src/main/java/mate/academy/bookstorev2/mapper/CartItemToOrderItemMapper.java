package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.model.CartItem;
import mate.academy.bookstorev2.model.Order;
import mate.academy.bookstorev2.model.OrderItem;

public interface CartItemToOrderItemMapper {
    OrderItem toOrderItem(CartItem cartItem, Order order);
}
