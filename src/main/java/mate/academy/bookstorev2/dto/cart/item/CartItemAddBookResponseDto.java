package mate.academy.bookstorev2.dto.cart.item;

public record CartItemAddBookResponseDto(
        Long bookId,
        int quantity
) {
}
