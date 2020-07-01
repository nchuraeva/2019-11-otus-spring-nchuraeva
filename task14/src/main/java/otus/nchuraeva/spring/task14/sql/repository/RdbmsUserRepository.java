package otus.nchuraeva.spring.task14.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.nchuraeva.spring.task14.sql.entity.RdbmsUser;

public interface RdbmsUserRepository  extends JpaRepository<RdbmsUser, Long> {
}