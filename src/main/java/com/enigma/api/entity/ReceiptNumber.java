package com.enigma.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_receipt_number")
public class ReceiptNumber {

    @Id
    @GenericGenerator(strategy = "uuid2", name="system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "sequence_id")
    private String sequenceId;

    @Column(name = "branch_code")
    private String branchCode;

    private Integer year;

    @Column(name = "current_value")
    private Integer currentValue;
}
