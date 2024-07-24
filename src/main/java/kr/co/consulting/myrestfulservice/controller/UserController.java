package kr.co.consulting.myrestfulservice.controller;


import kr.co.consulting.myrestfulservice.Dao.UserDaoService;
import kr.co.consulting.myrestfulservice.bean.User;
import kr.co.consulting.myrestfulservice.exception.UsernotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user =  service.findOne(id);
        if(user == null){
            // 해당id의 유저가 없을때 exception 발생
            throw new UsernotFoundException(String.format("ID [%S] not found", id)); 
            // 500 Internal Service Error 발생  
            // 하지만 보안상의 문제 떄문에 500 에러대신에 적절한 상태코드를 발생시켜야함
        }
        return user;
    }



    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = service.save(user);
        
        // 주소값을 반환하는데 생성된 사용자의 상세 정보  주소 url 을 반환
        // 현재 요청된 uli 에  파라미터 추가
        // 트래픽 감소
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build(); // 201번 상태값 반환

        // 적절한 메소드, 적절한 반환값, 상태 코드 이렇게 구분하는 것이 REST API 를 만들때 필요한 기본적인 설계 가이드

    }
    @DeleteMapping("users/{id}")
    // 반환 상태값 변경하려면 ㅇ반환값을  ResponseEntity 타입으로 변경
    public ResponseEntity deleteUser(@PathVariable int id){
        User deletedUser = service.deleteById(id);

        if(deletedUser ==null){
            throw new UsernotFoundException(String.format("ID[%S] NOT FOUND", id));
        }

        return ResponseEntity.noContent().build(); // 204 상태코드 반환


    }



}
