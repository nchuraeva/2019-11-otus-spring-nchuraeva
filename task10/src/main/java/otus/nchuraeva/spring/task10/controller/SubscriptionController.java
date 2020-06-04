package otus.nchuraeva.spring.task10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.nchuraeva.spring.task10.document.Subscription;
import otus.nchuraeva.spring.task10.document.User;
import otus.nchuraeva.spring.task10.service.SubscriptionService;
import otus.nchuraeva.spring.task10.service.UserService;

import java.util.List;


@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController {

    private static final String ADMIN = "admin";

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @GetMapping(path = "/subscriptions")
    public List<Subscription> findAllSubscriptions() {
        return subscriptionService.findAllSubscriptions();
    }

    @PostMapping(path = "/subscriptions")
    public void addSubscription(@RequestBody Subscription subscription) {
        User user = userService.getUser(ADMIN);
        subscriptionService.addSubscription(user.getId(), subscription);
    }
    
    @GetMapping(path = "/subscriptions/{id}")
    public Subscription findSubscriptionById(@PathVariable("id") String id) {
        User user = userService.getUser(ADMIN);
        return subscriptionService.findUserAndSubscription(user.getId(), id);
    }

    @PutMapping(path = "/subscriptions/{id}")
    public void updateSubscription(@PathVariable("id") String id, @RequestBody Subscription subscription) {
        User user = userService.getUser(ADMIN);
        subscriptionService.updateSubscription(user.getId(), id, subscription);
    }

    @DeleteMapping(value = "/subscriptions/{id}")
    public void deleteSubscription(@PathVariable("id") String id) {
        User user = userService.getUser(ADMIN);
        Subscription subscription = subscriptionService.findUserAndSubscription(user.getId(), id);
        subscriptionService.deleteSubscription(user.getId(), subscription);
    }
}
