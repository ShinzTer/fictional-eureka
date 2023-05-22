package com.bank.dto;

import com.bank.entity.TransactionEntity;
import com.bank.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String fio;
    private Long bankId;
    private List<Long> idTransactions;

    public UserDTO(Long id, String fio, Long bankId, List<Long> idTransactions) {
        this.id = id;
        this.fio = fio;
        this.bankId = bankId;
        this.idTransactions = idTransactions;
    }

    public static UserDTO toUserDto(UserEntity user) {
        return new UserDTO(user.getId(), user.getFio(), user.getBank().getId(), user.getHistory().stream().map(TransactionEntity::getId).toList());
    }
}
