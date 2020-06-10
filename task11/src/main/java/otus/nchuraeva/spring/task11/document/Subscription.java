package otus.nchuraeva.spring.task11.document;

import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Subscription {

    private String userId;

    private String id;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private BigDecimal price;

    private LocalDateTime notifyTime;

    @ToString.Include
    Service service = new Service();
}
