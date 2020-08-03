package otus.nchuraeva.spring.task15;


import org.springframework.messaging.Message;

public class ErrorHandler {

    public void handle(Message<Exception> message) {
        System.out.println("Error: "+ message.getPayload().getCause().getMessage());
    }
}
