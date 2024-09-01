package mate.academy.bookstorev2.dto.cart.item;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public record CartItemUpdateDto(
        @Positive
        @NonNull
        int quantity
) {
}
