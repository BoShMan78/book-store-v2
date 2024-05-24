package mate.academy.bookstorev2.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findBookById(Long id);
}
