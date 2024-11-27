package mate.academy.bookstorev2.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;
    private String description;
}
