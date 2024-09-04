package mate.academy.bookstorev2.dto.cart.item;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity
) {
}
