package otus.nchuraeva.spring.task13.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "subscriptions")
public class Subscription {

    private String id;

    @DBRef
    private User user;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private BigDecimal price;

    private LocalDateTime notifyTime;

    @ToString.Include
    Service service = new Service();
}
