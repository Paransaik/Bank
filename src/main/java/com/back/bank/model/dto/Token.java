package com.back.bank.model.dto;

import lombok.*;

public class Token {

    public enum Type {
        R, A
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto {
        String accessToken;
        String refreshToken;
        String key;
    }
}
