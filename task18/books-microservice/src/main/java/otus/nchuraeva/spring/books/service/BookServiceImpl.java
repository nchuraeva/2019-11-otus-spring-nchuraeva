package otus.nchuraeva.spring.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.nchuraeva.spring.books.entity.Book;
import otus.nchuraeva.spring.books.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book isn't be existed"));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
