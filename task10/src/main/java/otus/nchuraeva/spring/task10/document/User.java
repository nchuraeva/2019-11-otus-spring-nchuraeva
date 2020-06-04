package otus.nchuraeva.spring.task10.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
