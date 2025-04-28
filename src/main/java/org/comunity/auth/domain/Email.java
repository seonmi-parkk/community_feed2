package org.comunity.auth.domain;

import java.util.regex.Pattern;

public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email){
        this.emailText = email;
    }

    public static Email createEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (isNotValidEmailString(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }

        return new Email(email);
    }

    public String getEmailText() {
        return this.emailText;
    }

    private static boolean isNotValidEmailString(String email){
        return !pattern.matcher(email).matches();
    }
}
