package mate.academy.bookstorev2.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookSearchParametersDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.BookMapper;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.repository.book.BookRepository;
import mate.academy.bookstorev2.repository.book.spec.BookSearchSpecification;
import mate.academy.bookstorev2.repository.category.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSearchSpecification bookSearchSpecification;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllWithCategories(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto requestDto) {
        if (bookRepository.findById(id).isPresent()) {
            Book existingBook = bookMapper.toModel(requestDto);
            existingBook.setId(id);
            Book updatedBook = bookRepository.save(existingBook);
            return bookMapper.toDto(updatedBook);
        } else {
            throw new EntityNotFoundException("Can't find book by id:" + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable) {
        String title = searchParameters.title();
        String author = searchParameters.author();
        List<Book> books = bookRepository.searchByTitleAndAuthor(title, author, pageable);
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }



    @Override
    public List<BookWithoutCategoriesDto> searchBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategoriesId(id, pageable).stream()
                .map(bookMapper::toBookWithoutCategoriesDto)
                .toList();
    }
}
