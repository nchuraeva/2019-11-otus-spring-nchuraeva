package otus.nchuraeva.spring.task15.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import otus.nchuraeva.spring.task15.entity.Butterfly;

@Repository
public interface ButterflyRepository extends MongoRepository<Butterfly, String> { }
