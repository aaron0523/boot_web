package com.aaron.springboot.config.auth.dto;

import com.aaron.springboot.dmain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
// SessionUser 클래스는 인증된 사용자 정보만 필요하기 때문에 아래 처럼 3가지 변수만 필드로 선언
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
/* 왜 User 클래스를 사용하지 않고 Dto 세션을 추가로 만들어서 사용했을까?
* User 클래스를 세션에 저장하려고 할 시 User 클래스에 직렬화를 구현하지 않아 에러가 발생함
* 오류를 해결하기 위해 User 클래스에 직렬화 코드를 넣더라도 User 클래스는 엔티티이기 때문에 직렬화 대상에 자식 엔티티들까지 포함되면 성능 이슈, 부수 효과가 발생할 확률이 높아짐
* 그렇기 때문에 직렬화 기능을 가진 Dto 를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 됨
* */