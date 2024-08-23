package mate.academy.bookstorev2.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        List<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categoryIds);
    }

    BookWithoutCategoriesDto toBookWithoutCategoriesDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }
}
