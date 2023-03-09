package com.aaron.springboot.web.dto;

import com.aaron.springboot.dmain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
/* 여기서 Entity 클래스와 거의 유사한 형태임에도 Dto 클래스를 추가로 생성하였다.
* 하지만, 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안된다.
* Entity 클래스는 DB 와 맞닿은 핵심 클래스 이기 때문이다.
* Entity 클래스를 기준으로 테이블이 생성되고, 스키마가 변경된다.
* 화면 변경은 아주 사소한 기능이므로 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경이다.
* View Layer 와 DB Layer 의 역활 분리는 철저히 하는게 좋다. */