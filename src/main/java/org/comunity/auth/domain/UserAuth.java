package org.comunity.auth.domain;

public class UserAuth {
    // 3가지 String 값을 받아서 mashup 해주는 클래스

    private final Email email;
    private final Password password;
    private final UserRole userRole;
    private Long UserId;

    public UserAuth(String email, String password, String userRole) {
        this.email = Email.createEmail(email);
        this.password = Password.createEncryptedPassword(password);
        this.userRole = UserRole.valueOf(userRole);
    }

    public UserAuth(String email, String password, String userRole, Long userId) {
        this.email = Email.createEmail(email);
        this.password = Password.createEncryptedPassword(password);
        this.userRole = UserRole.valueOf(userRole);
        this.UserId = userId;
    }

    public String getEmail() {
        return email.getEmailText();
    }

    public String getPassword() {
        return password.getEncryptedPassword();
    }

    public String getUserRole() {
        return userRole.name();
    }

    public Long getUserId() {
        return UserId;
    }

}
