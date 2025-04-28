package org.comunity.auth.repository;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.interfaces.EmailVerificationRepository;
import org.comunity.auth.domain.Email;
import org.comunity.auth.repository.entity.EmailVerificationEntity;
import org.comunity.auth.repository.jpa.JpaEmailVerificationRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Override
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailAddress);

        if(entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();

            if(emailVerificationEntity.isVerified()) {
                // 이미 인증이 되어있는 상태
                throw new IllegalArgumentException("이미 인증된 이메일입니다.");
            }

            // 보냈는데 아직 인증이 안된 상태
            emailVerificationEntity.updateToken(token);
            return;
        }

        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity);
    }
}
