package otus.nchuraeva.spring.comments.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "bigserial", updatable = false)
    private Long id;

    @Column(name = "BODY", nullable = false)
    private String body;

    @Column(name = "COMMENT_DATE", nullable = false)
    private LocalDateTime commentDate;

    @Column(name = "bookId", nullable = false)
    private Long bookId;
}
