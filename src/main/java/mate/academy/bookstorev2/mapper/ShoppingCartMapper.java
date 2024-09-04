package mate.academy.bookstorev2.mapper;

import java.util.List;
import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.cart.item.CartItemDto;
import mate.academy.bookstorev2.dto.shopping.cart.ShoppingCartDto;
import mate.academy.bookstorev2.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = CartItemsMapper.class)
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void mapCartItems(@MappingTarget ShoppingCartDto shoppingCartDto,
                              ShoppingCart shoppingCart,
                              @Context CartItemsMapper cartItemsMapper) {
        List<CartItemDto> cartItems = shoppingCart.getCartItems().stream()
                .map(cartItemsMapper::toDto)
                .toList();
        shoppingCartDto.setCartItems(cartItems);
    }
}
