package otus.nchuraeva.spring.task11.document;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String login;

    private String password;

    private String email;

    @ToString.Include
    private List<Subscription> subscriptions;
}
