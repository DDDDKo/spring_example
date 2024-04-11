package com.example.basic.service;

public interface BasicService {
    // 인터페이스 메서드는 반드시 public abstract 이어야 한다 (반드시 public abstract 이어야 하기 때문에 생략 가능)
    // public abstract String getHello ();

    // public abstract 제어자 생략 가능
    String getHello(); 
    String getApple();
}
