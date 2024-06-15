package mate.academy.bookstorev2.service;

import java.util.List;
import mate.academy.bookstorev2.dto.BookDto;
import mate.academy.bookstorev2.dto.BookSearchParametersDto;
import mate.academy.bookstorev2.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findBookById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto bookDto);

    void deleteById(Long id);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters);
}
