package org.comunity.user.application;

import org.comunity.fake.FakeObjectFactory;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.comunity.user.application.interfaces.UserRelationRepository;
import org.comunity.user.application.interfaces.UserRepository;
import org.comunity.user.domain.User;
import org.comunity.user.repository.FakeUserRelationRepository;
import org.comunity.user.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRelationServiceTest {
//    FakeObjectFactory를 사용해서 간단하게 주입받을 수 있음.
//    private final UserRepository userRepository =  new FakeUserRepository();
//    private final UserService userService = new UserService(userRepository);
//    private final UserRelationRepository userRelationRepository = new FakeUserRelationRepository();
    private final UserService userService = FakeObjectFactory.getUserService();
    private final UserRelationService userRelationService = FakeObjectFactory.getUserRelationService();


    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    @BeforeEach
    void init(){
        CreateUserRequestDto dto = new CreateUserRequestDto("test","");
        this.user1 = userService.createUser(dto);
        this.user2 = userService.createUser(dto);

        this.requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());

    }

    @Test
    void givenCreateTwoUser_whenFollow_thenUserFollowSaved() {
        // when
        userRelationService.follow(requestDto);

        //then
        assertEquals(1, user1.followingCount());
        assertEquals(1, user2.followerCount());
    }

    @Test
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError(){
        // given
        userRelationService.follow(requestDto);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenFollow_thenUserThrowError(){
        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(sameUser));
    }



    @Test
    void givenCreateTwoUserFollow_whenUnfollow_thenUserUnfollowSaved() {
        // given
        userRelationService.follow(requestDto);

        // when
        userRelationService.unfollow(requestDto);

        //then
        assertEquals(0, user1.followingCount());
        assertEquals(0, user2.followerCount());
    }

    @Test
    void givenCreateTwoUser_whenUnfollow_thenUserThrowError(){
        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenUnfollowSelf_thenUserThrowError(){
        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(sameUser));
    }

}
