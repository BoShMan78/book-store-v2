package mate.academy.bookstorev2.dto.category;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequestDto {
    @Column(nullable = false)
    private String name;
    private String description;
}
