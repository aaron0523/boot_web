package com.aaron.springboot.dmain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// PostsRepository 는 MyBatis 등에서 Dao 라고 불리는 DB Layer 접근자 이다.
// 단순히 인터페이스 생성 후 JpaRepository<Entity 클래스, PK 타입>을 상속하면 CRUD 메소드 자동 생성
// 주의할점은 Entity class 와 EntityRepository 는 같은 위치에 있어야 한다.
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    /* 규모 있는 프로젝트에서의 데이터 조희는 FK의 조인, 복잡한 조건 등으로 인해 이런 Entity 클래스만으로 처리하기 어려워
    * 조회용 프레임워크를 추가로 사용한다. 대표적으로 querydsl, jooq, MyBatis 등이 있다.
    * 등록/수정/삭제 등은 SpringDataJpa 를 사용한다. */

}
