package otus.nchuraeva.spring.task12.controller;


import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import otus.nchuraeva.spring.task12.document.Category;
import otus.nchuraeva.spring.task12.document.Service;
import otus.nchuraeva.spring.task12.document.Subscription;
import otus.nchuraeva.spring.task12.document.User;
import otus.nchuraeva.spring.task12.service.SubscriptionService;
import otus.nchuraeva.spring.task12.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private static final User user = User.builder()
            .id(ObjectId.get().toString())
            .email("admin@gmail.com")
            .username("admin")
            .password("admin")
            .build();

    private static final Subscription subscription = Subscription.builder()
            .id(ObjectId.get().toString())
            .user(user)
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

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    public void testShowSubscriptionPage() throws Exception {
        given(userService.findUserByUsername(user.getUsername())).willReturn(user);
        given(subscriptionService.findAllSubscriptions(user.getId())).willReturn(Collections.singletonList(subscription));
        List<Subscription> exceptedSubscriptions = Collections.singletonList(subscription);

        mockMvc.perform(get("/subscriptions?user=admin"))
                .andDo(print())
                .andExpect(view().name("subscriptions"))
                .andExpect(model().attribute("subscriptions", exceptedSubscriptions))
                .andExpect(status().isOk());

    }
}
