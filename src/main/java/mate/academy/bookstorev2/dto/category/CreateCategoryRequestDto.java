package mate.academy.bookstorev2.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;
    private String description;
}
