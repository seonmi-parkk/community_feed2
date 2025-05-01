package org.comunity.acceptance.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comunity.auth.application.dto.CreateUserAuthRequestDto;
import org.comunity.auth.application.dto.SendEmailRequestDto;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static org.comunity.acceptance.steps.SignUpAcceptanceSteps.*;
import static org.comunity.acceptance.steps.UserAcceptanceSteps.createUser;
import static org.comunity.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    // 이렇게 entityManager를 통해서 직접 nativeQuery를 작성해서 셋팅을 해줘도 좋음.
    // @PersistenceContext
    // private EntityManager em;

    public void loadData(){
        // 기본 데이터
        // user 1,2,3 생성
        for(int i=1; i<4; i++){
            createUser("user"+i+"@test.com");
        }

        // 유저 팔로우
        followUser(new FollowUserRequestDto(10L, 11L));
        followUser(new FollowUserRequestDto(10L, 12L));
    }

    public String getEmailToken(String email){
        return entityManager.createNativeQuery("SELECT token FROM community_email_verification WHERE email = ?", String.class)
                .setParameter(1, email)
                .getSingleResult()
                .toString();
    }

    public boolean isEmailVerified(String email){
        return entityManager.createQuery("SELECT isVerified FROM EmailVerificationEntity WHERE email = :email", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long getUserId(String email){
        return entityManager.createQuery("SELECT userId FROM UserAuthEntity WHERE email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public void createUser(String email) {
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);
        registerUser(new CreateUserAuthRequestDto(email, "password", "USER", "name", ""));

    }
}
