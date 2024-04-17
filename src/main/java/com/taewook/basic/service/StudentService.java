package com.taewook.basic.service;

import org.springframework.http.ResponseEntity;

import com.taewook.basic.dto.Request.Student.PatchStudentRequestDto;
import com.taewook.basic.dto.Request.Student.PostStudentRequestDto;
import com.taewook.basic.dto.Request.Student.SignInRequestDto;

public interface StudentService {
    ResponseEntity<String> postStudent(PostStudentRequestDto dto);
    ResponseEntity<String> patchStudent(PatchStudentRequestDto dto);
    ResponseEntity<String> deleteStudent(Integer studentNumber);
    ResponseEntity<String> signIn(SignInRequestDto dto);
}
