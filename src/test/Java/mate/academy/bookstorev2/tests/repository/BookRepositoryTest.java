package mate.academy.bookstorev2.tests.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.bookstorev2.model.Book;
import mate.academy.bookstorev2.model.Category;
import mate.academy.bookstorev2.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("""
            Verify findAllByCategoriesId() method works
            """)
    public void findAllByCategoriesId_ValidCategoryId_ReturnsList() {
        Category category = new Category();
        category.setName("Testing category");

        Book book = new Book();
        book.setTitle("Testing book");
        book.setIsbn("testing book isbn");
        book.setPrice(BigDecimal.valueOf(10));
        book.setCategories(Set.of(category));

        Pageable pageable = PageRequest.of(0, 10);

        bookRepository.save(book);

        List<Book> actual = bookRepository.findAllByCategoriesId(1L, pageable);

        Assertions.assertEquals(1, actual.size());

    }
}
