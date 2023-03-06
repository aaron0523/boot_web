# boot_web

TDD 와 Unit Test -> 여기서는 Unit Test 로 진행한다.

< DAO, DTO, Entity Class의 차이 >
1. DAO(Data Access Object) 란?
: 실제로 DB에 접근하는 객체이다.
2. DTO(Data Transfer Object) 란?
: 계층간 데이터 교환을 위한 객체(Java Beans)이다.
  참고] VO(Value Object) vs DTO
  VO는 특정한 비즈니스 값을 담는 객체이고, DTO는 Layer간의 통신 용도로 오고가는 객체를 말한다.
3. Entity Class 란?
: 실제 DB의 테이블과 매칭될 클래스. 즉, 테이블과 링크될 클래스임을 나타낸다. @Entity, @Column, @Id 등을 이용

< 전체 구조 (package 기준) >
1. controller(web)
: 해당 요청 url에 따라 적절한 view와 mapping 처리
2. service
: 적절한 Business Logic을 처리한다. DAO로 DB에 접근하고 DTO로 데이터를 전달받은 다음, 비지니스 로직을 처리해 적절한 데이터를 반환한다.
3. repository(dao)
: 실제로 DB에 접근하는 객체이다.

< gradle 다운 그레이드 >
터미널에 ./gradlew wrapper --gradle-version 4.10.2 입력

< SI 환경 > 
1. Spring & MyBatis
: MyBatis 는 SQL Mapper 로 쿼리를 매핑한다. MyBatis 를 사용할 때 DAO 패키지를 사용

2. SpringBoot & JPA
  1) JPA 소개
  - 현대의 웹 애플리케이션에서 관계형 데이터베이스는 중요한 요소임 -> SQL 을 통해야만 데이서 조작가능
  - 그렇다보니 SQL은 어떻게 데이터를 저장할지에 대해 초점이 맞춰져 있고 반대로 객체지향 프로그래밍은 메시지를 기반으로 기능과 속성을 한곳에서 관리하는 기능으로 상반된다.
  - 추상화, 캡슐화, 정보은닉, 다형성 등을 관계형 데이터베이스로 객체지향을 표현하기는 어렵다.
  - JPA 는 객체지향 프로그래밍 언어와 관계형 데이터베이스를 중간에서 패러다임 일치 시켜주기 위한 기술이다.

  2) Spring Data JPA
  - JPA 는 인터페이스이며 사용하기 위해서는 구현체가 필요하다. 구현체로는 Hibernate, EclipseLink 등이 있다.
  - 하지만 스프링에서는 JPA를 사용할 때는 이 구현체들을 직접 다루지 않고 Spring Data JPA 모듈을 사용하여 JPA 기술을 다룬다.
  - JPA <- Hibernate <- Spring Data JPA. 이렇게 한 단계 더 감싸놓은 Spring Data JPA가 등장한 이유는 크게 두가지가 있다.
    1. 구현체 교체의 용이성
    - Hibernate 외에 다른 구현체로 쉽게 교체하기 위함
    2. 저장소 교체의 용이성
    - 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함. 만약 NoSQL 로 전환하고 싶다면 Spring Data JPA에서 의존성만 교체하면 된다.
    
< 등록/수정/조회 API 만들기 >
API를 만들기 위해 총 3개의 클래스가 필요하다.
  1. Request 데이터를 받을 Dto
  2. API 요청을 받을 Controller
  3. 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
여기서 많은 오해가 있지만 Service 에서 비지니스 로직을 처리하지 않고 Service 는 트랜잭션, 도메인 간 순서 보장의 역활만 한다.
비즈니스 로직은 Domain 에서 처리한다. 

  < Spring 웹 계층 >
  Web Layer - (DTOs) - Service Layer - (Domain Model) - Repository Layer
  1. Web Layer : 흔히 사용하는 컨트롤러(@Controller)와 JSP/Freemarker 등의 뷰 템플릿 영역.
                이외에도 @Filter, 인터셉터, @ControllerAdvice 등 외부 요청과 응답에 대한 전반적인 영역을 이야기 한다.
  2. Service Layer : @Service에 사용되는 서비스 영역. @Transactional 이 사용되어야 하는 영역이기도 하다.
  3. Repository Layer : DB와 같이 데이터 저장소에 접근하는 영역. DAO 영역으로 이해하면 된다.
  4. DTOs(Data Transfer Object) : 계층 간에 데이터 교환을 위한 객체들.
                                뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등을 이야기한다.
  5. Domain Model : 테이블 관계나 VO 처럼 값 객체들이 이 영역에 해당한다.
