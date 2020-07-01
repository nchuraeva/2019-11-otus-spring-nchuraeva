package otus.nchuraeva.spring.task14.sql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "subscriptions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RdbmsSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "startedAt")
    private LocalDateTime startedAt;

    @Column(name = "endedAt")
    private LocalDateTime endedAt;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "notifyTime")
    private LocalDateTime notifyTime;

    @OneToOne(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private RdbmsService service;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private RdbmsUser user;
}
