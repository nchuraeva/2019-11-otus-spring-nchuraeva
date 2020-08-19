package otus.nchuraeva.spring.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.nchuraeva.spring.comments.reposiory.CommentRepository;
import otus.nchuraeva.spring.comments.entity.Comment;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findCommentsByBookId(Long id) {
        return commentRepository.findAllByBookId(id);
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}