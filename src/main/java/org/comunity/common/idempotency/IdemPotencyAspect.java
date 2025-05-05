package org.comunity.common.idempotency;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.comunity.common.ui.Response;
import org.springframework.stereotype.Component;

// 멱등성 객체를 확인하는 AOP 객체
@Aspect
@Component
@RequiredArgsConstructor
public class IdemPotencyAspect {

    private final IdempotencyRepository idempotencyRepository;
    private final HttpServletRequest request;

    //@Around - 메소드 실행 전후에 동작하는 어노테이션
    @Around("@annotation(Idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint) throws Throwable {
        String idempotencyKey = request.getHeader("Idempotency-Key");
        if (idempotencyKey == null) {
            return joinPoint.proceed();
        }

        Idempotency idempotency = idempotencyRepository.getByKey(idempotencyKey);

        if (idempotency != null) {
            return idempotency.getResponse(); // 로직을 수행하지 않고 저장된 응답값 반환
        }

        Object result = joinPoint.proceed(); // 로직을 수행
        Idempotency newIdempotency = new Idempotency(idempotencyKey, (Response<?>) result);
        idempotencyRepository.save(newIdempotency);

        return result;
    }
}
