package com.aaron.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name =  "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name); // assertThat 은 assertj 라는 테스트 검증 라이브러리의 검증 메소드이다.
        assertThat(dto.getAmount()).isEqualTo(amount); // isEqualTo 값을 비교하는 메소드
    }
}