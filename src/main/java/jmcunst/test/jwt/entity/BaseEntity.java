package jmcunst.test.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}