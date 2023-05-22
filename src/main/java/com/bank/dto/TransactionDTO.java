package com.bank.dto;

import com.bank.entity.TransactionEntity;
import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private Long cntOfMoney;
    private Long idUser;
    private Long bankId;

    public TransactionDTO(Long id, Long cntOfMoney, Long idUser, Long bankId) {
        this.id = id;
        this.cntOfMoney = cntOfMoney;
        this.idUser = idUser;
        this.bankId = bankId;
    }

    public static TransactionDTO toTransactionDTO(TransactionEntity transaction) {
        return new TransactionDTO(transaction.getId(), transaction.getCntOfMoney(), transaction.getUser().getId(), transaction.getBank().getId());
    }
}
