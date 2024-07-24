package kr.co.consulting.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // 프로퍼티의 setter/ getter 생성
@AllArgsConstructor // 현재 가지고 있는 모든 프로퍼티들을 다 사용
public class HelloWorldBean {
    private String message;
//    public HelloWorldBean(String message){
//        this.messae = message;
//
//    }
}
