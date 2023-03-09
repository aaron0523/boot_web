package com.aaron.springboot.dmain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 이 클래스를 상속할 경우 여기 필드들도 칼럼으로 인식하게 된다.
// 중복되는 칼럼들을 이 클래스에서 관리. 예) 생성일자, 수정일자, 식별자
@EntityListeners(AuditingEntityListener.class) // 해당 클래스에 Auditing 기능을 포함
// 이 클래스는 모든 Entity 의 상위 클래스가 되어 Entity 들의 createdDate, modifiedDate 를 자동으로 관리하는 역활을 한다.
public class BaseTimeEntity {

    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동 저장
    @Column(updatable = false) // 최초 저장 후 수정 금지
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;

    @CreatedBy // 등록자
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 수정자
    private String lastModifiedBy;
    // 또한, 등록자와 수정자에 넣어줄 Bean 설정을 Application.java 에 같이 해줘야 한다.
}
