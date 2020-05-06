package otus.nchuraeva.spring.task8.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sessions")
public class Session {
  @Id
  private String id;

  @DBRef
  private User user;

  private String refreshToken;

  private String fingerprint;

  private String ip;

  private String userAgent;

  private Long expiresIn;

  private LocalDateTime createAt;
}
