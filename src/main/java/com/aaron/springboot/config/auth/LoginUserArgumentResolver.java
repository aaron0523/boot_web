package com.aaron.springboot.config.auth;

import com.aaron.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
// HandleMethodArgumentResolver 는 한가지 기능을 지원한다.
// 조건에 맞는 경우 메소드가 있다면 HandleMethodArgumentResolver 의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘기는 기능을 지원
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    /* supportsParameter
       : 컨트롤러 메소드의 특정 파라미터를 지원하는지 판단
         여기서는 파라미터에 @LoginUser 어노테이션이 붙어있고,
         파라미터 클래스 타입이 SessionUser.class 인 경우 true 반환 */
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    /* resolveArgument
       : 파라미터에 전달할 객체를 생성
         여기서는 세션에서 객체를 가져옴 */
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}