package otus.nchuraeva.spring.task16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.nchuraeva.spring.task16.document.Subscription;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findAllByUserId(String userId);
}
