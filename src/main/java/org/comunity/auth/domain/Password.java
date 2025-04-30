package org.comunity.auth.domain;

public class Password {

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public static Password createEncryptedPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 비어있을 수 없습니다.");
        }
        return new Password(SHA256.encrypt(password));
    }

    public boolean matchPassword(String password) {
        return encryptedPassword.matches(SHA256.encrypt(password));
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
