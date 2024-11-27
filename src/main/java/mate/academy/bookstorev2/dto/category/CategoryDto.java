package mate.academy.bookstorev2.dto.category;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
