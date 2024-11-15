package mate.academy.bookstorev2.tests.service;

import mate.academy.bookstorev2.model.ShoppingCart;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.shopping.cart.ShoppingCartRepository;
import mate.academy.bookstorev2.service.ShoppingCartServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    @InjectMocks
    ShoppingCartServiceImpl shoppingCartService;
    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("""
            Verify createShoppingCartForUser() method works
            """)
    public void createShoppingCartForUser_WithValidUser_ShouldReturnValidShoppingCart() {
        //Given
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("qwerty");
        user.setFirstName("Test");
        user.setLastName("Testov");

        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setUser(user);

        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(expectedShoppingCart);

        //When
        ShoppingCart actual = shoppingCartService.createShoppingCartForUser(user);

        //Then
        assertEquals(expectedShoppingCart, actual);
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }
}
