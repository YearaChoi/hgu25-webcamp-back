package com.example.hgu25_webcamp_back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cpost")
public class Cpost {
    @Id
    private String id;

    @Setter
    @Column(nullable = false)
    private String deleted;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 2000000)
    @Lob
    private String description;

    @Setter
    @Column(nullable = false)
    private String category;

    @Setter
    @Column(nullable = false)
    private String imageName;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // JPA 요구사항에 따라 기본 생성자를 `protected`로 설정
    // 기본 생성자는 외부에서 직접 호출되지 않으며 JPA가 엔티티를 생성할 때 사용
    protected Cpost() {}

    private Cpost(String title, String description, String category, String imageName) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageName = imageName;
    }

    // 정적 팩토리 메서드: cpost 객체를 생성하는 방법
    public static Cpost of(String title, String description, String category, String imageName) {
        return new Cpost(title, description, category, imageName);
    }

    // 엔티티가 영속성 컨텍스트에 저장되기 전에 실행되는 메서드
    // @PrePersist: JPA 라이프사이클 이벤트로, 데이터가 처음 저장되기 전 실행
    @PrePersist
    public void onPrePersist() {
        // 엔티티가 저장되기 전에 id 값을 UUID로 생성
        this.id = UUID.randomUUID().toString().replace("-", "");
        // 엔티티가 처음 생성될 때 삭제 여부는 "N"으로 설정됨
        this.deleted = "N";
    }
}
