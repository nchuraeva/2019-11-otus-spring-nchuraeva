package otus.nchuraeva.spring.books.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import otus.nchuraeva.spring.books.entity.Author;
import otus.nchuraeva.spring.books.entity.Genre;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomBookDto {

    private Long id;

    private String title;

    private Genre genre;

    private Set<Author> authors;

    private List<CommentDto> comments;
}
