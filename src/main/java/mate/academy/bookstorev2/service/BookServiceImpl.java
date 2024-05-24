package mate.academy.bookstorev2.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.BookDto;
import mate.academy.bookstorev2.dto.CreateBookRequestDto;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.mapper.BookMapper;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.repository.BookRepository;
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
        Book book = bookRepository.findBookById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id)
        );
        return bookMapper.toDto(book);
    }
}
