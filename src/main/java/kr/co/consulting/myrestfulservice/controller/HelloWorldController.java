package kr.co.consulting.myrestfulservice.controller;

import kr.co.consulting.myrestfulservice.bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // 스프링 컨텍스트에 등록함
public class HelloWorldController {

    // Get
    // uri - /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")

    @GetMapping(path="/hello-world")
    public String helloworld(){
        return "Hello World";
    }


    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloworldBean(){
        // 반환 형식이 bean 타입으로 되어있고 이렇게 전달하게 되면 스프링 부트에서는 이것을 자동으로 responsebody 로 변환 시켜주고 있기 때문에
        // json 형태 값을 가지고 데이터가 나타내질 것임
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }


}
