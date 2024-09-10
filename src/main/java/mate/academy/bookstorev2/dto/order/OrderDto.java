package mate.academy.bookstorev2.dto.order;

import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstorev2.dto.cart.item.CartItemDto;
import mate.academy.bookstorev2.model.Order.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private List<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;

}
