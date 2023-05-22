package com.bank.controller;

import com.bank.dto.BankDTO;
import com.bank.dto.UserDTO;
import com.bank.entity.BankEntity;
import com.bank.service.BankService;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BankDTO>> banks() {
        return bankService.banks();
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<BankDTO> bank(@PathVariable Long bankId) {
        return bankService.bank(bankId);
    }

    //сложный метод с использованием данных из нескольких таблиц (получаение пользователей у которых сумма задолженности больше или равно введенному значению)
    @GetMapping("/usersWithBigDuty/{bankId}/{sum}")
    public ResponseEntity<List<UserDTO>> getUsersWithDutyMoreThenSum(@PathVariable Long bankId, @PathVariable Long sum) {
        return bankService.getUsersWithDutyMoreThenSum(bankId, sum);
    }

    @PostMapping("/")
    public ResponseEntity<BankDTO> createBank(@RequestBody BankEntity bank) {
        return bankService.createBank(bank);
    }

    @PatchMapping("/{bankId}")
    public ResponseEntity<BankDTO> updateBankInfo(@RequestBody BankEntity bank, @PathVariable Long bankId) {
        return bankService.updateBankInfo(bank, bankId);
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<BankDTO> removeBank(@PathVariable Long bankId) {
        return bankService.removeBank(bankId);
    }

}
