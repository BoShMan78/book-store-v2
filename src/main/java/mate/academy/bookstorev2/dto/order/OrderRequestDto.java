package mate.academy.bookstorev2.dto.order;

import mate.academy.bookstorev2.model.OrderItem;

import java.util.List;
import java.util.Set;

public record OrderRequestDto(
        String shippingAddress,
        List<OrderItem> orderItems
) {
}
