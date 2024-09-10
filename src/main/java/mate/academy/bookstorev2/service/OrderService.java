package mate.academy.bookstorev2.service;

import mate.academy.bookstorev2.dto.order.OrderCreateDto;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.dto.order.OrderUpdateStatusDto;
import mate.academy.bookstorev2.model.Order;
import mate.academy.bookstorev2.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderCreateDto addOrder(User user, OrderRequestDto dto);

    List<OrderDto> findAll(Pageable pageable, User user);

    List<OrderItemDto> getOrderItemsFromOrder(Long id);

    OrderItemDto getOrderItemsById(Long orderId, Long itemId);

    OrderDto updateOrderStatus(Long orderId, OrderUpdateStatusDto dto);
}
