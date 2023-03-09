package com.aaron.springboot.service;

import com.aaron.springboot.dmain.posts.Posts;
import com.aaron.springboot.dmain.posts.PostsRepository;
import com.aaron.springboot.web.dto.PostsListResponseDto;
import com.aaron.springboot.web.dto.PostsResponseDto;
import com.aaron.springboot.web.dto.PostsSaveRequestDto;
import com.aaron.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
        /* update 기능에서 DB에 query 를 날리는 부분이 없다. JPA 의 영속성 컨텍스트 때문이다.
        * 영속성 컨텍스트란, 엔티티를 영구 저장하는 환경이다.
        * JPA 의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다.
        * 트랜잭션 안에서 DB 에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다.
        * 이 상태에서 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다.
        * 즉, Entity 객체의 값만 변경하면 별도로 Update query 를 날릴 필요가 없으며 이 개념을 dirty checking 이라고 한다. */
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    /* findAllDesc 메소드의 트랜잭션 어노테이션 옵션인 readOnly=true 추가
       : 트랜잭션 범위는 유지하되, 조회 기능만 남겨두고 조회 속도가 개선되기 때문에
        등록/수정/삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 것을 추천 */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        /* .map(PostsListResponseDto::new) 람다식 : .map(posts -> new PostsListResponseDto(posts))
           : postsRepository 결과 넘어온 Posts의 Stream을 map을 통해 PostsListReponseDto로 변환하고 List로 반환하는 메소드 */
        return  postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
        /* Stream 을 List 로 변환하는 방법이 크게 collect(Collectors.toList()) 와 Stream.toList() 있는데
        * 전자는 .add 로 수정이 가능하지만 후자는 수정시 예외 처리가 된다. 그리고 둘다 NULL 허용 */
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
	/* JpaRepository 에서 지원하는 delete 메소드 활용
       엔티티를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용하면 id로 삭제할 수 있음
       존재하는 Posts 인지 확인을 위해 엔티티 조회 후 그대로 삭제 */
        postsRepository.delete(posts);
    }
}
