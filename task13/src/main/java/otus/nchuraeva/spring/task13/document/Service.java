package otus.nchuraeva.spring.task13.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Category category = new Category();
}
