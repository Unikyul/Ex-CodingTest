package com.example.demo.user;

import com.example.demo.core.ex.ExceptionApi400;
import com.example.demo.core.ex.ExceptionApi404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 유저 조회
    @Transactional(readOnly = true)
    public UserResponse.listDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ExceptionApi404("ID X"));
        return UserResponse.listDTO.loadList(user);
    }

    // 유저 등록
    public User registerUser(UserRequest.JoinDTO joinDTO) {
        return userRepository.save(joinDTO.regUser());
    }

    //유저 수정
    @Transactional(readOnly = true)
    public UserResponse.listDTO updateUser(Integer id, UserRequest.UpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ExceptionApi404("ID X"));
        user.setName(updateDTO.getName());
        User updatedUser = userRepository.save(user);
        return UserResponse.listDTO.loadList(updatedUser);
    }

}
