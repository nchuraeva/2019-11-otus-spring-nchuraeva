package otus.nchuraeva.spring.task10.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.nchuraeva.spring.task10.document.User;

;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    User findUserByLogin(String login);
}
