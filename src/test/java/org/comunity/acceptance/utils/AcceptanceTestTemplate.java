package org.comunity.acceptance.utils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

// test 프로파일을 실행하는것을 명시
@ActiveProfiles("test")
// WebEnvironment.DEFINED_PORT : 고정으로 8080포트를 사용하겠다는 의미
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    @Autowired
    private DatabaseClaenUp cleanUP;

    @Autowired
    private DataLoader loader;

    @BeforeEach
    public void init() {
        cleanUP.execute();
        loader.loadDate();
    }

    protected void cleanUp() {
        cleanUP.execute();
    }

    protected  String getEmailToken(String email) {
        return loader.getEmailToken(email);
    }

    protected boolean isEmailVerified(String email) {
        return loader.isEmailVerified(email);
    }

    protected Long getUserId(String email) {
        return loader.getUserId(email);
    }
}

