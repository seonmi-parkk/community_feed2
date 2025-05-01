package org.comunity.common.Principal;

import lombok.Getter;
import org.comunity.auth.domain.UserRole;

@Getter
public class UserPrincipal {

    private Long userId;
    private UserRole userRole;

    public UserPrincipal(Long userId, String role) {
        this.userId = userId;
        this.userRole = UserRole.valueOf(role);
    }

}
