package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import mate.academy.bookstorev2.model.Book;
import org.mapstruct.Mapping;

//@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    BookWithoutCategoriesDto toBookWithoutCategoriesDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    //    @AfterMapping
    //    default void setCategories(@MappingTarget Book book, CreateBookRequestDto requestDto) {
    //        Set<Category> categorySet = requestDto.getCategoryIds().stream()
    //                .map(Category::new)
    //                .collect(Collectors.toSet());
    //        book.setCategories(categorySet);
    //    }
}
