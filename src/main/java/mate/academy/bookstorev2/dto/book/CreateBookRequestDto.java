package mate.academy.bookstorev2.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import mate.academy.bookstorev2.validation.UniqueIsbn;

@Getter
@Setter
@Accessors(chain = true)
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
    private List<Long> categoryIds;
}
