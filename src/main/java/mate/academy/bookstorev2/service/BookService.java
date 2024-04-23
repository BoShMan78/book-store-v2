package mate.academy.bookstorev2.service;

import java.util.List;
import mate.academy.bookstorev2.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
