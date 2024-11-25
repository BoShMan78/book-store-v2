package mate.academy.bookstorev2.tests.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.model.Category;
import mate.academy.bookstorev2.repository.book.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Book book;
    private Pageable pageable;
    private Category category;

    @BeforeEach
    private void inputStartingData() {
        category = new Category();
        category.setName("Testing category");
        entityManager.persist(category);

        book = new Book();
        book.setTitle("Testing book");
        book.setIsbn("testing book isbn");
        book.setAuthor("Author");
        book.setPrice(BigDecimal.valueOf(10));
        book.setCategories(Set.of(category));

        pageable = PageRequest.of(0, 10);

        bookRepository.save(book);
    }

    @AfterEach
    public void clearDatabase() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("""
            Verify findAllByCategoriesId() method works
            """)
    public void findAllByCategoriesId_ValidCategoryId_ReturnsList() {
        List<Book> actual = bookRepository.findAllByCategoriesId(category.getId(), pageable);

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(book.getTitle(), actual.get(0).getTitle());
    }

    @Test
    @DisplayName("""
            Verify findAllWithCategories() method works
            """)
    public void findAllWithCategories_ValidDate_ReturnsList() {
        List<Book> allWithCategories = bookRepository.findAllWithCategories(pageable);
        Set<Category> actualCategories = allWithCategories.get(0).getCategories();

        Assertions.assertEquals(1, actualCategories.size());
        Assertions.assertTrue(actualCategories.contains(category));
    }

    @Test
    @DisplayName("""
            Verify findById() method works""")
    public void findById_ValidId_ReturnsValidBook() throws RuntimeException {
        Long id = book.getId();
        Book actualBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find the book with id: " + id));

        Assertions.assertEquals(book.getTitle(), actualBook.getTitle());
    }

    @Test
    @DisplayName("""
            Verify search searchByTitleAndAuthor() method works""")
    public void searchByTitleAndAuthor_ValidTitle_ReturnsList() {
        String title = book.getTitle();
        String author = book.getAuthor();
        List<Book> actualBooks = bookRepository.searchByTitleAndAuthor(title, author, pageable);

        Assertions.assertEquals(1, actualBooks.size());
        Assertions.assertEquals(title, actualBooks.get(0).getTitle());
    }
}
