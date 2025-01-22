package com.example.hgu25_webcamp_back.controller;

import com.example.hgu25_webcamp_back.service.CpostService;
import org.springframework.web.bind.annotation.*;

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

    // POST 요청을 처리하는 메서드
    @PostMapping("")
    public Map<String, Object> create(@RequestBody Map<String, Object> param) {
        System.out.println(param);
        // 서비스의 create 메서드를 호출하여 결과 반환
        return cpostService.create(param);
    }

    // PUT 요청을 처리하는 메서드
    // 요청할 때, JSON 데이터를 Body에 담아 보내야 함
    @PutMapping("")
    public Map<String, Object> update(@RequestBody Map<String, Object> param) {
        System.out.println(param);
        // 서비스의 update 메서드를 호출하여 결과를 반환
        return cpostService.update(param);
    }

    // DELETE 요청을 처리하는 메서드, 해당 id 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable("id") String id) {
        System.out.println(id);
        // 서비스의 delete 메서드를 호출하여 결과를 반환
        return cpostService.delete(id);
    }

    // GET 요청을 처리하는 메서드, 해당 id 게시글 불러오기
    @GetMapping("/get/{id}")
    public Map<String, Object> detail(@PathVariable("id") String id) {
        System.out.println(id);
        // 서비스의 get 메서드를 호출하여 결과를 반환
        return cpostService.get(id);
    }

    // GET 요청을 처리하는 메서드로, 특정 카테고리의 모든 게시물을 가져옴
    @GetMapping("/category/{category}")
    public List<Map<String, Object>> board(@PathVariable("category") String category) {
        System.out.println(category);
        // 서비스의 categoryGetAll 메서드를 호출하여 결과를 반환
        return cpostService.categoryGetAll(category);
    }

    // GET 요청을 처리하는 메서드로, 모든 게시물을 가져옴
    @GetMapping("/getAll")
    public List<Map<String, Object>> getAll() {
        // 서비스의 getAll 메서드를 호출하여 결과를 반환
        return cpostService.getAll();
    }
}

