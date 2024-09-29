package com.example.demo.user;

import jakarta.validation.Valid; // 유효성 검사를 위해 필요
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 유저 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse.listDTO> getUserById(@PathVariable Integer id) {
        UserResponse.listDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // 유저 등록 API
    @PostMapping
    public ResponseEntity<UserResponse.listDTO> registerUser(@Valid @RequestBody UserRequest.JoinDTO joinDTO) {
        User newUser = userService.registerUser(joinDTO);
        UserResponse.listDTO response = new UserResponse.listDTO(newUser.getId(), newUser.getName());
        return ResponseEntity.ok(response);
    }

    // 유저 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse.listDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserRequest.UpdateDTO updateDTO) {
        UserResponse.listDTO updatedUser = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //application.properties에서 안되길래 수동으로 넣어봤습니다...
    @GetMapping("/test404")
    public ResponseEntity<?> test404() throws NoHandlerFoundException {
        throw new NoHandlerFoundException("GET", "/invalid-url", HttpHeaders.EMPTY);
    }
}