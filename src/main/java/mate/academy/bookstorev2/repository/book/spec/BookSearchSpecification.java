package mate.academy.bookstorev2.repository.book.spec;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import mate.academy.bookstorev2.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSearchSpecification {
    public Specification<Book> searchByTitleAndAuthor(String title, String author) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
