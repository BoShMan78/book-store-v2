package mate.academy.bookstorev2.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.cart.item.CartItemRequestDto;
import mate.academy.bookstorev2.dto.cart.item.CartItemUpdateDto;
import mate.academy.bookstorev2.dto.shopping.cart.ShoppingCartDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.ShoppingCartMapper;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.model.CartItem;
import mate.academy.bookstorev2.model.ShoppingCart;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.book.BookRepository;
import mate.academy.bookstorev2.repository.cart.item.CartItemRepository;
import mate.academy.bookstorev2.repository.shopping.cart.ShoppingCartRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCartDto addToCart(User user, CartItemRequestDto dto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find shopping cart"));
        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by id: " + dto.bookId()));
        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> Objects.equals(cartItem.getBook().getId(), book.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setShoppingCart(cart);
                    newItem.setBook(book);
                    return newItem;
                });
        item.setQuantity(item.getQuantity() + dto.quantity());
        if (!cart.getCartItems().contains(item)) {
            cart.getCartItems().add(item);
        }
        shoppingCartRepository.save(cart);
        ShoppingCart updatedCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: " + user.getId()));
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getShoppingCart(User user, Pageable pageable) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: " + user.getId()));
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto updateCartItem(Long cartItemId, CartItemUpdateDto cartItemUpdateDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        cartItem.setQuantity(cartItemUpdateDto.quantity());
        cartItemRepository.save(cartItem);
        ShoppingCart updatedShoppingCart = shoppingCartRepository
                .findByUserId(cartItem.getShoppingCart().getUser().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find Shopping Cart by user id"));
        return shoppingCartMapper.toDto(updatedShoppingCart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
