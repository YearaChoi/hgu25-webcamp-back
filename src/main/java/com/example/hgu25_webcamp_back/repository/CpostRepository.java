package com.example.hgu25_webcamp_back.repository;

import com.example.hgu25_webcamp_back.domain.Cpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository: 이 인터페이스가 Spring에서 데이터 접근 계층의 컴포넌트임을 나타냄
@Repository
// cpost 인터페이스는 JpaRepository를 확장하며, cpost 엔티티와 기본 키 타입(String)을 사용
public interface CpostRepository extends JpaRepository<Cpost, String> {
    // 특정 카테고리에 해당하는 모든 cpost를 조회하는 메서드
    // JPA의 메서드 이름 규칙에 따라 자동으로 쿼리를 생성
    List<Cpost> findAllByCategory(String category);
}
