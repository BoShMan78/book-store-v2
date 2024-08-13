package mate.academy.bookstorev2.repository.book;

import java.util.List;
import mate.academy.bookstorev2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);

    List<Book> findBooksByCategoriesId(Long id);
}
