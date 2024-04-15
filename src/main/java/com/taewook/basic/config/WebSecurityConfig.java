package com.taewook.basic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.taewook.basic.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
// @Configurable :
// - 생성자나 '메서드'가 호출시에 Spring bean 등록을 자동화(제어 역전)하는 어노테이션
@Configurable
// @EnableWebSecurity
// - Web Security 설정을 지원하도록 하는 어노테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // @Bean :
    // - Spring bean으로 등록하는 어노테이션
    // - @Component를 사용하지 못할때 사용 (메서드일때 사용하지만 클래스에서도 사용 가능)
    // - @Autowired의 목적이 아닐때 사용(인스턴스 생성할 목적이 아닐때)
    @Bean
    protected SecurityFilterChain configure(HttpSecurity security) throws Exception {
        // calss::method :
        // - 메서드 참조, 특정 클래스의 메서드를 참조할 때 사용
        // - 일반적으로 매개변수로 메서드를 전달할때 사용됨
        
        security
        // basic authentication 미사용 지정
        .httpBasic(HttpBasicConfigurer::disable)
        // session :
        // - 웹 애플리케이션에서 사용자의 대한 정보 및 상태를 유지하기 위한 기술
        // - 서버측에서 사용자 정보 및 상태를 저장하는 방법
        // - REST API 서버에서는 사용자 정보 및 상태를 클라이언트가 유지하기 때문에 session을 생성하지 않음
        // cookie : =>세션과 동일하지만 저장위치가다름
        // - 웹 애플리케이션에서 사용자의 대한 정보 및 상태를 유지하기 위한 기술
        // - 클라이언트 측에서 사용자 정보 및 상태를 저장하는 방법
        // session과 cookie의 차이 :
        // - 저장위치 : cookie는 클라이언트, session은 서버에 저장
        // - 보안 : session이 보안 수준이 더 높음, cookie의 보안 수준이 session보다 떨어짐
        // - 수명 : cookie는 지정한 기간동안 지속적으로 유지, session은 연결이 끊기면 파기됨
        // - 용도 : cookie에는 간단한 데이터(id, token)를 저장, session은 민감한 데이터(개인정보)를 저장
        // cache : 
        // - 데이터나 값을 미리 복사해두고 저장하는 임시 공간
        // - 사용자의 접근을 조금 더 빠르게 할 수 있도록 함(한번 접근했던 페이지가 더 빠르게 로드됨)
        // - 시스템 성능 향상
        // - 하드웨어 캐시 : cpu cache, disk cache
        // - 소프트웨어 캐시 : wen cache, database cache
        // - 네트워크 캐시 : CDN 
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // CSRF (Cross-Site Request Forgery): 
        // - 클라이언트(사용자)가 자신의 의도와는 무관한 공격행위를 하는 것
        // SQL Injection :
        // - 공격자가 데이터 베이스의 쿼리문을 직접 조작하여 데이터를 탈취하는 공격
        // XSS (Cross-Site Script) :
        // - 공격자가 웹 브라우저에 악성 스크립트를 작성하여 실행시키는 공격
        .csrf(CsrfConfigurer::disable);

        // 우리가 생성한 jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 등록
        security.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return security.build();
    };

}