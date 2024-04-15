package jmcunst.test.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jmcunst.test.jwt.entity.Greeting;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "접근 확인")
@AllArgsConstructor
@RestController
@RequestMapping
public class AccessController {
    private Environment env;
    private Greeting greeting;

    @Operation(summary = "접속 테스트")
    @GetMapping("/access-test")
    public String access() {
        return "SUCCESS ACCESS TEST";
    }

    @Operation(summary = "환영 인사")
    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

    @Operation(summary = "헬스 체크")
    @GetMapping("/health-check")
    public String status(){
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", refresh token expiration time=" + env.getProperty("spring.jwt.token.access-expiration-time")
                + ", access token expiration time=" + env.getProperty("spring.jwt.token.refresh-expiration-time")
        );
    }
}
