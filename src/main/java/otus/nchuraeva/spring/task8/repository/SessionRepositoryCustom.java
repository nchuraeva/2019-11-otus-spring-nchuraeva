package otus.nchuraeva.spring.task8.repository;


public interface SessionRepositoryCustom {
    void removeSessionsByUserId(String id);
}
