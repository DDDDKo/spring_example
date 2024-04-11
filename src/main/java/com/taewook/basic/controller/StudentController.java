package com.taewook.basic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taewook.basic.dto.Request.Student.PatchStudentRequestDto;
import com.taewook.basic.dto.Request.Student.PostStudentRequestDto;
import com.taewook.basic.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor // spring boot한테 제어건을 넘겨서 생성자를 이용해서 제어하게함
public class StudentController {
    
    private final StudentService studentService;

    // Post는 CRUD중 C Create
    @PostMapping("/")   // 첫번째 /는 생략 가능하다
    public ResponseEntity<String> postStudent (
        @RequestBody @Valid PostStudentRequestDto requestbody
    ) {
        ResponseEntity<String> respose = studentService.postStudent(requestbody);
        return respose;
    }

    // Patch, Put은 CRUD중 Update 수정과 삭제는 primary key를 조건으로 걸어두는것이 가장 안전
    @PatchMapping("/")
    public ResponseEntity<String> patchStudent (
        @RequestBody @Valid PatchStudentRequestDto requestBody
    ) {
        ResponseEntity<String> response = studentService.patchStudent(requestBody);
        return response;
    }

    // DELTE는 CRUD중 DELETE  수정과 삭제는 primary key를 조건으로 걸어두는것이 가장 안전
    @DeleteMapping("/{studentNumber}")
    public ResponseEntity<String> deleteStudent (
        @PathVariable ("studentNumber") Integer studentNumber
    ) {
        ResponseEntity<String> response = studentService.deleteStudent(studentNumber);
        return response;
    }
}