package com.aaron.springboot.web;

import com.aaron.springboot.dmain.posts.Posts;
import com.aaron.springboot.dmain.posts.PostsRepository;
import com.aaron.springboot.web.dto.PostsSaveRequestDto;
import com.aaron.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest 를 사용하지 않은 이유는 JPA 기능이 작동하지 않기 때문이다.
@RunWith(SpringRunner.class)
// 아래 구문은 호스트가 사용하지 않는 랜덤 포트를 사용하겠다는 의미다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    /* @Before
       : 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성 */
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @LocalServerPort
    private int port;

    @Autowired
    /* TestRestTemplate 은 REST 방식으로 개발한 API 의 Test 를 최적화 하기 위해 만들어진 클래스이다.
     * HTTP 요청 후 데이터를 응답 받을 수 있는 템플릿 객체이며 ResponseEntity 와 함께 자주 사용된다.
     * Header 와 Content-Type 등을 설정하여 API 를 호출 할 수 있다.
     * MockMvc 과는 다르게 실제 서블릿 컨테이너를 실행하여 컨테이너를 직접 실행한다.
     * */
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    /* @WithMockUser(roles="USER")
    : 인증된 모의 (가짜) 사용자를 만들어서 사용
     roles 에 권한을 추가할 수 있음
     즉, 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 API 를 요청하는 것과 동일한 효과 */
    // @WithMockUser 가 MockMvc 에서만 작동하기 때문에 PostsApiController 에 MockMvc 를 사용하도록 해야 함
    @WithMockUser(roles = "USER")
    public void Posts_등록된다() throws Exception {

        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
//            * ResponseEntity 란?
//             * Spring Framework 에서 제공하는 클래스 중 HttpEntity 라는 클래스가 존재한다.
//             * 이것은 HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader 와 HttpBody 를 포함하는 클래스이다.
//             * RequestEntity 와 ResponseEntity 는 이 HttpEntity 를 상속받는다.
//            ResponseEntity<Long> responseEntity = restTemplate
//                    .postForEntity(url, requestDto, Long.class);
//            postForEntity() : JSON 콘텐츠를 보내는 POST 요청
        /* mvc.perform
          : 생성된 MockMvc를 통해 API를 테스트
            본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환 */
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
//            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//            assertThat(responseEntity.getBody()).isGreaterThan(0L);
//            System.out.println("@@@@ " + responseEntity);
//            System.out.println("@@@@ " + responseEntity.getBody());
//
//            List<Posts> all = postsRepository.findAll();
//            assertThat(all.get(0).getTitle()).isEqualTo(title);
//            assertThat(all.get(0).getContent()).isEqualTo(content);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception {

        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
//            ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
//            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//            assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//            List<Posts> all = postsRepository.findAll();
//            assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//            assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
    /* 조회 기능은 실제로 톰캣을 실행해서 확인해야 한다.
     * 이유는 H2는 메모리에서 실행되기 때문에 직접 접근하려면 웹 콘솔을 사용해야 한다.
     * application.properties 에 spring.h2.console.enabled=true */

}
