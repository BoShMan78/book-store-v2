package mate.academy.bookstorev2.tests.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.dto.book.BookDto;
import mate.academy.bookstorev2.dto.book.BookSearchParametersDto;
import mate.academy.bookstorev2.dto.book.BookWithoutCategoriesDto;
import mate.academy.bookstorev2.dto.book.CreateBookRequestDto;
import mate.academy.bookstorev2.mapper.BookMapper;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.repository.book.BookRepository;
import mate.academy.bookstorev2.service.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("""
            Verify the correct book title was returned when book exists
            """)
    public void findBookById_WithValidBookId_ShouldReturnValidBookTitle() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Testing book");
        book.setIsbn("testing book isbn");
        book.setDeleted(false);

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setIsbn(book.getIsbn());

        // When
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        String actual = bookService.findBookById(book.getId()).getTitle();
        // Then
        String expected = book.getTitle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify that an exception is thrown when a negative book ID is passed
            """)
    public void findBookById_WithNegativeBookId_ShouldThrowException() {
        // Given
        Long bookId = -10L;
        when(bookRepository.findById(bookId)).thenThrow(new RuntimeException("Can't find book by id:" + bookId));

        // When
        Exception exception = assertThrows(RuntimeException.class, () -> bookService.findBookById(bookId));

        // Then
        String expected = "Can't find book by id:" + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify that an exception is thrown when a non existing book ID is passed
            """)
    public void findBookById_WithNonExistingBookId_ShouldThrowException() {
        // Given
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(RuntimeException.class, () -> bookService.findBookById(bookId));

        // Then
        String expected = "Can't find book by id:" + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify save() method works
            """)
    public void save_ValidCreateBookRequestDto_ReturnBookDto() {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test book");
        requestDto.setAuthor("Test Author");
        requestDto.setPrice(BigDecimal.valueOf(100));
        requestDto.setIsbn("123someUniqueNumber1234");

        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPrice(requestDto.getPrice());
        book.setIsbn(requestDto.getIsbn());

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setIsbn(book.getIsbn());

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // WHEN
        BookDto savedBookDto = bookService.save(requestDto);

        //Then
        assertThat(savedBookDto).isEqualTo(bookDto);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("""
            Verify findAll() method works
            """)
    public void findAll_ValidPageable_ReturnsAllBooks() {
        // Given
        Book book = new Book();
        book.setTitle("Test book");
        book.setAuthor("Test Author");
        book.setPrice(BigDecimal.valueOf(100));
        book.setIsbn("123someUniqueNumber1234");

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setIsbn(book.getIsbn());

        Pageable pageable = PageRequest.of(0, 10);
        List<Book> bookList = List.of(book);

        when(bookRepository.findAllWithCategories(pageable)).thenReturn(bookList);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // When
        List<BookDto> actual = bookService.findAll(pageable);

        // Then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(bookDto);

        verify(bookRepository, times(1)).findAllWithCategories(pageable);
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("""
            Verify updateBookById() method works
            """)
    public void updateBookById_WithValidBook_ShouldReturnUpdatedBookDto() {
        // Given
        Long id = 12345L;

        Book existingBookInRepo = new Book();
        existingBookInRepo.setId(id);
        existingBookInRepo.setTitle("Test book");
        existingBookInRepo.setAuthor("Test Author");
        existingBookInRepo.setPrice(BigDecimal.valueOf(100));
        existingBookInRepo.setIsbn("123someUniqueNumber1234");

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated test book");
        requestDto.setAuthor("Updated test Author");
        requestDto.setPrice(BigDecimal.valueOf(100));
        requestDto.setIsbn("123updated1234");

        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setTitle(requestDto.getTitle());
        updatedBook.setAuthor(requestDto.getAuthor());
        updatedBook.setPrice(requestDto.getPrice());
        updatedBook.setIsbn(requestDto.getIsbn());

        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setId(id);
        expectedBookDto.setTitle(requestDto.getTitle());
        expectedBookDto.setAuthor(requestDto.getAuthor());
        expectedBookDto.setPrice(requestDto.getPrice());
        expectedBookDto.setIsbn(requestDto.getIsbn());

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBookInRepo));
        when(bookMapper.toModel(requestDto)).thenReturn(updatedBook);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedBookDto);

        // When
        BookDto actual = bookService.updateBookById(id, requestDto);

        // Then
        assertEquals(actual, expectedBookDto);
        verify(bookRepository).findById(id);
        verify(bookMapper).toModel(requestDto);
        verify(bookRepository).save(updatedBook);
        verify(bookMapper).toDto(updatedBook);
    }

    @Test
    @DisplayName("""
            Verify searchBooks() method works
            """)
    public void searchBooks_WithValidSearchParams_ShouldReturnBookDtoList() {
        // Given
        String title = "Test book";
        String author = "Test author";
        Pageable pageable = PageRequest.of(0, 10);
        BookSearchParametersDto bookSearchParametersDto = new BookSearchParametersDto(title, author);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(BigDecimal.valueOf(100));
        book.setIsbn("123someUniqueNumber1234");

        List<Book> bookList = List.of(book);

        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setTitle(book.getTitle());
        expectedBookDto.setAuthor(book.getAuthor());
        expectedBookDto.setPrice(book.getPrice());
        expectedBookDto.setIsbn(book.getIsbn());
        List<BookDto> expected = List.of(expectedBookDto);

        when(bookRepository.searchByTitleAndAuthor(title, author, pageable)).thenReturn(bookList);
        when(bookMapper.toDto(book)).thenReturn(expectedBookDto);

        // When
        List<BookDto> actual = bookService.searchBooks(bookSearchParametersDto, pageable);

        //Then
        assertEquals(expected, actual, "The list of BookDto objects should match the expected list");
        verify(bookRepository).searchByTitleAndAuthor(title, author, pageable);
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("""
            Verify searchBooksByCategoryId() method
            """)
    public void searchBooksByCategoryId_WithValidData_ShouldReturnBookWithoutCategoriesDtoList() {
        // Given
        Long id = 12345L;
        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        book.setTitle("Test book");
        book.setAuthor("Test Author");
        book.setPrice(BigDecimal.valueOf(100));
        book.setIsbn("123someUniqueNumber1234");

        List<Book> bookList = List.of(book);

        BookWithoutCategoriesDto bookWithoutCategoriesDto = new BookWithoutCategoriesDto();
        bookWithoutCategoriesDto.setTitle(book.getTitle());
        bookWithoutCategoriesDto.setAuthor(book.getAuthor());
        bookWithoutCategoriesDto.setPrice(book.getPrice());
        bookWithoutCategoriesDto.setIsbn(book.getIsbn());

        List<BookWithoutCategoriesDto> expected = List.of(bookWithoutCategoriesDto);

        when(bookRepository.findAllByCategoriesId(id, pageable)).thenReturn(bookList);
        when(bookMapper.toBookWithoutCategoriesDto(book)).thenReturn(bookWithoutCategoriesDto);

        // When
        List<BookWithoutCategoriesDto> actual = bookService.searchBooksByCategoryId(id, pageable);

        // Then
        assertEquals(expected, actual);
        verify(bookRepository).findAllByCategoriesId(id, pageable);
        verify(bookMapper).toBookWithoutCategoriesDto(book);

    }


}
