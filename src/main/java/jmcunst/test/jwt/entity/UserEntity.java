package jmcunst.test.jwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity extends BaseEntity {
    @JsonIgnore
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "USER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "UUID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String uuid;

    @Column(name = "USERNAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(nullable = false,unique = true)
    private String encryptedPwd;

    @Column(name = "EMAIL", length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    public UserEntity(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull RoleType roleType
    ) {
        this.userId = userId;
        this.username = username;
        this.encryptedPwd = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.roleType = roleType;
    }
}