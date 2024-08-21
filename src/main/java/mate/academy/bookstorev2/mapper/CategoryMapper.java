package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.category.CategoryDto;
import mate.academy.bookstorev2.dto.category.CreateCategoryRequestDto;
import mate.academy.bookstorev2.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);

    void updateCategoryFromDto(CreateCategoryRequestDto requestDto,
                               @MappingTarget Category category);
}
