package mate.academy.bookstorev2.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.dto.order.OrderUpdateStatusDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.OrderItemMapper;
import mate.academy.bookstorev2.mapper.OrderMapper;
import mate.academy.bookstorev2.model.CartItem;
import mate.academy.bookstorev2.model.Order;
import mate.academy.bookstorev2.model.Order.Status;
import mate.academy.bookstorev2.model.OrderItem;
import mate.academy.bookstorev2.model.ShoppingCart;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.order.OrderItemRepository;
import mate.academy.bookstorev2.repository.order.OrderRepository;
import mate.academy.bookstorev2.repository.shopping.cart.ShoppingCartRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public OrderDto placeAnOrder(User user, OrderRequestDto dto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: " + user.getId()));

        Order order = orderMapper.toModel(dto);
        order.setUser(user);
        order.setStatus(Status.RECEIVED);
        order.setOrderDate(LocalDateTime.now());

        Set<CartItem> cartItems = cart.getCartItems();
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItems.add(orderItem);
        }

        BigDecimal total = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderItems(orderItems);
        order.setTotal(total);
        orderRepository.save(order);
        shoppingCartService.removeAllCartItems(cart.getId());
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> findAll(Pageable pageable, User user) {
        return orderMapper.toOrderDtoList(orderRepository.findAllByUserId(user.getId(), pageable));
    }

    @Override
    public List<OrderItemDto> getOrderItemsFromOrder(Pageable pageable, Long id) {
        List<OrderItem> allById = Optional.ofNullable(orderItemRepository
                        .findAllByOrder_Id(id, pageable))
                .orElseGet(Collections::emptyList);
        List<OrderItemDto> orderItemDtos = allById.stream()
                .map(orderItemMapper::toDto)
                .toList();
        return orderItemDtos;
    }

    @Override
    public OrderItemDto getOrderItemsById(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrder_Id(itemId, orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find an item with item_id: " + itemId
                        + ", order_id: " + orderId));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderUpdateStatusDto dto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find an order with id: " + orderId));
        order.setStatus(dto.status());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
