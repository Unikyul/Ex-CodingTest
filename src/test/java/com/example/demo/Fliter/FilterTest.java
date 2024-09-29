package com.example.demo.Fliter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilterTest {

    @Autowired
    private MockMvc mockMvc;

    // 특수문자가 포함된 URL에 대해 필터가 제대로 작동하는지 테스트
    @Test
    public void testBlockedSpecialCharacters() throws Exception {
        // 1. Given - 테스트에 필요한 데이터 설정 또는 상태 준비
        String invalidUrl = "/users/1?name=test!!";  // 특수문자가 포함된 URL

        // 2. When - 실제 테스트할 동작 수행

        mockMvc.perform(get(invalidUrl))

                // 3. Then - 기대하는 결과 확인 (특수문자가 포함된 URL에 대해 400 응답을 기대)
                .andExpect(status().isBadRequest()); // 필터가 특수문자를 차단하고 400 에러 반환
    }

    @Test
    public void testValidRequest() throws Exception {
        // 1. Given - 테스트에 필요한 데이터 설정 또는 상태 준비
        String validUrl = "/users/1?name=test";  // 정상적인 URL

        // 2. When - 실제 테스트할 동작 수행
        mockMvc.perform(get(validUrl))

                // 3. Then - 기대하는 결과 확인 (정상적인 요청에 대해 200 응답을 기대)
                .andExpect(status().isOk()); // 정상적인 요청은 허용됨
    }
}
