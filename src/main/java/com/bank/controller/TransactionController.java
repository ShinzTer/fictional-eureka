package com.bank.controller;

import com.bank.dto.TransactionDTO;
import com.bank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDTO>> transactions() {
        return transactionService.transactions();
    }

    @GetMapping("/{transId}")
    public ResponseEntity<TransactionDTO> transaction(@PathVariable Long transId) {
        return transactionService.transaction(transId);
    }

    @DeleteMapping("{transId}")
    public ResponseEntity<TransactionDTO> removeTransaction(@PathVariable Long transId) {
        return transactionService.removeTransaction(transId);
    }
}
