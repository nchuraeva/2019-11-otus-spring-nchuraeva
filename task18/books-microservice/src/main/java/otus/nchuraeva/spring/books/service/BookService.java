package otus.nchuraeva.spring.books.service;

import otus.nchuraeva.spring.books.entity.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    void deleteById(Long id);

    Book findById(Long id);

    List<Book> findAll();
}
