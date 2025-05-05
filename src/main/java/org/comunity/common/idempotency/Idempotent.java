package org.comunity.common.idempotency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메소드에서 동작
@Retention(RetentionPolicy.RUNTIME) // 런타임 시점에 동작
public @interface Idempotent {
}
