package org.comunity.common.Principal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 파라미터 앞에 붙는 어노테이션
@Retention(RetentionPolicy.RUNTIME) // 런타임시점에 찾게 되는 어노테이션
public @interface AuthPrincipal {

}
