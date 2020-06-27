package otus.nchuraeva.spring.task12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import otus.nchuraeva.spring.task12.document.Subscription;
import otus.nchuraeva.spring.task12.repository.SubscriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Subscription> findAllSubscriptions(String userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    @Override
    public void updateSubscription(String subscriptionId, Subscription updateSubscription) {
        Query query = Query.query(Criteria.where("id").is(subscriptionId));
        Update update = new Update();
        update.set("price", updateSubscription.getPrice());
        update.set("service.name", updateSubscription.getService().getName());
        update.set("service.link", updateSubscription.getService().getLink());
        update.set("service.category.name", updateSubscription.getService().getCategory().getName());

        mongoTemplate.updateFirst(query, update, Subscription.class);
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        Subscription subscription = this.findSubscriptionById(subscriptionId);
        subscriptionRepository.delete(subscription);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription findSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with id: " + subscriptionId));
    }
}
