package com.bank.dto;

import com.bank.entity.BankEntity;
import com.bank.entity.TransactionEntity;
import com.bank.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class BankDTO {
    private Long id;
    private String name;
    private Long cntOfMoney;
    private Long cntEmpl;
    private List<Long> usersId;
    private List<Long> transactionsId;

    public BankDTO(Long id, String name, Long cntOfMoney, Long cntEmpl, List<Long> usersId, List<Long> transactionsId) {
        this.id = id;
        this.name = name;
        this.cntOfMoney = cntOfMoney;
        this.cntEmpl = cntEmpl;
        this.usersId = usersId;
        this.transactionsId = transactionsId;
    }

    public static BankDTO toBankDto(BankEntity bank) {
        return new BankDTO(bank.getId(), bank.getName(), bank.getCntOfMoney(), bank.getCntEmpl(),
                bank.getBankUsers().stream().map(UserEntity::getId).toList(), bank.getHistory().stream().map(TransactionEntity::getId).toList());
    }
}
