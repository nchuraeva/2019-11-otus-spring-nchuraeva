package otus.nchuraeva.spring.task14.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsSubscription;

public interface RdbmsSubscriptionRepository extends JpaRepository<RdbmsSubscription, Long> {
}