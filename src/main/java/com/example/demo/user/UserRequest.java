package com.example.demo.user;

import jakarta.validation.constraints.NotNull; // 이 패키지로 변경
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {

    // 유저 등록 DTO
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class JoinDTO {
        @NotNull(message = "실제사유") // 이름이 비어 있을 경우 검증 오류 발생
        private String name;

        public User regUser() {
            return User.builder().name(name).build();
        }
    }

    // 유저 수정 DTO
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateDTO {
        private String name;
    }
}