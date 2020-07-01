package otus.nchuraeva.spring.task14.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsCategory;

public interface RdmsCategoryRepository extends JpaRepository<RdbmsCategory, Long> {
}