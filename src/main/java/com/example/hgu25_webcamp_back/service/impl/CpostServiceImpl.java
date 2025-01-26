package com.example.hgu25_webcamp_back.service.impl;

import com.example.hgu25_webcamp_back.domain.Cpost;
import com.example.hgu25_webcamp_back.repository.CpostRepository;
import com.example.hgu25_webcamp_back.service.CpostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.*;

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
    public Map<String, Object> create(Map<String, Object> param, MultipartFile file) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println(param);

        try {
            // 파일 저장 경로 설정
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID(); // 고유 ID 생성
            String imageName = uuid + "-" + file.getOriginalFilename(); // 고유한 파일 이름 생성
            File saveFile = new File(projectPath, imageName);

            // 파일 저장
            file.transferTo(saveFile);

            // 데이터베이스에 저장할 상대 경로 생성
            String imagePath = "/files/" + imageName;

            // 전달받은 데이터를 기반으로 cpost 객체 생성
            Cpost cpost = Cpost.of(
                    param.get("title") + "",
                    param.get("price") + "",
                    param.get("description") + "",
                    param.get("category") + "",
                    imageName,
                    imagePath // 상대 경로 저장
            );

            // 생성된 게시물을 데이터베이스에 저장
            cpostRepository.save(cpost);

            // 결과로 ID를 반환
            returnMap.put("id", cpost.getId());
            // 저장된 이미지 경로 반환
            returnMap.put("imagePath", imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("error", "파일 저장 실패: " + e.getMessage());
        }

        return returnMap;
    }

    // 게시물 업데이트
//    public Map<String, Object> update(Map<String, Object> param, MultipartFile file) {
//        Map<String, Object> returnMap = new HashMap<>();
//        System.out.println(param);
//        // ID로 기존 게시물 조회
//        Cpost cpost = cpostRepository.findById(param.get("id") + "").orElseThrow(() -> new RuntimeException(""));
//
//        if (param.get("title") != null) {
//            cpost.setTitle(param.get("title") + "");
//        }
//        if (param.get("description") != null) {
//            cpost.setDescription(param.get("description") + "");
//        }
//        if (param.get("category") != null) {
//            cpost.setCategory(param.get("category") + "");
//        }
//
//        if (param.get("imageName") != null) {
//            cpost.setImageName(param.get("imageName") + "");
//        }
//
//        // 업데이트된 데이터를 저장
//        cpostRepository.save(cpost);
//
//        // 결과로 ID와 상태를 반환
//        returnMap.put("id", cpost.getId());
//        returnMap.put("updated", "complete");
//        return returnMap;
//    }
    public Map<String, Object> update(Map<String, Object> param, MultipartFile file) {
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println(param);
        // ID로 기존 게시물 조회
        Cpost cpost = cpostRepository.findById(param.get("id") + "").orElseThrow(() -> new RuntimeException(""));

        if (param.get("title") != null) {
            cpost.setTitle(param.get("title") + "");
        }

        if (param.get("price") != null) {
            cpost.setPrice(param.get("price") + "");
        }

        if (param.get("description") != null) {
            cpost.setDescription(param.get("description") + "");
        }
        if (param.get("category") != null) {
            cpost.setCategory(param.get("category") + "");
        }

        // 파일이 포함되었을 때 파일 업데이트 처리
        if (file != null) {
            // 이전에 저장된 이미지 파일 삭제 (옵션)
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            File oldFile = new File(projectPath, cpost.getImageName());
            if (oldFile.exists()) {
                oldFile.delete();
            }

            // 새로운 이미지 저장
            try {
                UUID uuid = UUID.randomUUID();
                String imageName = uuid + "-" + file.getOriginalFilename();
                File saveFile = new File(projectPath, imageName);

                // 파일 저장
                file.transferTo(saveFile);

                // 새로운 이미지 경로 설정
                String imagePath = "/files/" + imageName;
                cpost.setImageName(imageName);
                cpost.setImagePath(imagePath); // 해당 경로 업데이트
            } catch (Exception e) {
                e.printStackTrace();
                returnMap.put("error", "파일 저장 실패: " + e.getMessage());
                return returnMap;
            }
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
        returnMap.put("price", cpost.getPrice());
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
            returnMap.put("price", cpost.getPrice());
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
            returnMap.put("price", cpost.getPrice());
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