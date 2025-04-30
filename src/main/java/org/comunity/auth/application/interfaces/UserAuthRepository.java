package org.comunity.auth.application.interfaces;

import org.comunity.auth.domain.UserAuth;
import org.comunity.user.domain.User;

public interface UserAuthRepository {

    UserAuth registerUser(UserAuth auth, User user);
}
