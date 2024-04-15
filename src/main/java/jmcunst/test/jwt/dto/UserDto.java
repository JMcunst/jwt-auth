package jmcunst.test.jwt.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UserDto {
    private String email;
    private String username;
    private String userId;
    private Date createdAt;

    private String encryptedPwd;
}
