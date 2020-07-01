package otus.nchuraeva.spring.task14.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsService;

public interface RdmsServiceRepository extends JpaRepository<RdbmsService, Long> {
}