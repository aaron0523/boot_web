package com.aaron.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.boot.SpringApplication.run;

//@EnableJpaAuditing // JPA Auditing 어노테이션을 모두 활성화 할수있게 하는 annotation
@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성 모두 자동 설정
// 주의!! 맨처음 이 애노테이션을 읽기 때문에 프로젝트 최상단 위치에 있어야 한다.
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }

    @Bean // UUID로 등록자 및 수정자를 만들었지만 실무에서는 보통 세션정보의 유저 id 를 넣어준다.
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
