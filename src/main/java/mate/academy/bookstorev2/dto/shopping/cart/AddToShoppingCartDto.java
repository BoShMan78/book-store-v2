package mate.academy.bookstorev2.dto.shopping.cart;

public record AddToShoppingCartDto(
        Long bookId,
        int quantity
) {
}
