package mate.academy.bookstorev2.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.cart.item.CartItemAddBookResponseDto;
import mate.academy.bookstorev2.dto.cart.item.CartItemUpdateDto;
import mate.academy.bookstorev2.dto.shopping.cart.AddToShoppingCartDto;
import mate.academy.bookstorev2.dto.shopping.cart.ShoppingCartDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.CartItemsMapper;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemsMapper cartItemsMapper;

    @Override
    public ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public CartItemAddBookResponseDto addToCart(User user, AddToShoppingCartDto dto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createShoppingCartForUser(user));
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

        return cartItemsMapper.toAddBookResponseDto(item);
    }

    @Override
    public ShoppingCartDto getShoppingCart(User user, Pageable pageable) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by id: " + user.getId()));
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public void updateCartItem(Long cartItemId, CartItemUpdateDto cartItemUpdateDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        cartItem.setQuantity(cartItemUpdateDto.quantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteCartItemById(cartItemId);
    }
}
