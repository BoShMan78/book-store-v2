package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.order.OrderItemDto;
import mate.academy.bookstorev2.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);
}
