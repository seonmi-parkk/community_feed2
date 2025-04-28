package org.comunity.auth.application.interfaces;

import org.comunity.auth.domain.Email;

public interface EmailSendRepository {
    void sendEmail(Email email, String randomToken);
}
