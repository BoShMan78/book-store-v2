package mate.academy.bookstorev2.dto.cart.item;

import jakarta.validation.constraints.Positive;

public record CartItemUpdateDto(
        @Positive
        int quantity
) {
}
