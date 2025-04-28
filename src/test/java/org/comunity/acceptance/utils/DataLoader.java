package org.comunity.acceptance.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

import static org.comunity.acceptance.steps.UserAcceptanceSteps.createUser;
import static org.comunity.acceptance.steps.UserAcceptanceSteps.followUser;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    // 이렇게 entityManager를 통해서 직접 nativeQuery를 작성해서 셋팅을 해줘도 좋음.
    // @PersistenceContext
    // private EntityManager em;

    public void loadDate(){
        // 기본 데이터
            // 유저 생성
        CreateUserRequestDto dto = new CreateUserRequestDto("test_user", "");
        createUser(dto);
        createUser(dto);
        createUser(dto);

            // 유저 팔로우
        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }

    public String getEmailToken(String email){
        return entityManager.createNativeQuery("SELECT token FROM community_email_verification WHERE email = ?", String.class)
                .setParameter(1, email)
                .getSingleResult()
                .toString();
    }
}
