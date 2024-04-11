package com.taewook.basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taewook.basic.dto.ResponseSampleDto;

@RestController  // json형태로 반환받기 위한 어노테이션

// HTTP * localhost:4000/response-data/**
@RequestMapping("/response-data")

public class ResponseDataController {
    
    // @ResponseEntity : 
    // - Response의 header, status code, status message, data를 조작할 수 있도록 하는 클래스
    @GetMapping("/{number}")
    public ResponseEntity<String> getNumber(
        @PathVariable("number") Integer number  // @PathVarialbe url에 {}에 있는 값을 받기 위한 어노테이션
    ){
        // String body = number > 0 ? "양수": number < 0 ? "음수" : "영";
        // return new ResponseEntity<>(body, HttpStatusCode.valueOf(400));
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);  // 윗 라인과 둘 다 똑같은 기능?형태?
        ResponseEntity<String> response = null;

        if (number < 0) response = ResponseEntity.status(202).body("음수");
        if (number == 0 ) response = ResponseEntity.status(201).body("영");
        if (number > 0) response = ResponseEntity.status(200).body("양수");
        return response;
    }

    @GetMapping("/response/dto")
    public ResponseEntity<ResponseSampleDto> getResponseDto(){  // ResponseEntity로 status code와 body를 변경할 수있도록 반환값을 잡고 반환값의 타입은 만들어둔 ResponseSampleDto 타입으로 받도록 지정
        ResponseEntity<ResponseSampleDto> response =  ResponseEntity.status(201).body(new ResponseSampleDto("문자열", 99));
        return response;
    } 

}
