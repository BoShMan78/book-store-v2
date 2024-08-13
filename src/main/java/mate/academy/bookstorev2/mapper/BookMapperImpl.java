package mate.academy.bookstorev2.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.model.Category;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());

        setCategoryIds(bookDto, book);

        return bookDto;
    }

    private void setCategoryIds(BookDto bookDto, Book book) {
        List<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categoryIds);
    }

    @Override
    public Book toModel(CreateBookRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPrice(requestDto.getPrice());
        book.setIsbn(requestDto.getIsbn());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());

        setCategories(requestDto.getCategoryIds(), book);

        return book;
    }

    private void setCategories(List<Long> categoryIds,Book book) {
        Set<Category> categories = categoryIds.stream()
                        .map(Category::new)
                        .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    public BookWithoutCategoriesDto toBookWithoutCategoriesDto(Book book) {
        if (book == null) {
            return null;
        }

        BookWithoutCategoriesDto bookDto = new BookWithoutCategoriesDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());

        return bookDto;
    }
}
