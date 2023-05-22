package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cntOfMoney;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private BankEntity bank;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserEntity user;

    public TransactionEntity(Long cntOfMoney, BankEntity bank, UserEntity user) {
        this.cntOfMoney = cntOfMoney;
        this.bank = bank;
        this.user = user;
    }
}
