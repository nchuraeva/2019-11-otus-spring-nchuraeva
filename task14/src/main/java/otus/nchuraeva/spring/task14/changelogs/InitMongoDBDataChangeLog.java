package otus.nchuraeva.spring.task14.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.nchuraeva.spring.task14.nosql.document.Category;
import otus.nchuraeva.spring.task14.nosql.document.Service;
import otus.nchuraeva.spring.task14.nosql.document.Subscription;
import otus.nchuraeva.spring.task14.nosql.document.User;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@ChangeLog(order = "001")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitMongoDBDataChangeLog {

    private User savedUser;

    @ChangeSet(order = "000", id = "dropDB", author = "nadia", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "Admin", author = "nadia", runAlways = true)
    public void initUser(MongoTemplate template) {
        User user = User.builder()
                .email("admin@gmail.com")
                .username("admin")
                .password("$2y$12$tl1uJPpAUNaHkY7GZWAhO.9qSZTeCm5nzlMCmwjqtCdZEI2Qto4Hi")
                .build();

        savedUser = template.save(user);
    }

    @ChangeSet(order = "002", id = "Subscriptions", author = "nadia", runAlways = true)
    public void initUserSubscriptions(MongoTemplate template) {
        Subscription subscription = Subscription.builder()
                .user(savedUser)
                .price(BigDecimal.valueOf(199))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        template.save(subscription);
    }
}