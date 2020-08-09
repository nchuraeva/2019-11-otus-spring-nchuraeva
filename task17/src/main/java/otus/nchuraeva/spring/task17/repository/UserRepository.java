package otus.nchuraeva.spring.task17.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.nchuraeva.spring.task17.document.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
