package com.example.hgu25_webcamp_back.service.impl;

import com.example.hgu25_webcamp_back.domain.Cpost;
import com.example.hgu25_webcamp_back.repository.CpostRepository;
import com.example.hgu25_webcamp_back.service.CpostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CpostServiceImpl implements CpostService {
    // cpostRepository를 사용하기 위해 주입
    private final CpostRepository cpostRepository;

    // 생성자를 통해 cpostRepository를 주입받습니다.
    public CpostServiceImpl(
            CpostRepository cpostRepository
    ) {
        this.cpostRepository = cpostRepository;
    }

    // 게시물 생성
    public Map<String, Object> create(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println(param);
        // 전달받은 데이터를 기반으로 cpost 객체를 생성
        Cpost cpost = Cpost.of(param.get("title") + "", param.get("description") + "", param.get("category") + "", param.get("imageName") + "");
        // 생성된 게시물을 데이터베이스에 저장
        cpostRepository.save(cpost);

        // 결과로 ID를 반환
        returnMap.put("id", cpost.getId());
        return returnMap;
    }

    // 게시물 업데이트
    public Map<String, Object> update(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println(param);
        // ID로 기존 게시물 조회
        Cpost cpost = cpostRepository.findById(param.get("id") + "").orElseThrow(() -> new RuntimeException(""));

        if (param.get("title") != null) {
            cpost.setTitle(param.get("title") + "");
        }
        if (param.get("description") != null) {
            cpost.setDescription(param.get("description") + "");
        }
        if (param.get("category") != null) {
            cpost.setCategory(param.get("category") + "");
        }

        if (param.get("imageName") != null) {
            cpost.setImageName(param.get("imageName") + "");
        }

        // 업데이트된 데이터를 저장
        cpostRepository.save(cpost);

        // 결과로 ID와 상태를 반환
        returnMap.put("id", cpost.getId());
        returnMap.put("updated", "complete");
        return returnMap;
    }

    // 게시물 삭제
    public Map<String, Object> delete(String id) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println();
        // ID로 기존 게시물 조회
        Cpost cpost = cpostRepository.findById(id).orElseThrow(() -> new RuntimeException(""));

        // 게시물을 데이터베이스에서 삭제
        cpostRepository.delete(cpost);

        // 결과로 ID와 상태를 반환
        returnMap.put("id", cpost.getId());
        returnMap.put("updated", "complete");
        return returnMap;
    }

    // 특정 ID에 해당하는 게시물 조회
    public Map<String, Object> get(String id) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println(id);
        // ID로 게시물을 조회
        Cpost cpost = cpostRepository.findById(id).orElseThrow(() -> new RuntimeException(""));

        // 조회된 게시물의 정보 반환
        returnMap.put("id", cpost.getId());
        returnMap.put("title", cpost.getTitle());
        returnMap.put("description", cpost.getDescription());
        returnMap.put("category", cpost.getCategory());
        returnMap.put("imageName", cpost.getImageName());
        returnMap.put("createdAt", cpost.getCreatedDate());
        returnMap.put("modifiedAt", cpost.getModifiedDate());

        return returnMap;
    }

    // 모든 게시물 조회
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> result = new ArrayList<>();
        // 모든 게시물 조회
        List<Cpost> allFeed = cpostRepository.findAll();

        // 조회된 게시물 각각에 대해 정보를 매핑하여 리스트에 추가
        for (Cpost cpost : allFeed) {
            Map<String, Object> returnMap = new HashMap<>();

            returnMap.put("id", cpost.getId());
            returnMap.put("title", cpost.getTitle());
            returnMap.put("description", cpost.getDescription());
            returnMap.put("category", cpost.getCategory());
            returnMap.put("imageName", cpost.getImageName());
            returnMap.put("createdAt", cpost.getCreatedDate());
            returnMap.put("modifiedAt", cpost.getModifiedDate());
            result.add(returnMap);
        }

        return result;
    }

    // 특정 카테고리의 모든 게시물을 조회
    public List<Map<String, Object>> categoryGetAll(String category) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 특정 게시판 이름에 해당하는 모든 게시물을 조회
        List<Cpost> allFeed = cpostRepository.findAllByCategory(category);

        // 조회된 게시물 각각에 대해 정보를 매핑하여 리스트에 추가
        for (Cpost cpost : allFeed) {
            Map<String, Object> returnMap = new HashMap<>();

            returnMap.put("id", cpost.getId());
            returnMap.put("title", cpost.getTitle());
            returnMap.put("description", cpost.getDescription());
            returnMap.put("category", cpost.getCategory());
            returnMap.put("imageName", cpost.getImageName());
            returnMap.put("createdAt", cpost.getCreatedDate());
            returnMap.put("modifiedAt", cpost.getModifiedDate());
            result.add(returnMap);
        }

        return result;
    }
}