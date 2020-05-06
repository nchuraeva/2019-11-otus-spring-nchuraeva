package otus.nchuraeva.spring.task8.document;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    private String id;

    private String name;

    private String link;

    private String favicon;

    @ToString.Include
    private Category category;
}
