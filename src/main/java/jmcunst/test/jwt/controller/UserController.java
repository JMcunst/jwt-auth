package jmcunst.test.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jmcunst.test.jwt.common.BaseResponse;
import jmcunst.test.jwt.dto.UserDto;
import jmcunst.test.jwt.dto.request.UserRequest;
import jmcunst.test.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Log4j2
public class UserController {
    private final UserService userService;

    @Operation(summary = "사용자 생성")
    @PostMapping("/signup")
    public BaseResponse<UserDto> signup(@RequestBody UserRequest userRequest){
        return new BaseResponse<>(userService.createUser(userRequest));
    }
}
