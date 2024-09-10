package mate.academy.bookstorev2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.order.OrderCreateDto;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.dto.order.OrderUpdateStatusDto;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.service.OrderService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderCreateDto addOrder(@RequestBody @Valid OrderRequestDto dto, @AuthenticationPrincipal User user) {
        return orderService.addOrder(user, dto);
    }

    @GetMapping
    public List<OrderDto> getAll(@ParameterObject @PageableDefault Pageable pageable, @AuthenticationPrincipal User user) {
        return orderService.findAll(pageable, user);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItemsFromOrder(@PathVariable @Positive Long orderId) {
        return orderService.getOrderItemsFromOrder(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItemById(@PathVariable @Positive Long orderId, @PathVariable @Positive Long itemId) {
        return orderService.getOrderItemsById(orderId, itemId);
    }

    @PatchMapping("/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable @Positive Long orderId, @RequestBody OrderUpdateStatusDto dto) {
        return orderService.updateOrderStatus(orderId, dto);
    }
}
