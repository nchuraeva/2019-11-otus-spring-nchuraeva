package otus.nchuraeva.spring.task15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import otus.nchuraeva.spring.task15.entity.Butterfly;
import otus.nchuraeva.spring.task15.entity.Caterpillar;

import java.util.concurrent.TimeUnit;


@Configuration
public class Config {

    @MessagingGateway(errorChannel = "errorChannel")
    public interface CaterpillarService {

        @Gateway(requestChannel = "transformCaterpillar.input")
        void caterpillar(Caterpillar order);
    }

    @Bean
    public Converter<Caterpillar, Butterfly> caterpillarButterflyConverter() {
        return new CaterpillarToButterflyConverter();
    }

    @Bean
    MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(com.mongodb.client.MongoClients.create(), "test1");
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }


    @Bean
    public IntegrationFlow transformCaterpillar(MongoTemplate mongoTemplate) {
        return f -> f
                .transform(Transformers.converter(caterpillarButterflyConverter()))
                .route(Butterfly::isFastTransforming, this::routeButterflies)
                .handle(mongoOutboundAdapter(mongoTemplate));
    }

    @Bean
    public MessageHandler mongoOutboundAdapter(MongoTemplate mongoTemplate) {
        MongoDbStoringMessageHandler mongoHandler = new MongoDbStoringMessageHandler(mongoTemplate);
        mongoHandler.setCollectionNameExpression(new LiteralExpression("butterfly"));
        return mongoHandler;
    }

    @Bean
    IntegrationFlow exceptionChannelFlow() {
        return IntegrationFlows.from(errorChannel())
                .handle(errorHandler(), "handle")
                .get();
    }

    @Bean
    MessageChannel errorChannel() {
        return MessageChannels.direct("transformCaterpillar.input").get();
    }

    @Bean
    public TransformProcess transformProcess() {
        return new TransformProcess();
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ErrorHandler();
    }

    @Bean
    public ButterflyService butterflyService() {
        return new ButterflyService();
    }

    @Bean
    public IntegrationFlow getButterfly(MongoTemplate mongoTemplate) {
        return IntegrationFlows.from(mongoMessageSource(mongoTemplate), c -> c.poller(Pollers.fixedDelay(3, TimeUnit.SECONDS)))
                .handle(butterflyService(), "printButterfly")
                .get();
    }

    private RouterSpec<Boolean, MethodInvokingRouter> routeButterflies(RouterSpec<Boolean, MethodInvokingRouter> mapping) {
        return mapping
                .subFlowMapping(true, sf -> sf.handle(transformProcess(), "fastProcess"))
                .subFlowMapping(false, sf -> sf.handle(transformProcess(), "process"));
    }

    @Bean
    public MessageSource<Object> mongoMessageSource(MongoTemplate mongoTemplate) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongoTemplate, new LiteralExpression("{'isFastTransforming' : true }"));
        messageSource.setCollectionNameExpression(new LiteralExpression("butterfly"));
        messageSource.setExpectSingleResult(true);
        messageSource.setEntityClass(Butterfly.class);
        return messageSource;
    }
}
