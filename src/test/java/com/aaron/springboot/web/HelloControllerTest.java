package com.aaron.springboot.web;

import com.aaron.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class) // 테스트를 진행할때 JUnit에 내장된 실행자 외됨 다른 실행자를 실행
// 여기서는 SpringRunner를 실행
//@WebMvcTest // @Controller, @ControllerAdvice 등 사용할수 있음
// 하지만 @Service, @Component, @Repository 는 사용 안됨
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 Bean 을 주입받는다.
    private MockMvc mvc; // 웹 API 를 테스트할 때 사용하며 MVC 테스트의 시작점(GET, POST 등에 대한 API 테스트)

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // HTTP Header 의 status 를 검증
                .andExpect(content().string(hello)); // Controller 에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // param() 요청 파라미터를 설정. 값은 String 만 가능
                        .param("amount", String.valueOf(amount)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(name)).exists())
                        // JSON 응닶값을 필들별로 검증할 수 있는 메소드. $.를 기준으로 필드명을 암시
                        .andExpect(jsonPath("$.amount", is(amount)).exists());

    }
}
