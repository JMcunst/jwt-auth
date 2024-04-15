package jmcunst.test.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jmcunst.test.jwt.common.BaseException;
import jmcunst.test.jwt.common.BaseResponse;
import jmcunst.test.jwt.dto.request.LoginRequest;
import jmcunst.test.jwt.dto.response.TokenDto;
import jmcunst.test.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public BaseResponse<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult br) throws BaseException {

        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(authService.login(loginRequest));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    @Operation(summary = "토큰 재발행")
    @PostMapping("/token")
    public BaseResponse<TokenDto> refresh(String refreshToken){
        return new BaseResponse<>(authService.token(refreshToken));
    }
}
