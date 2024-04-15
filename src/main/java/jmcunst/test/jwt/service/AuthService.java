package jmcunst.test.jwt.service;


import jmcunst.test.jwt.dto.request.LoginRequest;
import jmcunst.test.jwt.dto.response.TokenDto;

public interface AuthService {
    TokenDto login(LoginRequest loginRequest);

    TokenDto token(String refreshToken);
}
