package com.enigma.api.entity;

import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EntityListeners({AuditingEntityListener.class})
@Table(name = "m_branch")
public class Branch {

    @Id
    @GenericGenerator(strategy = "uuid2", name="system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "branch_code", nullable = false, unique = true)
    private String branchCode;

    @Column(name = "branch_name", nullable = false, unique = true)
    private String branchName;

    private String address;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "branch")
    private List<Product> products;
}
