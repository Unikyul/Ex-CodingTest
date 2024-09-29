package com.example.demo.User;

import com.example.demo.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    // 유저 조회 테스트
    @Test
    public void testGetUser() throws Exception {
        // 1. given
        int id = 1;

        // 2. when
        mockMvc.perform(get("/users/" + id)) // 경로에 ID를 포함한 GET 요청
                // 3. then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("홍길동"));
    }

    // 유저 등록 테스트
    @Test
    public void testRegisterUser() throws Exception {
        // 1. given JoinDTO 객체 생성
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO("홍이동");

        // 객체를 JSON으로 변환
        String userJson = objectMapper.writeValueAsString(joinDTO);
        // 2. when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                // 3. then
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.id").exists()) // id가 존재하는지 확인
                .andExpect(jsonPath("$.name").value("홍이동")); // name 필드가 'John'인지 확인
    }

    // 유저 수정 테스트
    @Test
    public void testUpdateUser() throws Exception {
        // 1. given - 수정할 유저 정보
        UserRequest.UpdateDTO updateDTO = new UserRequest.UpdateDTO("updatedName");

        // 객체를 JSON으로 변환
        String updateUserJson = objectMapper.writeValueAsString(updateDTO);

        // 2. when - 수정 요청 실행
        mockMvc.perform(put("/users/1") // id가 1인 유저 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson)) // 수정할 데이터 전송
                // 3. then - 응답 결과 확인
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.id").value(1)) // id가 1인지 확인
                .andExpect(jsonPath("$.name").value("updatedName")); // 수정된 name 필드 확인
    }

}
