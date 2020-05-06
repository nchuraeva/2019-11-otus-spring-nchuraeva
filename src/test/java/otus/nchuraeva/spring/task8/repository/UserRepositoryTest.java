package otus.nchuraeva.spring.task8.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import otus.nchuraeva.spring.task8.annotation.MongoTest;
import otus.nchuraeva.spring.task8.document.Category;
import otus.nchuraeva.spring.task8.document.Service;
import otus.nchuraeva.spring.task8.document.Subscription;
import otus.nchuraeva.spring.task8.document.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@MongoTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @DisplayName("создание пользователя")
    @Test
    void shouldCreateUser() {
        val user = User.builder()
                .email("ivanov123@gmail.com")
                .login("ivanov1993")
                .password("1w2q3r4e5y6t")
                .build();

        val savedUser = userRepository.save(user);
        assertThat(savedUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @DisplayName("добавить подписку пользователя")
    @Test
    void shouldSaveUserSubscription() {
        val user = User.builder()
                .email("ivanov123@gmail.com")
                .login("ivanov1996")
                .password("1w2q3r4e5y6t")
                .build();
        val savedUser = userRepository.save(user);

        Subscription subscription = Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        userRepository.setSubscription(savedUser.getId(), subscription);
        val updateUser = userRepository.findUserByLogin(user.getLogin());
        val savedSubscription = updateUser.getSubscriptions().get(0);

        assertThat(savedSubscription).isNotNull();
    }

    @DisplayName("удалить подписку пользователя")
    @Test
    void shouldDeleteUserSubscription() {
        val user = User.builder()
                .email("ivanov123@gmail.com")
                .login("ivanov1997")
                .password("1w2q3r4e5y6t")
                .build();
        val savedUser = userRepository.save(user);

        Subscription subscription = Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        userRepository.setSubscription(savedUser.getId(), subscription);
        val updateUser = userRepository.findUserByLogin(user.getLogin());
        Subscription savedSubscription = updateUser.getSubscriptions().get(0);
        userRepository.deleteSubscription(savedUser.getId(), savedSubscription);
        assertThat(userRepository.findUserByLogin(user.getLogin()).getSubscriptions().size()).isEqualTo(0);
    }

    @DisplayName("получить подписку пользователя")
    @Test
    void shouldGetUserSubscription() {
        val user = User.builder()
                .email("ivanov123@gmail.com")
                .login("ivanov1998")
                .password("1w2q3r4e5y6t")
                .build();
        val savedUser = userRepository.save(user);

        Subscription oneSubscription = Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        Subscription twoSubscription = Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        userRepository.setSubscription(savedUser.getId(), oneSubscription);
        userRepository.setSubscription(savedUser.getId(), twoSubscription);

        User updateUser = userRepository.findUserByIdAndSubscriptionId(savedUser.getId(), oneSubscription.getId());
        Subscription subscription = null;
        if (updateUser != null) {
            subscription = updateUser.getSubscriptions().get(0);
        }
        assertThat(subscription).isNotNull();
        assertThat(subscription.getId()).isEqualTo(oneSubscription.getId());
    }

    @DisplayName("получить подписки пользователя")
    @Test
    void shouldGetUserSubscriptions() {
        val user = User.builder()
                .email("ivanov123@gmail.com")
                .login("ivanov1999")
                .password("1w2q3r4e5y6t")
                .build();
        val savedUser = userRepository.save(user);

        Subscription subscription = Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build();

        userRepository.setSubscription(savedUser.getId(), subscription);
        val updateUser = userRepository.findUserByLogin(user.getLogin());
        assertThat(userRepository.getSubscriptions(updateUser.getId())).isNotNull();
    }

    @DisplayName("удаление пользователя")
    @Test
    void shouldDeleteUser() {
        List<Subscription> subscriptions = new ArrayList<>(1);
        subscriptions.add(Subscription.builder()
                .id(ObjectId.get().toString())
                .price(BigDecimal.valueOf(189))
                .notifyTime(LocalDateTime.now())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .service(Service.builder().id(ObjectId.get().toString()).name("Кинопоиск").link("https://www.kinopoisk.ru/")
                        .category(Category.builder().id(ObjectId.get().toString()).name("Кино").build()).build())
                .build());

        val user = User.builder()
                .email("ivanov12@gmail.com")
                .login("ivanov1991")
                .password("1w2q3r4e5y6t")
                .subscriptions(subscriptions)
                .build();

        val savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());

        assertThat(userRepository.findUserByLogin("ivanov1991")).isNull();
    }
}
