package otus.nchuraeva.spring.task11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.nchuraeva.spring.task11.document.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>, UserRepositoryCustom {
    Mono<User> findUserByLogin(String login);
}
