package kr.co.consulting.myrestfulservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.consulting.myrestfulservice.Dao.UserDaoService;
import kr.co.consulting.myrestfulservice.bean.User;
import kr.co.consulting.myrestfulservice.exception.UsernotFoundException;
import org.apache.coyote.Response;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name="user-controller", description = "일반 사용자 서비스를 위한 컨트롤러입니다.") // 클래스에 대한 설명
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
/*
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
    }*/

    // 헤테오스
    @Operation(summary="사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보 조회를 합니다.") //메소드에 대한 설명
    @ApiResponses({
            @ApiResponse(responseCode="200", description="OK !!"),
            @ApiResponse(responseCode="400", description="BAD REQUEST !!"),
            @ApiResponse(responseCode="404", description="USER NOT FOUND !!"),
            @ApiResponse(responseCode="500", description="INTERNAL SERVER ERROR")
    })
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser2(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable int id){
        User user =  service.findOne(id);
        if(user == null){
            throw new UsernotFoundException(String.format("ID [%S] not found", id));

        }
        EntityModel entityModel = EntityModel.of(user);

        //헤테오스 링크작업
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // all-users -> http://localhost:8088/users
        return entityModel;
    }




    //@Valid : User 에 대해 유효성 검사를  하겠다는 표시
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
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
