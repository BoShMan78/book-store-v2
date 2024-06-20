package mate.academy.bookstorev2.repository.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface PagingAndSortingRepository<T, I0> extends Repository<T, I0> {
    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}
