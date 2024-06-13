package mate.academy.bookstorev2.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.BookDto;
import mate.academy.bookstorev2.dto.BookSearchParametersDto;
import mate.academy.bookstorev2.dto.CreateBookRequestDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.BookMapper;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.repository.book.BookRepository;
import mate.academy.bookstorev2.repository.book.spec.BookSearchSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = BookSearchSpecification.searchByTitleAndAuthor(
                searchParameters.title(),
                searchParameters.author()
        );
        return bookRepository.findAll(spec)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}

