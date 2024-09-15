package mate.academy.bookstorev2.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.bookstorev2.model.Order.Status;

public record OrderUpdateStatusDto(
        @NotNull
        Status status
) {
}
