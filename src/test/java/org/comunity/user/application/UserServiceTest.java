package org.comunity.user.application;

import org.comunity.fake.FakeObjectFactory;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.comunity.user.domain.UserInfo;
import org.comunity.user.repository.FakeUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {
    // UserRepository의 구현체를 만들지 않고 작업진행중이었음
    // 단위 테스트 시 스텁이나 페이크 객체를 통해서 가짜로 테스트 더블을 주입해주면 됨.
    // 페이크 객체를 통해서 인메모리 데이터베이스를 만들어서 테스트 진행.

    private final UserService userService = FakeObjectFactory.getUserService();

    @Test
    void givenUserInfoDto_whenCreateUser_thenFindUser(){
        //given
        CreateUserRequestDto dto = new CreateUserRequestDto("test","");

        //when
        User savedUser = userService.createUser(dto);

        //then
        User foundUser = userService.getUser(savedUser.getId());
        UserInfo userInfo = foundUser.getUserInfo();
        assertEquals(foundUser.getId(), savedUser.getId());
        assertEquals("test", userInfo.getName());

    }
}
