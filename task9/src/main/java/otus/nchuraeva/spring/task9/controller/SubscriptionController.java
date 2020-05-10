package otus.nchuraeva.spring.task9.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import otus.nchuraeva.spring.task9.document.Subscription;
import otus.nchuraeva.spring.task9.document.User;
import otus.nchuraeva.spring.task9.service.SubscriptionService;
import otus.nchuraeva.spring.task9.service.UserService;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController {

    private static final String ADMIN = "admin";

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @GetMapping(path = {"/","/subscriptions"})
    public String showSubscriptions(ModelMap model) {
        model.addAttribute("subscription",  new Subscription());
        model.addAttribute("subscriptions",  subscriptionService.findAllSubscriptions());
        return "subscriptions";
    }

    @PostMapping(path = "/subscriptions")
    public String saveSubscription(final Subscription subscription, final ModelMap model) {
        User user = userService.getUser(ADMIN);
        subscriptionService.addSubscription(user.getId(), subscription);
        model.clear();

        return "redirect:/subscriptions";
    }

    @GetMapping(path = "/subscriptions/edit/{id}")
    public String showEditSubscription(@PathVariable("id") String id, ModelMap model) {
        User user = userService.getUser(ADMIN);
        model.addAttribute("subscription", subscriptionService.findUserAndSubscription(user.getId(), id));

        return "editSubscription";
    }

    @PostMapping(path = "/subscriptions/edit/{id}")
    public String saveEditSubscription(@PathVariable("id") String id, Subscription subscription, ModelMap model) {
        User user = userService.getUser(ADMIN);
        subscriptionService.updateSubscription(user.getId(), subscription);
        model.clear();

        return "redirect:/subscriptions";
    }

    @GetMapping(value = "/subscriptions/remove/{id}")
    public String deleteSubscription(@PathVariable("id") String id) {
        User user = userService.getUser(ADMIN);
        Subscription subscription = subscriptionService.findUserAndSubscription(user.getId(), id);
        subscriptionService.deleteSubscription(user.getId(), subscription);

        return "redirect:/subscriptions";
    }
}
