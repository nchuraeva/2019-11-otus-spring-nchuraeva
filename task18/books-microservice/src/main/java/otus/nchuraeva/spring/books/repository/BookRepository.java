package otus.nchuraeva.spring.books.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import otus.nchuraeva.spring.books.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "authors-and-genre-book-graph", type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"genre", "authors"})
    List<Book> findAll();
}
