package otus.nchuraeva.spring.task9.document;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Builder
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String login;

    private String password;

    private String email;

    @ToString.Include
    private List<Subscription> subscriptions = new ArrayList<>();
}
