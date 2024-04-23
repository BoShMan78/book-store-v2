package mate.academy.bookstorev2.repository;

import java.util.List;
import mate.academy.bookstorev2.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
