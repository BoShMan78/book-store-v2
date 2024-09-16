package mate.academy.bookstorev2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.dto.order.OrderUpdateStatusDto;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.service.OrderService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order",
            description = "Move all items from shopping cart to order")
    public OrderDto placeAnOrder(@RequestBody @Valid OrderRequestDto dto,
                                       @AuthenticationPrincipal User user) {
        return orderService.placeAnOrder(user, dto);
    }

    @GetMapping
    @Operation(summary = "Get information about orders",
            description = "Get information about orders")
    public List<OrderDto> getAll(
            @ParameterObject @PageableDefault Pageable pageable,
            @AuthenticationPrincipal User user) {
        return orderService.findAll(pageable, user);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get information about items by order id",
            description = "Get information about items by order id")
    public List<OrderItemDto> getOrderItemsFromOrder(
            @ParameterObject @PageableDefault Pageable pageable,
            @PathVariable @Positive Long orderId) {
        return orderService.getOrderItemsFromOrder(pageable, orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get information about specific item by order id and item id",
            description = "Get information about specific item by order id and item id")
    public OrderItemDto getOrderItemById(@PathVariable @Positive Long orderId,
                                         @PathVariable @Positive Long itemId) {
        return orderService.getOrderItemsById(orderId, itemId);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change order status",
            description = "Change order status by order id")
    public OrderDto updateOrderStatus(@PathVariable @Positive Long orderId,
                                      @RequestBody OrderUpdateStatusDto dto) {
        return orderService.updateOrderStatus(orderId, dto);
    }
}
