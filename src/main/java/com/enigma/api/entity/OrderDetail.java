package com.enigma.api.entity;

import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_order_detail")
@EntityListeners({AuditingEntityListener.class})
public class OrderDetail {


    @Id
    @GenericGenerator(strategy = "uuid2", name="system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "bill_detail_id")
    private String billDetailId;

    private Integer quantity;

    @Column(name = "total_sales")
    private BigDecimal totalSales;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Transaction bill;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

}

