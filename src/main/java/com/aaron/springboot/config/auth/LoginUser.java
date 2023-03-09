package com.aaron.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* SessionUser user = (SessionUser) httpSession.getAttribute("user");
* 세션 값을 가져오는 부분은 index 메소드 외에 다른 컨트롤러와 메소드에서 세션값이 필요하면 그때마다 직접 세션에서 값을 가져와야 한다.
* 이 부분을 메소드 인자로 세션값을 바로 받을 수 있도록 annotation 기반으로 개선하기 위해 이 클래스를 만들었다. */

/* @Target(ElementType.PARAMETER)
   : 이 어노테이션이 생성될 수 있는 위치를 지정해준다.
     PARAMETER 로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
     이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있음 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
/* @interface
   : 이 파일을 어노테이션 클래스로 지정해준다.
     LoginUser 라는 이름을 가진 어노테이션이 생성된 것 */
public @interface LoginUser {
}
