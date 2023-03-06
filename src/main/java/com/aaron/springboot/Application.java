package com.aaron.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성 모두 자동 설정
// 주의!! 맨처음 이 애노테이션을 읽기 때문에 프로젝트 최상단 위치에 있어야 한다.
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }
}
