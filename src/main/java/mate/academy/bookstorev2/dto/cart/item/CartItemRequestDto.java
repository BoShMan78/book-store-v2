package mate.academy.bookstorev2.dto.cart.item;

public record CartItemRequestDto(
        Long bookId,
        int quantity
) {
}
