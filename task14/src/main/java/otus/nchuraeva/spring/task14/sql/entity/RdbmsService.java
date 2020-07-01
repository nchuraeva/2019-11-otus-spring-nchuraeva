package otus.nchuraeva.spring.task14.sql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import otus.nchuraeva.spring.task14.nosql.document.Category;
import otus.nchuraeva.spring.task14.nosql.document.Subscription;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "services")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RdbmsService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "favicon")
    private String favicon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private RdbmsSubscription subscription;

    @OneToOne(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private RdbmsCategory category;
}
