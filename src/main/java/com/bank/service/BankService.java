package com.bank.service;

import com.bank.dto.BankDTO;
import com.bank.dto.UserDTO;
import com.bank.entity.BankEntity;
import com.bank.entity.TransactionEntity;
import com.bank.entity.UserEntity;
import com.bank.repository.BankRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BankService(BankRepository bankRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<List<BankDTO>> banks() {
        return ResponseEntity.ok().body(bankRepository.findAll().stream().map(BankDTO::toBankDto).toList());
    }

    public ResponseEntity<BankDTO> bank(Long bankId) {
        return ResponseEntity.ok().body(BankDTO.toBankDto(bankRepository.findById(bankId).orElseThrow()));
    }

    // Исп данные из нескольких таблиц
    public ResponseEntity<List<UserDTO>> getUsersWithDutyMoreThenSum(Long bankId, Long sum) {
        List<UserEntity> bankUsers = bankRepository.findById(bankId).orElseThrow().getBankUsers();
        List<UserDTO> searchedUsers = new ArrayList<>();
        bankUsers.forEach(user -> {
            if(user.getHistory().stream().map(TransactionEntity::getCntOfMoney).reduce(Long::sum).get() >= sum) {
                searchedUsers.add(UserDTO.toUserDto(user));
            }
        });
        return ResponseEntity.ok().body(searchedUsers);
    }
    public ResponseEntity<BankDTO> createBank(BankEntity bank) {
        return ResponseEntity.ok().body(BankDTO.toBankDto(bankRepository.save(bank)));
    }

    public ResponseEntity<BankDTO> updateBankInfo(BankEntity chBank, Long bankId) {
        BankEntity bank = bankRepository.findById(bankId).orElseThrow();
        if(chBank.getName() != null) {
            bank.setName(chBank.getName());
        }
        if(chBank.getCntEmpl() != null) {
            bank.setCntEmpl(chBank.getCntEmpl());
        }
        if(chBank.getCntOfMoney() != null) {
            bank.setCntOfMoney(chBank.getCntOfMoney());
        }
        return ResponseEntity.ok().body(BankDTO.toBankDto(bank));
    }

    public ResponseEntity<BankDTO> removeBank(Long bankId) {
        BankEntity bank = bankRepository.findById(bankId).orElseThrow();
        bankRepository.delete(bank);
        return ResponseEntity.ok().body(BankDTO.toBankDto(bank));
    }
}
