package otus.nchuraeva.spring.task15;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import otus.nchuraeva.spring.task15.entity.Butterfly;

import java.util.Map;


public class ButterflyService {

    @ServiceActivator
    public void printButterfly(Butterfly payload, @Headers Map<String, Object> headerMap) {
        System.out.println(payload);
    }
}
