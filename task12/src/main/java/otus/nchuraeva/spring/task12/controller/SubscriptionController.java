package otus.nchuraeva.spring.task12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otus.nchuraeva.spring.task12.document.Subscription;
import otus.nchuraeva.spring.task12.document.User;
import otus.nchuraeva.spring.task12.service.SubscriptionService;
import otus.nchuraeva.spring.task12.service.UserService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @GetMapping(path = {"/","/subscriptions"})
    public String showSubscriptions(@RequestParam("user") String userName,  ModelMap model) {
        User user = userService.findUserByUsername(userName);
        model.addAttribute("subscription",  new Subscription());
        model.addAttribute("username",  userName);
        model.addAttribute("subscriptions",  subscriptionService.findAllSubscriptions(user.getId()));

        return "subscriptions";
    }

    @PostMapping(path = "/subscriptions")
    public String saveSubscription(@RequestParam("user") String userName, Subscription subscription, final ModelMap model) {
        User user = userService.findUserByUsername(userName);
        subscription.setUser(user);
        subscriptionService.addSubscription(subscription);
        model.clear();

        return "redirect:/subscriptions?user=" + userName;
    }

    @GetMapping(path = "/subscriptions/edit/{id}")
    public String showEditSubscription(@PathVariable("id") String id, ModelMap model) {
        model.addAttribute("subscription", subscriptionService.findSubscriptionById(id));

        return "editSubscription";
    }

    @PostMapping(path = "/subscriptions/edit/{id}")
    public String saveEditSubscription(@PathVariable("id") String id, Subscription updateSubscription, ModelMap model) {
        Subscription subscription = subscriptionService.findSubscriptionById(id);
        subscriptionService.updateSubscription(id, updateSubscription);
        model.clear();

        return "redirect:/subscriptions?user=" + subscription.getUser().getUsername();
    }

    @GetMapping(value = "/subscriptions/remove/{id}")
    public String deleteSubscription(@PathVariable("id") String id) {
        Subscription subscription = subscriptionService.findSubscriptionById(id);
        subscriptionService.deleteSubscription(id);

        return "redirect:/subscriptions?user=" + subscription.getUser().getUsername();
    }
}
