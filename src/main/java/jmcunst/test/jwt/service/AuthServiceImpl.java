package jmcunst.test.jwt.service;

import jmcunst.test.jwt.common.BaseException;
import jmcunst.test.jwt.dto.request.LoginRequest;
import jmcunst.test.jwt.dto.response.TokenDto;
import jmcunst.test.jwt.entity.UserEntity;
import jmcunst.test.jwt.repository.UserRepository;
import jmcunst.test.jwt.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jmcunst.test.jwt.common.BaseResponseStatus.INVALID_USER_ID;
import static jmcunst.test.jwt.common.BaseResponseStatus.INVALID_USER_PW;

@RequiredArgsConstructor
@Transactional
@Service
@Log4j2
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TokenDto login(LoginRequest loginRequest) {
        try {
            UserEntity user = userRepository.findByUserId(loginRequest.getUserId());
            if (user == null) {
                throw new BaseException(INVALID_USER_ID);
            }

            // 저장된 비밀번호와 사용자가 제공한 비밀번호를 비교
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getEncryptedPwd())) {
                throw new BaseException(INVALID_USER_PW);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserId(),
                    user.getEncryptedPwd() // 인코딩된 비밀번호 사용
            );

            TokenDto tokenDto = new TokenDto(
                    jwtTokenProvider.createAccessToken(authentication),
                    jwtTokenProvider.createRefreshToken(authentication)
            );

            return tokenDto;

        }catch(BadCredentialsException e){
            log.error(INVALID_USER_PW.getMessage());
            throw new BaseException(INVALID_USER_PW);
        }
    }

    @Override
    public TokenDto token(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);

        // Access Token 에서 User uid를 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

        // Redis에서 저장된 Refresh Token 값을 가져옴
//        String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());
//        if(!redisRefreshToken.equals(refreshToken)) {
//            throw new BaseException(NOT_EXIST_REFRESH_JWT);
//        }
        // 토큰 재발행
        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.createAccessToken(authentication),
                jwtTokenProvider.createRefreshToken(authentication)
        );

        return tokenDto;
    }
}
