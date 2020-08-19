package otus.nchuraeva.spring.comments.service;

import otus.nchuraeva.spring.comments.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findCommentsByBookId(Long id);

    void saveComment(Comment comment);
}