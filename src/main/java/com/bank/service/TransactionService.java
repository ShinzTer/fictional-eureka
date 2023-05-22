package com.bank.service;

import com.bank.dto.TransactionDTO;
import com.bank.entity.BankEntity;
import com.bank.entity.TransactionEntity;
import com.bank.entity.UserEntity;
import com.bank.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<List<TransactionDTO>> transactions() {
        return ResponseEntity.ok().body(transactionRepository.findAll().stream().map(TransactionDTO::toTransactionDTO).toList());
    }

    public ResponseEntity<TransactionDTO> transaction(Long transId) {
        return ResponseEntity.ok().body(TransactionDTO.toTransactionDTO(transactionRepository.findById(transId).orElseThrow()));
    }

    public ResponseEntity<TransactionDTO> removeTransaction(Long transId) {
        TransactionEntity transaction = transactionRepository.findById(transId).orElseThrow();
        BankEntity bank = transaction.getBank();
        UserEntity user = transaction.getUser();

        bank.getHistory().remove(transaction);
        user.getHistory().remove(transaction);
        transactionRepository.delete(transaction);

        return ResponseEntity.ok().body(TransactionDTO.toTransactionDTO(transaction));
    }
}
