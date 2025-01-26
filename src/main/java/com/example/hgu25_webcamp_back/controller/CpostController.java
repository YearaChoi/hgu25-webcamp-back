package com.example.hgu25_webcamp_back.controller;

import com.example.hgu25_webcamp_back.service.CpostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

// RequestMapping: 컨트롤러가 처리할 기본 URL을 "/api/cpost"로 설정
@RequestMapping("/api/cpost")
@RestController
public class CpostController {
    // CpostService를 주입받아 사용하기 위해 선언
    private final CpostService cpostService;

    // 생성자를 통해 cpostService를 주입 (의존성 주입)
    public CpostController(CpostService cpostService) {
        this.cpostService = cpostService;
    }

@PostMapping("")
@CrossOrigin(origins = "http://localhost:3000/")
public Map<String, Object> create(
        // request part는 무조건 param, file이 수신되어야 함, 아닌 경우 400에러 발생
        @RequestPart("param") String paramJson,  // JSON 문자열 수신
        @RequestPart("file") MultipartFile file  // 파일 수신
) {
    // JSON 문자열을 Map으로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> param = null;
    try {
        param = objectMapper.readValue(paramJson, Map.class); // JSON 문자열을 Map으로 변환
    } catch (Exception e) {
        e.printStackTrace();
        return Map.of("error", "Invalid JSON format");
    }

    System.out.println("Received param: " + param);
    System.out.println("Received file: " + file.getOriginalFilename());

    return cpostService.create(param, file); // 파일과 데이터를 서비스로 전달
}

    // PUT 요청을 처리하는 메서드
    @PutMapping("")
    @CrossOrigin(origins = "http://localhost:3000/")
    public Map<String, Object> update(
            @RequestPart("param") String paramJson,  // JSON 데이터 받기
            @RequestPart(value = "file", required = false) MultipartFile file  // 파일 받기 (없을 수도 있음)
    ) {
        // JSON 문자열을 Map으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> param = null;
        try {
            param = objectMapper.readValue(paramJson, Map.class); // JSON 문자열을 Map으로 변환
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Invalid JSON format");
        }

        return cpostService.update(param, file); // 파일과 데이터를 서비스로 전달
    }


    // DELETE 요청을 처리하는 메서드, 해당 id 게시글 삭제
    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "http://localhost:3000/")
    public Map<String, Object> delete(@PathVariable("id") String id) {
        System.out.println(id);
        // 서비스의 delete 메서드를 호출하여 결과를 반환
        return cpostService.delete(id);
    }

    // GET 요청을 처리하는 메서드, 해당 id 게시글 불러오기
    @GetMapping("/get/{id}")
    @CrossOrigin(origins = "http://localhost:3000/")
    public Map<String, Object> detail(@PathVariable("id") String id) {
        System.out.println(id);
        // 서비스의 get 메서드를 호출하여 결과를 반환
        return cpostService.get(id);
    }

    // GET 요청을 처리하는 메서드로, 특정 카테고리의 모든 게시물을 가져옴
    @GetMapping("/category/{category}")
    @CrossOrigin(origins = "http://localhost:3000/")
    public List<Map<String, Object>> board(@PathVariable("category") String category) {
        System.out.println(category);
        // 서비스의 categoryGetAll 메서드를 호출하여 결과를 반환
        return cpostService.categoryGetAll(category);
    }

    // GET 요청을 처리하는 메서드로, 모든 게시물을 가져옴
    @GetMapping("/getAll")
    @CrossOrigin(origins = "http://localhost:3000/")
    public List<Map<String, Object>> getAll() {
        // 서비스의 getAll 메서드를 호출하여 결과를 반환
        return cpostService.getAll();
    }
}

