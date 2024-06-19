package mate.academy.bookstorev2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstorev2.validation.UniqueIsbn;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @PositiveOrZero
    @NotNull
    private BigDecimal price;
    @NotBlank
    @UniqueIsbn
    private String isbn;
    private String description;
    private String coverImage;
}
