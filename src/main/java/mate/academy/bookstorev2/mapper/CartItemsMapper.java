package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.cart.item.CartItemAddBookResponseDto;
import mate.academy.bookstorev2.dto.cart.item.CartItemDto;
import mate.academy.bookstorev2.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class)
public interface CartItemsMapper {
    @Mappings({
            @Mapping(target = "bookId", source = "book.id"),
            @Mapping(target = "bookTitle", source = "book.title")
    })
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "bookId", source = "book.id")
    CartItemAddBookResponseDto toAddBookResponseDto(CartItem cartItem);
}
