package com.sovathc.authjwt.authentication.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthTokenDTO {
    private Long id;
    private String unqiueKey;
    private LocalDateTime requestTime;
}
