package com.enigma.api.entity;

import com.enigma.api.entity.enums.TransEnum;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "m_order")
@EntityListeners({AuditingEntityListener.class})
public class Transaction {

    @Id
    @GenericGenerator(strategy = "uuid2", name="system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "bill_id")
    private String billId;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "trans_date")
    private LocalDateTime transDate;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransEnum transactionType;

    @OneToMany(mappedBy = "bill")
    private List<OrderDetail> billDetails;
}
