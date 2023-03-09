package com.aaron.springboot.domain.posts;

import com.aaron.springboot.dmain.posts.Posts;
import com.aaron.springboot.dmain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest()  // H2 데이터베이스(인모메리 관계형 데이터베이스)를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정, 테스트간 데이터 침범을 막음
    // 여러 테스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 테스트가 실패할 수 있다.
    @After
    public void cleanup() {
        // 다음 테스트 실행 시를 위해 데이터 삭제
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /* 테이블 posts에 insert/updata 쿼리를 실행
           id 값이 있다면 update, 없다면 insert 쿼리 실행 */
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("aaronhan0523@gmail.com")
                .build());

        // when
        // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

        System.out.println(posts.getId());
        System.out.println(posts.getTitle());
        System.out.println(posts.getContent());
        System.out.println(posts.getAuthor());
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        // LocalDateTime.of() : 인자로 전달한 값에 따른 시간 데이터 생성한다.

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> createdDate="+ posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        // then
        assertThat(posts.getCreatedDate()).isAfter(now); // isAfter() : 검증 대상의 시간이 인자로 전달된 시간 이후인지를 검증하는 메서드
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
