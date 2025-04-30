package org.comunity.auth.ui;

import lombok.RequiredArgsConstructor;
import org.comunity.auth.application.AuthService;
import org.comunity.auth.application.EmailService;
import org.comunity.auth.application.dto.CreateUserAuthRequestDto;
import org.comunity.auth.application.dto.SendEmailRequestDto;
import org.comunity.common.ui.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final EmailService emailService;
    private final AuthService authService;

    @PostMapping("/send-verification-email")
    public Response<Void> sendEmail(@RequestBody SendEmailRequestDto dto) {
        emailService.sendEmail(dto);
        return Response.ok(null);
    }

    @GetMapping("/verify-token")
    public Response<Void> verifyEmail(String email, String token) {
        emailService.verifyEmail(email, token);
        return Response.ok(null);
    }

    @PostMapping("/register")
    public Response<Long> register(@RequestBody CreateUserAuthRequestDto dto) {
        return Response.ok(authService.registerUser(dto));
    }


}
