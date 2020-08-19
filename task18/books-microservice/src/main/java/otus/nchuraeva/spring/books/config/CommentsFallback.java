package otus.nchuraeva.spring.books.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import otus.nchuraeva.spring.books.dto.CommentDto;
import otus.nchuraeva.spring.books.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentsFallback implements CommentService {

    @Override
    public List<CommentDto> findCommentsByBookId(Long id) {
        List<CommentDto> set = new ArrayList<>();
        CommentDto dto = new CommentDto();
        dto.setBody("Oops! Comments are temporary unavailable. But we have been fixing it!");
        dto.setCommentDate(LocalDateTime.now());
        set.add(dto);
        return set;
    }
}
