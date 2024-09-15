package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.model.CartItem;
import mate.academy.bookstorev2.model.Order;
import mate.academy.bookstorev2.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemToOrderItemMapperImpl implements CartItemToOrderItemMapper {
    @Override
    public OrderItem toOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setOrder(order);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }
}
