package mate.academy.bookstorev2.service;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.order.OrderCreateDto;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.dto.order.OrderUpdateStatusDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.OrderItemMapper;
import mate.academy.bookstorev2.mapper.OrderMapper;
import mate.academy.bookstorev2.model.Order;
import mate.academy.bookstorev2.model.Order.Status;
import mate.academy.bookstorev2.model.OrderItem;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.order.OrderItemRepository;
import mate.academy.bookstorev2.repository.order.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderCreateDto addOrder(User user, OrderRequestDto dto) {
        Order order = orderMapper.toModel(dto);
        order.setUser(user);
        order.setStatus(Status.RECEIVED);
        order.setOrderDate(LocalDateTime.now());
        Set<OrderItem> orderItems = order.getOrderItems();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getPrice());
        }
        order.setTotal(total);
        orderRepository.save(order);
        return orderMapper.toCreateDto(order);
    }

    @Override
    public List<OrderDto> findAll(Pageable pageable, User user) {
        List<Order> allByUserId = orderRepository.findAllByUserId(user.getId()); //если пусто?
        List<OrderDto> orderDtos = allByUserId.stream()
                .map(orderMapper::toDto)
                .toList();
        return orderDtos;
    }

    @Override
    public List<OrderItemDto> getOrderItemsFromOrder(Long id) {
        List<OrderItem> allById = orderItemRepository.findAllById(id);
        List<OrderItemDto> orderItemDtos = allById.stream()
                .map(orderItemMapper::toDto)
                .toList();
        return orderItemDtos;
    }

    @Override
    public OrderItemDto getOrderItemsById(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrder_Id(itemId, orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find an item"));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderUpdateStatusDto dto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find an order"));
        order.setStatus(dto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
