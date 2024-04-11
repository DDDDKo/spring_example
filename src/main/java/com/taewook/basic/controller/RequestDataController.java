package com.taewook.basic.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taewook.basic.dto.SampleDto;

import jakarta.validation.Valid;

@RestController

// HTTP * localhost:4000/reqeust-data/**
@RequestMapping("/request-data")
public class RequestDataController {

    // @RequestParam() :
    // - GET, DELETE 처럼 URL로 데이터를 전송하는 메서드에서 Query String 으로 지정된 데이터를 가져오기 위해 사용

    // HTTP GET localhost:4000/request-data/request-param
    @GetMapping("/request-param")

    // http://localhost:4000/request-data/request-param?userId=qwer&userName=gildong
    public String getRequestParam(
        @RequestParam(name="userId") String userId, // 무조건 받아야 하는 방식
        @RequestParam(name="userName", required = false) String userName,  // required = false로 지정하면 필수로 입력 하지 않아도 됨, 무조건 받지 않아도 되는 방식
        @RequestParam() int userAge // 좋지 않은 방식
    ) {
        return "사용자 아이디 : " + userId + " / 사용자 이름 : " + userName + " / 사용자 나이:" + userAge;
    }

    // @PathVariable() :
    // - 모든 HTTP 메서드에서 URL의 특정 패턴에 따라서 데이터를 추출하는 방식

    // HTTP DELETE localhost:4000/request-data/path-variable
    @DeleteMapping({    
        "/path-variable/{age}",     //url 중복 지정 하는법 ({ "url1", "url2", ...})
        "/path-variable/{age}/{name}",
        "/path-variable/{age}/"
    })
    // http://localhost:4000/request-data/path-variable/10/gilldong
    public String deletePathVariable(
        @PathVariable("age" ) Integer age,
        @PathVariable(name = "name", required = false) String name
    ){
        return "사용자 나이 : " + age + " / 사용자 이름 : " + name;
    }

    // HTTP PATCH localhost:4000/request-data/patch/gildong/update
    @PatchMapping("/patch/{userName}/update")
    // http://localhost:4000/request-data/patch/gildong/update
    public String patchUpdate (
        @PathVariable("userName") String userName
    ){
        return "사용자 이름 : " + userName;
    }
    /// 여기서 똑같이 값을 받아오는 기능을 하는데 뭐가 다른 것인지 모르겠음 => 사회적으로? 규칙?으로 정해놓았지만 우리가 정하는것임 꼭 Get일때 받아오고 Delete일때 삭제하고 Patch일때 수정하는 것이 아니고 내가 정해서 사용하는것임
    //! *****주의
    // URL 패턴으로 데이터를 받아오는 방식을 썼을 때 겹치는 패턴이 존재하는지 잘 확인 해야 함
    //! *****주의
    @GetMapping("/{value}/get")
    public String getPathVariable1(
        @PathVariable("value") String value
    ){
        return "getPathVariable1";
    }
    
    @GetMapping("/get/{value}")
    public String getPathVariable2(
        @PathVariable("value") String value
    ){
        return "getPathVariable2";
    }

    // @RequestBody() :
    // - POST, PATCH, PUT 처럼 RequestBody로 데이터를 전송하는 메서드에서 데이터를 가져오기 위해 사용

    // HTTP POST localhost:4000/request-data/post
    @PostMapping("/post")
    public String post(
        // @RequestBody String text
        // @Valid : 해당 payload에 대해서 유효성 검사를 실시하도록 함
        @RequestBody @Valid SampleDto dto
    ){
        return "전송한 데이터 : " + dto.toString();
    }

}
