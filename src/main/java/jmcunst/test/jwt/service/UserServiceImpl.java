package jmcunst.test.jwt.service;

import jmcunst.test.jwt.dto.UserDto;
import jmcunst.test.jwt.dto.request.UserRequest;
import jmcunst.test.jwt.entity.RoleType;
import jmcunst.test.jwt.entity.UserEntity;
import jmcunst.test.jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@Transactional
@Service
@Log4j2
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

//    private PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserRequest userRequest) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userRequest, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setRoleType(RoleType.USER);
        userEntity.setUuid(UUID.randomUUID().toString());

        userRepository.save(userEntity);

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
