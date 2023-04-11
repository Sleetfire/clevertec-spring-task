package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.dto.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(schema = "ecl", name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            schema = "ecl",
            name = "gift_certificates_orders",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")}
    )
    List<GiftCertificate> giftCertificates;
    private BigDecimal cost;
    @Column(name = "create_date")
    private String createDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Override
    public Long getId() {
        return this.id;
    }
}
