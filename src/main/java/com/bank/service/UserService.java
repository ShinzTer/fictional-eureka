package com.bank.service;

import com.bank.dto.UserDTO;
import com.bank.entity.BankEntity;
import com.bank.entity.TransactionEntity;
import com.bank.entity.UserEntity;
import com.bank.repository.BankRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository, BankRepository bankRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<List<UserDTO>> users() {
        return ResponseEntity.ok().body(userRepository.findAll().stream().map(UserDTO::toUserDto).toList());
    }

    public ResponseEntity<UserDTO> user(Long userId) {
        return userRepository.findById(userId).isPresent() ? ResponseEntity.ok().body(UserDTO.toUserDto(userRepository.findById(userId).get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<UserDTO> addUser(UserEntity user, Long id) {
        Optional<BankEntity> bank = bankRepository.findById(id);
        if (bank.isEmpty()) {
            throw new EntityNotFoundException("Банка с id равным " + id + " не существует!");
        }
        user.setBank(bank.get());
        bank.get().getBankUsers().add(user);
        return ResponseEntity.ok().body(UserDTO.toUserDto(userRepository.save(user)));
    }

    public ResponseEntity<String> makeTransaction(Long userId, Long sum) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        BankEntity bank = bankRepository.findById(user.getBank().getId()).orElseThrow();

        if (bank.getCntOfMoney() >= sum) {
            TransactionEntity transaction = new TransactionEntity(sum, bank, user);
            bank.setCntOfMoney(bank.getCntOfMoney() - sum);
            user.getHistory().add(transaction);
            bank.getHistory().add(transaction);

            transactionRepository.save(transaction);

            return ResponseEntity.ok().body("Транзакция успешно прошла!");
        } else {
            return ResponseEntity.badRequest().body("В банке недостаточно денег!");
        }
    }

    public ResponseEntity<UserDTO> updateUserInfo(UserEntity chUser, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        if(chUser.getFio() != null) {
            user.setFio(chUser.getFio());
        }
        if(chUser.getPhNum() != null) {
            user.setPhNum(chUser.getPhNum());
        }
        if(chUser.getPassport() != null) {
            user.setPassport(chUser.getPassport());
        }
        return ResponseEntity.ok().body(UserDTO.toUserDto(user));
    }

    public ResponseEntity<String> getDutyByUserId(Long userId) {
        return ResponseEntity.ok().body("Сумма долга составляет: " + userRepository.findById(userId).orElseThrow().getHistory().stream().map(TransactionEntity::getCntOfMoney).reduce(Long::sum).get());
    }

    public ResponseEntity<UserDTO> removeUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        BankEntity bank = user.getBank();

        bank.getBankUsers().remove(user);
        bank.getHistory().removeAll(user.getHistory());
        userRepository.delete(user);

        return ResponseEntity.ok().body(UserDTO.toUserDto(user));
    }
}
