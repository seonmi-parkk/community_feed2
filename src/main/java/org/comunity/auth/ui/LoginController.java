package org.comunity.auth.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.AuthService;
import org.comunity.auth.application.dto.LoginRequestDto;
import org.comunity.auth.application.dto.UserAccessTokenResponseDto;
import org.comunity.common.ui.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public Response<UserAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return Response.ok(authService.loginUser(dto));
    }

}
