package otus.nchuraeva.spring.task11.rest;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.nchuraeva.spring.task11.document.Category;
import otus.nchuraeva.spring.task11.document.Service;
import otus.nchuraeva.spring.task11.document.Subscription;
import otus.nchuraeva.spring.task11.document.User;
import otus.nchuraeva.spring.task11.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestSubscriptionHandler {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private UserRepository userRepository;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testAddSubscription() {
       Subscription subscription = new Subscription();
       subscription.setUserId("1");
       subscription.setId(ObjectId.get().toString());
       subscription.setPrice(BigDecimal.valueOf(199));

       Service service = new Service();
       service.setLink("https://www.kinopoisk.ru/");
       service.setName("Kinopoisk");
       subscription.setService(service);

       Category category = new Category();
       category.setName("Kino");
       service.setCategory(category);

        when(userRepository.addSubscription(subscription.getUserId(), subscription)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/subscriptions")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(subscription), Subscription.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testFindAllSubscription() {
        User user = new User();
        user.setId("1");
        user.setPassword("password");
        user.setLogin("login");
        user.setEmail("admin@example.com");

        Subscription subscription = new Subscription();
        subscription.setUserId("1");
        subscription.setId(ObjectId.get().toString());
        subscription.setPrice(BigDecimal.valueOf(199));

        Service service = new Service();
        service.setLink("https://www.kinopoisk.ru/");
        service.setName("Kinopoisk");
        subscription.setService(service);

        Category category = new Category();
        category.setName("Kino");
        service.setCategory(category);

        user.setSubscriptions(Collections.singletonList(subscription));

        when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/api/subscriptions" +"?userId=" + user.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Subscription.class)
                .value(subscriptions -> Assert.assertEquals(1, subscriptions.size()));
    }


    @Test
    public void testFindSubscription() {
        Subscription subscription = new Subscription();
        subscription.setUserId("1");
        subscription.setId(ObjectId.get().toString());
        subscription.setPrice(BigDecimal.valueOf(199));

        Service service = new Service();
        service.setLink("https://www.kinopoisk.ru/");
        service.setName("Kinopoisk");
        subscription.setService(service);

        Category category = new Category();
        category.setName("Kino");
        service.setCategory(category);

        when(userRepository.findUserByIdAndSubscriptionId(subscription.getUserId(), subscription.getId())).thenReturn(Mono.just(subscription));

        webTestClient.get()
                .uri("/api/subscriptions/" + subscription.getId() + "?userId=" + subscription.getUserId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Subscription.class)
                .value(findSubscription -> {
                            Assertions.assertThat(subscription.getId()).isEqualTo(findSubscription.getId());
                            Assertions.assertThat(subscription.getPrice()).isEqualTo(findSubscription.getPrice());
                        }
                );

    }
}