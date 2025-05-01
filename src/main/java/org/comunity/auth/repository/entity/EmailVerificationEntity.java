package org.comunity.auth.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.comunity.common.repository.entity.TimeBaseEntity;

@Entity
@Table(name="community_email_verification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailVerificationEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    private boolean isVerified;

    public EmailVerificationEntity(String email, String token) {
        this.id = null;
        this.email = email;
        this.token = token;
        this.isVerified = false;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void updateToken(String token) {
        this.token = token;
    }

    public void verify() {
        this.isVerified = true;
    }

    public boolean hasSameToken(String token) {
        return this.token.equals(token);
    }

}
