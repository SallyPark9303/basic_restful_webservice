package kr.co.consulting.myrestfulservice.controller;

import kr.co.consulting.myrestfulservice.bean.HelloWorldBean;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController // 스프링 컨텍스트에 등록함
public class HelloWorldController {

    // Get
    // uri - /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")

    // 메모리 소스 값을 선언하고 생성자를 통해서 주입받도록 함
    private MessageSource messageSource;

    // 주입 : 스프링 컨텍스트에 의해 기동이 될 때 해당하는 인스턴스를 미리 만들어 놓고 메모리에 등록을 함. 미리 등록되어진 다른 스프링의 빈을 가지고 와서
    // 현재 있는 클래스에서 사용 할 수 있도록 객체를 생성하지 않더라도 참조 할 수 있는 형태로 받아오는 것을 주입 **
    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

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


    @GetMapping(path="/hello-world-bean-internationlized")
    public String helloworldBeanInternalized(
            @RequestHeader(name="Accept-Language", required= false) Locale locale
    ){
        return messageSource.getMessage("greeting.message", null, locale);
    }

}
