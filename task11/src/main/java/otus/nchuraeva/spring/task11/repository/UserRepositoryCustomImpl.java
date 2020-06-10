package otus.nchuraeva.spring.task11.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import otus.nchuraeva.spring.task11.document.Subscription;
import otus.nchuraeva.spring.task11.document.User;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final ReactiveMongoOperations operations;

    @Override
    public Mono<Void> updateSubscription(Subscription subscription) {
        Query query = Query.query(Criteria.where("_id").is(subscription.getUserId()).and("subscriptions.id").is(subscription.getId()));
        Update update = new Update();
        update.set("subscriptions.$.price", subscription.getPrice());
        update.set("subscriptions.$.service.name", subscription.getService().getName());
        update.set("subscriptions.$.service.link", subscription.getService().getLink());
        update.set("subscriptions.$.service.category.name", subscription.getService().getCategory().getName());

        return operations.updateFirst(query, update, User.class).then(Mono.empty());
    }

    @Override
    public Mono<Void> addSubscription(String userId, Subscription subscription) {
        subscription.setId(ObjectId.get().toString());
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update().push("subscriptions", subscription);

        return operations.updateFirst(query, update, User.class).then(Mono.empty());
    }

    @Override
    public Mono<Void> deleteSubscription(String userId, Subscription subscription) {
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update().pull("subscriptions", subscription);

        return operations.updateMulti(query, update, User.class).then(Mono.empty());
    }

    @Override
    public Mono<Subscription> findUserByIdAndSubscriptionId(String userId, String subscriptionId) {
        Criteria findUserCriteria = Criteria.where("_id").is(userId);
        Criteria findSubscriptionCriteria = Criteria.where("subscriptions").elemMatch(Criteria.where("_id").is(subscriptionId));
        BasicQuery query = new BasicQuery(findUserCriteria.getCriteriaObject(), findSubscriptionCriteria.getCriteriaObject());

        return operations.find(query, User.class).take(1).next().map(obj -> obj.getSubscriptions().get(0));
    }
}

