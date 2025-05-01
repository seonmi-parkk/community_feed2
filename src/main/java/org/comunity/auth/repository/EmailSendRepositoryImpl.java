package org.comunity.auth.repository;

import org.comunity.auth.application.interfaces.EmailSendRepository;
import org.comunity.auth.domain.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailSendRepositoryImpl implements EmailSendRepository {

    @Override
    public void sendVerificationEmail(Email email, String token) {
        // TODO (구글 stmp 사용하여 이메일 전송 구현)
    }

}
