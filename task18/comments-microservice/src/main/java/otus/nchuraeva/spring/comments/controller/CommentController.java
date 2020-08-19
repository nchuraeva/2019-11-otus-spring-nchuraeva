package otus.nchuraeva.spring.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.nchuraeva.spring.comments.entity.Comment;
import otus.nchuraeva.spring.comments.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "/{id}")
    public List<Comment> findCommentsByBookId(@PathVariable("id") Long id) {
        return commentService.findCommentsByBookId(id);
    }

    @PostMapping
    public void findCommentsByBookId(@RequestBody Comment comment) {
        commentService.saveComment(comment);
    }
}
