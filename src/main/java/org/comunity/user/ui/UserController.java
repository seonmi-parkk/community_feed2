package org.comunity.user.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.common.ui.Response;
import org.comunity.user.application.UserService;
import org.comunity.user.application.dto.CreateUserRequestDto;
import org.comunity.user.application.dto.GetUserListResponseDto;
import org.comunity.user.application.dto.GetUserResponseDto;
import org.comunity.user.domain.User;
import org.comunity.user.repository.jpa.JpaUserListQueryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JpaUserListQueryRepository UserListQueryRepository;

    // 응답값을 통일시켜서 반환 (정해져 있는 응답 인터페이스 형태를 맞춰주면 좋음)
    @PostMapping
    public Response<Long> creatUser(@RequestBody CreateUserRequestDto dto){
        User user = userService.createUser(dto);
        return Response.ok(user.getId());
    }

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUserProfile(@PathVariable(name = "userId") Long userId){
        return Response.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/{userId}/following")
    public Response<List<GetUserListResponseDto>> getFollowingList(@PathVariable(name="userId") Long userId){
        List<GetUserListResponseDto> result = UserListQueryRepository.getFollowingUserList(userId);
        return Response.ok(result);
    }

    @GetMapping("/{userId}/follower")
    public Response<List<GetUserListResponseDto>> getFollowerList(@PathVariable(name="userId") Long userId){
        List<GetUserListResponseDto> result = UserListQueryRepository.getFollowerUserList(userId);
        return Response.ok(result);
    }
}
