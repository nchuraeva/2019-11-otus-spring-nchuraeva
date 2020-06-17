package otus.nchuraeva.spring.task11.rest;


import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import otus.nchuraeva.spring.task11.document.User;

import otus.nchuraeva.spring.task11.dto.UserCreateRequest;
import otus.nchuraeva.spring.task11.dto.UserResponse;
import otus.nchuraeva.spring.task11.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RestRouter.class, UserHandler.class, SubscriptionHandler.class})
@WebFluxTest
public class TestUserHandler {

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
    public void testGetUsers() {

        User user1 = new User();
        user1.setPassword("password");
        user1.setLogin("login");
        user1.setEmail("admin@example.com");

        User user2 = new User();
        user2.setPassword("password");
        user2.setLogin("login");
        user2.setEmail("example@example.com");

        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponse.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getLogin()).isEqualTo("login");
                            Assertions.assertThat(userResponse.get(0).getEmail()).isEqualTo("admin@example.com");
                            Assertions.assertThat(userResponse.get(1).getLogin()).isEqualTo("login");
                            Assertions.assertThat(userResponse.get(1).getEmail()).isEqualTo("example@example.com");
                        }
                );
    }

    @Test
    public void testCreateUser() {

        User user = new User();
        user.setPassword("password");
        user.setLogin("login");
        user.setEmail("admin@example.com");

        UserCreateRequest request = new UserCreateRequest();
        request.setPassword("password");
        request.setLogin("login");
        request.setEmail("admin@example.com");

        Mono<User> userMono = Mono.just(user);
        when(userRepository.save(user)).thenReturn(userMono);

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body( Mono.just(request), UserCreateRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getLogin()).isEqualTo("login");
                            Assertions.assertThat(userResponse.getEmail()).isEqualTo("admin@example.com");
                        }
                );
    }
}
