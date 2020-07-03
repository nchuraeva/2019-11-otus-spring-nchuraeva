package otus.nchuraeva.spring.task13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import otus.nchuraeva.spring.task13.document.Subscription;
import otus.nchuraeva.spring.task13.document.User;
import otus.nchuraeva.spring.task13.service.SubscriptionService;
import otus.nchuraeva.spring.task13.service.UserService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController {


    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = {"/","/subscriptions"})
    public String showSubscriptions(ModelMap model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("subscription",  new Subscription());
        model.addAttribute("subscriptions",  subscriptionService.findAllSubscriptions(user.getId()));

        return "subscriptions";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path ="/allSubscriptions")
    public String showAllSubscriptions(ModelMap model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        model.addAttribute("subscription",  new Subscription());
        model.addAttribute("subscriptions",  subscriptionService.findAllSubscriptions());

        return "subscriptions";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(path = "/subscriptions")
    public String saveSubscription(Subscription subscription, final ModelMap model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        User user = userService.findUserByUsername(userDetails.getUsername());
        subscription.setUser(user);
        subscriptionService.addSubscription(subscription);
        model.clear();

        return "redirect:/subscriptions";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/subscriptions/edit/{id}")
    public String showEditSubscription(@PathVariable("id") String id, ModelMap model) {
        model.addAttribute("subscription", subscriptionService.findSubscriptionById(id));

        return "editSubscription";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(path = "/subscriptions/edit/{id}")
    public String saveEditSubscription(@PathVariable("id") String id, Subscription updateSubscription, ModelMap model) {
        Subscription subscription = subscriptionService.findSubscriptionById(id);
        subscriptionService.updateSubscription(id, updateSubscription);
        model.clear();

        return "redirect:/subscriptions";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/subscriptions/remove/{id}")
    public String deleteSubscription(@PathVariable("id") String id) {
        Subscription subscription = subscriptionService.findSubscriptionById(id);
        subscriptionService.deleteSubscription(id);

        return "redirect:/subscriptions";
    }
}
