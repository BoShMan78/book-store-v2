package mate.academy.bookstorev2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @PositiveOrZero
    @NotNull
    private BigDecimal price;
    @NotNull
    private String isbn;
    private String description;
    private String coverImage;
}
