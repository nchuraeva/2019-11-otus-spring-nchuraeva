package otus.nchuraeva.spring.task14.batch;

import org.springframework.batch.item.ItemProcessor;
import otus.nchuraeva.spring.task14.nosql.document.Category;
import otus.nchuraeva.spring.task14.nosql.document.Service;
import otus.nchuraeva.spring.task14.nosql.document.Subscription;
import otus.nchuraeva.spring.task14.nosql.document.User;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsCategory;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsService;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsSubscription;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsUser;

public class SubscriptionItemProcessor implements ItemProcessor<Subscription, RdbmsSubscription> {

    @Override
    public RdbmsSubscription process(Subscription subscription) throws Exception {
        User user = subscription.getUser();
        Service service = subscription.getService();
        Category category = service.getCategory();

        RdbmsSubscription rdbmsSubscription = new RdbmsSubscription();
        RdbmsUser rdbmsUser = new RdbmsUser();
        RdbmsCategory rdbmsCategory = new RdbmsCategory();
        RdbmsService rdbmsService = new RdbmsService();

        rdbmsCategory.setName(category.getName());

        rdbmsUser.setPassword(user.getPassword());
        rdbmsUser.setEmail(user.getEmail());
        rdbmsUser.setUsername(user.getUsername());

        rdbmsSubscription.setEndedAt(subscription.getEndedAt());
        rdbmsSubscription.setNotifyTime(subscription.getNotifyTime());
        rdbmsSubscription.setPrice(rdbmsSubscription.getPrice());

        rdbmsService.setName(service.getName());
        rdbmsService.setLink(service.getLink());
        rdbmsService.setFavicon(service.getFavicon());
        rdbmsService.setCategory(rdbmsCategory);

        rdbmsSubscription.setUser(rdbmsUser);
        rdbmsSubscription.setService(rdbmsService);

        return rdbmsSubscription;
    }
}
