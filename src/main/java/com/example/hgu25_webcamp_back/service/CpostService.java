package com.example.hgu25_webcamp_back.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// @Service: 해당 인터페이스가 서비스 계층의 컴포넌트임을 나타냄
@Service
// 게시물 관련 비즈니스 로직을 처리하는 메서드 정의
public interface CpostService {
    public Map<String, Object> create(Map<String, Object> param);
    public Map<String, Object> update(Map<String, Object> param);
    public Map<String, Object> get(String id);
    public Map<String, Object> delete(String id);
    public List<Map<String, Object>> getAll();
    public List<Map<String, Object>> categoryGetAll(String category);
}




