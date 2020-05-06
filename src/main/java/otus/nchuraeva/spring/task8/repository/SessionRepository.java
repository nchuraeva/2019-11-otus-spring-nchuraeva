package otus.nchuraeva.spring.task8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import otus.nchuraeva.spring.task8.document.Session;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String>, SessionRepositoryCustom {

    @Query(value = "{ 'user.$id' : ?0 }")
    List<Session> findByUserId(String userId);
}
