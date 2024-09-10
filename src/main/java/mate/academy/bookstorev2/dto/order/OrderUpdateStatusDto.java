package mate.academy.bookstorev2.dto.order;

import mate.academy.bookstorev2.model.Order.Status;

public record OrderUpdateStatusDto(
        Status status
) {
}
