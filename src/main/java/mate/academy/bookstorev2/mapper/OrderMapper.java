package mate.academy.bookstorev2.mapper;

import java.util.List;
import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    Order toModel(OrderRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @AfterMapping
    default void mapOrderItems(@MappingTarget OrderDto orderDto,
                               Order order,
                               @Context OrderItemMapper orderItemMapper) {
        List<OrderItemDto> orderItemDtoList = order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
        orderDto.setOrderItems(orderItemDtoList);
    }

    @Mapping(source = "user.id", target = "userId")
    List<OrderDto> toOrderDtoList(List<Order> orderList);
}
