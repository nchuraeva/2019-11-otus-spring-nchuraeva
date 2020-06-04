package otus.nchuraeva.spring.task10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import otus.nchuraeva.spring.task10.document.Category;
import otus.nchuraeva.spring.task10.document.Service;
import otus.nchuraeva.spring.task10.document.Subscription;
import otus.nchuraeva.spring.task10.document.User;
import otus.nchuraeva.spring.task10.service.SubscriptionService;
import otus.nchuraeva.spring.task10.service.UserService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    private static final Subscription subscription = Subscription.builder()
            .id(ObjectId.get().toString())
            .price(BigDecimal.valueOf(199))
            .service(Service.builder()
                    .id(ObjectId.get().toString())
                    .name("Kinopoisk")
                    .link("https://www.kinopoisk.ru/")
                    .category(Category.builder()
                            .id(ObjectId.get().toString())
                            .name("Kino")
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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAllSubscriptions() throws Exception {
        given(subscriptionService.findAllSubscriptions())
                .willReturn(Collections.singletonList(subscription));
        List<Subscription> exceptedSubscriptions = Collections.singletonList(subscription);

        mockMvc.perform(get("/api/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subscription)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(exceptedSubscriptions)));;

    }

    @Test
    public void testUpdateSubscription() throws Exception {
        Mockito.doNothing().when(subscriptionService).updateSubscription("1", subscription.getId(), subscription);
        given(userService.getUser("admin")).willReturn(user);

        mockMvc.perform(put("/api/subscriptions/" + subscription.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(subscription)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSubscription() throws Exception {
        Mockito.doNothing().when(subscriptionService).deleteSubscription("1", subscription);
        given(userService.getUser("admin")).willReturn(user);
        given(subscriptionService.findUserAndSubscription("1", subscription.getId()))
                .willReturn(subscription);

        mockMvc.perform(delete("/api/subscriptions/{id}", subscription.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
