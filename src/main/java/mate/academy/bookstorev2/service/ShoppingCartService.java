package mate.academy.bookstorev2.service;

import mate.academy.bookstorev2.dto.cart.item.CartItemRequestDto;
import mate.academy.bookstorev2.dto.cart.item.CartItemUpdateDto;
import mate.academy.bookstorev2.dto.shopping.cart.ShoppingCartDto;
import mate.academy.bookstorev2.model.ShoppingCart;
import mate.academy.bookstorev2.model.User;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCart createShoppingCartForUser(User user);

    ShoppingCartDto addToCart(User user, CartItemRequestDto dto);

    ShoppingCartDto getShoppingCart(User user, Pageable pageable);

    ShoppingCartDto updateCartItem(Long cartItemId, CartItemUpdateDto cartItemUpdateDto);

    void removeCartItem(Long cartItemId);
}
