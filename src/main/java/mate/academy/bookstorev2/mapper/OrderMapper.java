package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.order.OrderCreateDto;
import mate.academy.bookstorev2.dto.order.OrderDto;
import mate.academy.bookstorev2.dto.order.OrderRequestDto;
import mate.academy.bookstorev2.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    Order toModel(OrderRequestDto dto);

    OrderCreateDto toCreateDto(Order order);

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

//    @AfterMapping
//    default void mapOrderItems(@MappingTarget OrderDto orderDto,
//                               @Context OrderItemMapper orderItemMapper)
}
