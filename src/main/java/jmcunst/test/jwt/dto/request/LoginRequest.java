package jmcunst.test.jwt.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String password;

    @NotNull(message = "User ID cannot be null")
    @Size(min = 2, message = "User ID not be less than two characters")
    private String userId;
}
