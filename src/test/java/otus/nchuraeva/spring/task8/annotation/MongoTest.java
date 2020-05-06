package otus.nchuraeva.spring.task8.annotation;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"otus.nchuraeva.spring.task8.config", "otus.nchuraeva.spring.task8.repository", "otus.nchuraeva.spring.task8.event"})
public @interface MongoTest {
}
