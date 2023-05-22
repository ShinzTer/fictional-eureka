package com.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bankUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String passport;
    private String phNum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private BankEntity bank;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TransactionEntity> history = new ArrayList<>();
}
