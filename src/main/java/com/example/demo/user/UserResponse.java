package com.example.demo.user;


import lombok.Data;

@Data
public class UserResponse {

    //유저 정보 조회 DTO
    @Data
    public static class listDTO {
        private Integer id;
        private String name;

        public listDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        // User 엔티티를 받아서 DTO로 변환하는 메서드
        public static listDTO loadList(User user) {
            return new listDTO(user.getId(), user.getName());
        }
    }
}


