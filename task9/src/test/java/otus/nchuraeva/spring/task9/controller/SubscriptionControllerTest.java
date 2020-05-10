package otus.nchuraeva.spring.task9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import otus.nchuraeva.spring.task9.document.Category;
import otus.nchuraeva.spring.task9.document.Service;
import otus.nchuraeva.spring.task9.document.Subscription;
import otus.nchuraeva.spring.task9.document.User;
import otus.nchuraeva.spring.task9.service.SubscriptionService;
import otus.nchuraeva.spring.task9.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    private static final Subscription subscription = Subscription.builder()
            .id(ObjectId.get().toString())
            .price(BigDecimal.valueOf(199))
            .notifyTime(LocalDateTime.now())
            .startedAt(LocalDateTime.now())
            .endedAt(LocalDateTime.now())
            .service(Service.builder()
                    .id(ObjectId.get().toString())
                    .name("Кинопоиск")
                    .link("https://www.kinopoisk.ru/")
                    .category(Category.builder()
                            .id(ObjectId.get().toString())
                            .name("Кино")
                            .build())
                    .build())
            .build();

    private static final User user = User.builder()
            .email("admin@gmail.com")
            .login("admin")
            .subscriptions(Collections.singletonList(subscription))
            .build();

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowPage() throws Exception {
        given(subscriptionService.findAllSubscriptions())
                .willReturn(Collections.singletonList(subscription));
        List<Subscription> exceptedSubscriptions = Collections.singletonList(subscription);

        mockMvc.perform(get("/subscriptions"))
                .andDo(print())
                .andExpect(view().name("subscriptions"))
                .andExpect(model().attribute("subscriptions", exceptedSubscriptions))
                .andExpect(status().isOk());

    }

    @Test
    public void testEditPage() throws Exception {
        Mockito.doNothing().when(subscriptionService).updateSubscription("1", subscription);
        given(userService.getUser("admin")).willReturn(user);

        mockMvc.perform(post("/subscriptions/edit/{id}", subscription.getId())
                .content(asJsonString(subscription))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/subscriptions"));

    }

    @Test
    public void testDeletePage() throws Exception {
        Mockito.doNothing().when(subscriptionService).deleteSubscription("1", subscription);
        given(userService.getUser("admin")).willReturn(user);
        given(subscriptionService.findUserAndSubscription("1", subscription.getId()))
                .willReturn(subscription);

        mockMvc.perform(get("/subscriptions/remove/{id}", subscription.getId()))
                .andDo(print())
                .andExpect(redirectedUrl("/subscriptions"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
