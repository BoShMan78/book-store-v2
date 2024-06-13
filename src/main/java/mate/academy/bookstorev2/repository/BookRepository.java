package mate.academy.bookstorev2.repository;

import mate.academy.bookstorev2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
