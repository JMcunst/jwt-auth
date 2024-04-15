package jmcunst.test.jwt.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    @NotNull(message = "User ID cannot be null")
    @Size(min = 2, message = "User ID not be less than two characters")
    private String userId;

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equal or greater than 8 characters")
    private String password;
}
