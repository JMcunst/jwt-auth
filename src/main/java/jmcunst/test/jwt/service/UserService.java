package jmcunst.test.jwt.service;

import jmcunst.test.jwt.dto.UserDto;
import jmcunst.test.jwt.dto.request.UserRequest;
import jmcunst.test.jwt.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserRequest userRequest);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String username);
}