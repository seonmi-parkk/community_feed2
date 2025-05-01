package org.comunity.acceptance.utils;

import org.comunity.auth.application.dto.LoginRequestDto;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import static org.comunity.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.comunity.acceptance.steps.UserAcceptanceSteps.followUser;

// test 프로파일을 실행하는것을 명시
@ActiveProfiles("test")
// WebEnvironment.DEFINED_PORT : 고정으로 8080포트를 사용하겠다는 의미
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    @Autowired
    private DatabaseClaenUp databaseCleanup;
    @Autowired
    private DataLoader dataLoader;

    public void init() {
        databaseCleanup.execute();
        dataLoader.loadData();

        followUser(new FollowUserRequestDto(10L, 11L));
        followUser(new FollowUserRequestDto(11L, 12L));
    }

    public void cleanUp() {
        databaseCleanup.execute();
    }

    protected String getEmailToken(String email) {
        return dataLoader.getEmailToken(email);
    }

    protected boolean isEmailVerified(String email) {
        return dataLoader.isEmailVerified(email);
    }

    protected Long getUserId(String email) {
        return dataLoader.getUserId(email);
    }

    protected void createUser(String email) {
        dataLoader.createUser(email);
    }

    protected String login(String email) {
        return requestLoginGetToken(new LoginRequestDto(email, "password"));
    }

}

