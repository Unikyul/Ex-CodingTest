package com.example.demo.core.until;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Resp<T> {
    private int status;
    private String msg;
    private T body;

    public static <B> Resp<?> ok(B body) {
        return new Resp<>(200, "ok", body);

    }
    public static <B> Resp<?> ok(B body, String message) {
        return new Resp<>(200, message , body);

    }
    // 실패 응답
    public static <T> Resp<T> fail(int status, String msg) {
        return new Resp<>(status, msg, null);
    }
}
