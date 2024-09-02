package mate.academy.bookstorev2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.cart.item.CartItemRequestDto;
import mate.academy.bookstorev2.dto.cart.item.CartItemUpdateDto;
import mate.academy.bookstorev2.dto.shopping.cart.ShoppingCartDto;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.service.ShoppingCartService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart management", description = "Endpoints for managing shopping carts")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @Operation(summary = "Add a book to the shopping cart",
            description = "Add a book to the shopping cart")
    public ShoppingCartDto addBookToShoppingCart(
            @RequestBody @Valid CartItemRequestDto dto,
            @AuthenticationPrincipal User user) {
        return shoppingCartService.addToCart(user, dto);
    }

    @GetMapping
    @Operation(summary = "Get information about shopping cart",
            description = "Get information about shopping cart")
    public ShoppingCartDto getCart(@AuthenticationPrincipal User user,
                                   @ParameterObject @PageableDefault Pageable pageable) {
        return shoppingCartService.getShoppingCart(user, pageable);
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update cart item",
            description = "Update quantity in cart item by cart item's id")
    public ShoppingCartDto updateCartItem(@PathVariable @Positive Long cartItemId,
                                      @RequestBody CartItemUpdateDto cartItemUpdateDto) {
        return shoppingCartService.updateCartItem(cartItemId, cartItemUpdateDto);
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Delete cart item", description = "Delete cart item by cart item's id")
    public void deleteCartItem(@PathVariable @Positive Long cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
    }
}
