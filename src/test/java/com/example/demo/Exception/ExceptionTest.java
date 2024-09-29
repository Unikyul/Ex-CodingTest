package com.example.demo.Exception;

import com.example.demo.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    // 잘못된 요청(필수 필드 누락)에 대한 400 에러 처리 테스트
    @Test
    public void testBadRequest() throws Exception {
        // 1. Given - 필수 필드(name)가 누락된 JSON 데이터 생성
        String invalidJson = objectMapper.writeValueAsString(new UserRequest.JoinDTO(null));

        // 2. When - POST 요청을 보내서 잘못된 데이터를 전송
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))

                // 3. Then - 기대하는 응답 상태 및 메시지 확인
                .andExpect(status().isBadRequest()) // 400 상태 코드 확인
                .andDo(print()) // 응답 본문 출력
                .andExpect(jsonPath("$.status").value(400)) // 응답의 status 필드 확인
                .andExpect(jsonPath("$.msg").value("실제사유")); // 응답의 msg 필드 확인
    }

    // 이거 에러테스트는 작동을 안 합니다..
    // GPT: Spring Boot의 기본 설정에서 404 Not Found 대신 400 Bad Request를 반환하는 이유일 수 있으며, 설정 및 예외 핸들러가 올바르게 적용되지 않았을 가능성이 큽니다.
    //application.properties 에서 오류가 나서 이 코드는 작동을 안합니다.
    //spring.mvc.throw-exception-if-no-handler-found=true
    //spring.web.resources.add-mappings=false
    //대신 밑에 Controller에 넣어서 작동되게 하였습니다.
    @Test
    public void testNotFound() throws Exception {
        // 1. Given - 존재하지 않는 API 경로 설정
        String invalidUrl = "/invalid-api";

        // 2. When - 존재하지 않는 API로 GET 요청을 보냄
        mockMvc.perform(get(invalidUrl))

                // 3. Then - 404 상태 코드와 에러 메시지 확인
                .andExpect(status().isNotFound()) // 404 상태 코드 확인
                .andExpect(jsonPath("$.status").value(404)) // 응답의 status 필드 확인
                .andExpect(jsonPath("$.msg").value("실제사유")); // 응답의 msg 필드 확인
    }

    // 존재하지 않는 API 호출 시 404 에러 수동 처리 테스트
    @Test
    public void testCustom404Exception() throws Exception {
        // 1. Given - 존재하지 않는 URL에 대한 테스트 URL 설정
        String testUrl = "/test404";

        // 2. When - /test404 URL로 GET 요청을 보냄
        mockMvc.perform(get(testUrl))

                // 3. Then - 404 상태 코드 및 메시지 확인
                .andExpect(status().isNotFound()) // 404 상태 코드 확인
                .andExpect(jsonPath("$.status").value(404)) // status 필드 확인
                .andExpect(jsonPath("$.msg").value("실제사유")); // msg 필드 확인
    }


}

