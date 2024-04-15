package jmcunst.test.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
