package org.comunity.common.Principal;

import org.comunity.auth.domain.TokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    // 어노테이션이 있다면 헤더에서 인증토큰을 가져와서 파싱해주는 기능 추가하면 됨
    //-> 그걸 위해서 스프링에서 제공하는 HandlerMethodArgumentResolver를 사용
    // AuthPrincipal 붙은 어노테이션이 있는 메서드 argument값을 반환하거나 변경할수있도록 함.

    // 헤더에 들어있는 bearer token(authorization token)을 가져와서 parsing해서 값을 넘겨주기
    private final TokenProvider tokenProvider;

    public AuthPrincipalArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // AuthPrincipal 어노테이션이 붙어있는지 확인
        return parameter.hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            // 헤더에서 authorization token을 가져옴
            String authToken = webRequest.getHeader("Authorization");
            if (authToken == null || authToken.split(" ").length != 2) {
                throw new IllegalArgumentException();
            }

            // Bearer 토큰값 <= 이런 형태이기 때문에 순수 토큰값만 가져오기
            String token = authToken.split(" ")[1];
            Long userId = tokenProvider.getUserId(token);
            String role = tokenProvider.getUserRole(token);

            return new UserPrincipal(userId, role);

        } catch (Exception e){
            throw new IllegalArgumentException("올바르지 않은 토큰입니다.");
        }
    }


}
