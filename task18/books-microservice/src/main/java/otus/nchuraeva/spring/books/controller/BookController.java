package otus.nchuraeva.spring.books.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otus.nchuraeva.spring.books.dto.CommentDto;
import otus.nchuraeva.spring.books.dto.CustomBookDto;
import otus.nchuraeva.spring.books.entity.Book;
import otus.nchuraeva.spring.books.service.BookService;
import otus.nchuraeva.spring.books.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping(value = "/api/v1/books")
    public List<Book> getBookList() {
        return bookService.findAll();
    }

    @GetMapping(path = "/api/v1/books/{id}")
    public CustomBookDto getBookWithComments(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return this.bookMapper(book);
    }

    @PostMapping("/api/v1/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping(value = {"/api/v1/books"})
    public Book updateBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping(value = {"/api/v1/books/{id}"})
    public void deleteBookByUid(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    private CustomBookDto bookMapper(Book book){
        CustomBookDto bookDto = new CustomBookDto();
        BeanUtils.copyProperties(book, bookDto);
        List<CommentDto> comments = commentService.findCommentsByBookId(book.getId());
        bookDto.setComments(comments);
        return bookDto;
    }
}