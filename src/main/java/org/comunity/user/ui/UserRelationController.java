package org.comunity.user.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.common.ui.Response;
import org.comunity.user.application.UserRelationService;
import org.comunity.user.application.dto.FollowUserRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relation")
@RequiredArgsConstructor
public class UserRelationController {

    private final UserRelationService userRelationService;

    @PostMapping("/follow")
    public Response<Void> follwerUser(@RequestBody FollowUserRequestDto dto){
        userRelationService.follow(dto);
        return Response.ok(null);
    }

    @PostMapping("/unfollow")
    public Response<Void> unfollwerUser(@RequestBody FollowUserRequestDto dto){
        userRelationService.unfollow(dto);
        return Response.ok(null);
    }
}
