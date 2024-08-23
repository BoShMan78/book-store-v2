package mate.academy.bookstorev2.service;

import java.util.List;
import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookSearchParametersDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findBookById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto bookDto);

    void deleteById(Long id);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable);

    List<BookWithoutCategoriesDto> searchBooksByCategoryId(Long id, Pageable pageable);
}
