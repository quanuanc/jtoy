package dev.cheng.controller;

import dev.cheng.controller.vo.User;
import dev.cheng.mapper.UserMapper;
import dev.cheng.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        List<User> collect = userRepository.findAll().stream()
                .map(userMapper::toVo)
                .toList();
        return ResponseEntity.ok(collect);
    }
}
