package com.aaron.springboot.dmain.posts;

import com.aaron.springboot.dmain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter  // 롬복 어노테이션 - 클래스 내의 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor  // 롬복 어노테이션 - 기본 생성자 자동 추가, public Posts() {}와 같은 효과
@Entity  // JPA 어노테이션 - 테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity { // 실제 DB 의 테이블과 매칭될 클래 ex) SalesManager.java -> sales_manager table

    @Id  // JPA 어노테이션 - 해당 테이블의 PK (기본키) 필드
    // 웬만한면 Entity 의 PK 는 Long 타입의 auto_increment 를 추천. 여러키를 조합한 복합키로 PK 를 잡을 경우 난감한 상황이 발생할수 있음
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // JPA 어노테이션 - PK 의 생성 규칙.
    // strategy = GenerationType.IDENTITY 옵션을 추가해야지 auto_increment 가 된다.
    private Long id;

    @Column(length = 500, nullable = false)  // JPA 어노테이션 - 테이블의 칼럼, 굳이 선언하지 않아도 되지만 옵션이 있을 때 사용
    // 문자열일 경우 VARCHAR(255) 이 기본값이다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder  // 롬복 어노테이션 - 해당 클래스의 빌더 패턴 클래스 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
/*
* Entity 클래스에서는 절대 Setter 메소드를 사용하지 않고 대신 목적과 의도를 나타낼 수 있는 메소드를 사용
* Setter 가 없는 대신 생성자를 통해 DB에 삽입하거나 해당 이벤트에 맞는 public 메소드를 호출하여 변경
* @Builder 를 통해 사용하면 반드시 필요한 값이 있어야 객체가 생성됨을 보장할 수 있어 안전성을 높일 수 있다.
* 채워야 할 필드가 무엇인지 명확히 지정할 수 없는 생성자와 달리 빌더는 어느 필드에 어떤 값을 채워야할지 명확하게 인지 가능
* */