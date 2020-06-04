package otus.nchuraeva.spring.task10.document;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    private String id;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private BigDecimal price;

    private LocalDateTime notifyTime;

    @ToString.Include
    Service service = new Service();
}
