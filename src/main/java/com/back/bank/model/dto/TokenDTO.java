package com.back.bank.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    String accessToken;
    String refreshToken;
    String key;

    public enum Type {
        R, A;
    };
}
