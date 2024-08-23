package mate.academy.bookstorev2.repository.book;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);

    List<Book> findAllByCategoriesId(Long id, Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories")
    List<Book> findAllWithCategories(Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id= :id")
    Optional<Book> findById(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories "
            + "WHERE (:title IS NULL OR b.title LIKE %:title%) "
            + "AND (:author IS NULL OR b.author LIKE %:author%)")
    List<Book> searchByTitleAndAuthor(@Param("title") String title,
                                      @Param("author") String author, Pageable pageable);
}
