package otus.nchuraeva.spring.task8.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.nchuraeva.spring.task8.document.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private User savedUser;

    @ChangeSet(order = "000", id = "dropDB", author = "nadia", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "User", author = "nadia", runAlways = true)
    public void initUser(MongoTemplate template) {
        List<Subscription> subscriptions = new ArrayList<>(1);
        subscriptions.add(Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(199))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build());

        val user = User.builder()
                .email("nchuraeva123@gmail.com")
                .login("nchuraeva")
                .password("123456")
                .subscriptions(subscriptions)
                .build();

        savedUser = template.save(user);
    }

    @ChangeSet(order = "002", id = "Session", author = "nadia", runAlways = true)
    public void initSession(MongoTemplate template) {
        val session = Session.builder()
                .fingerprint("Уникальный идентификатор устройства")
                .user(savedUser)
                .createAt(LocalDateTime.now())
                .expiresIn(100l)
                .ip("ip ресурса")
                .userAgent("Название и версия приложения")
                .refreshToken("1234rghjkk")
                .build();

        template.save(session);
    }
}
