package com.bank.controller;

import com.bank.dto.UserDTO;
import com.bank.entity.UserEntity;
import com.bank.service.UserService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> users() {
        return userService.users();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> user(@PathVariable Long userId) {
        return userService.user(userId);
    }

    @PostMapping("/{userId}/{sum}")
    public ResponseEntity<String> makeTransaction(@PathVariable Long userId, @PathVariable Long sum) {
        return userService.makeTransaction(userId, sum);
    }

    @PostMapping("/{bankId}")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserEntity user, @PathVariable Long bankId) {
        return userService.addUser(user, bankId);
    }

    //вычисляемое поле (сумма долга пользователя банку)
    @GetMapping("/duty/{userId}")
    public ResponseEntity<String> getDutyByUserId(@PathVariable Long userId) {
        return userService.getDutyByUserId(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserEntity user, @PathVariable Long userId) {
        return userService.updateUserInfo(user, userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> removeUser(@PathVariable Long userId) {
        return userService.removeUser(userId);
    }
}