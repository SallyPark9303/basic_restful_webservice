package kr.co.consulting.myrestfulservice.controller;

import jakarta.validation.Valid;
import kr.co.consulting.myrestfulservice.Repository.PostRepository;
import kr.co.consulting.myrestfulservice.Repository.UserRepository;
import kr.co.consulting.myrestfulservice.bean.Post;
import kr.co.consulting.myrestfulservice.bean.User;
import kr.co.consulting.myrestfulservice.exception.UsernotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // /jpa/users
    // 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // 개별 조회
    @GetMapping("/user/{id}")
    public ResponseEntity retrieveAUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UsernotFoundException("id- " + id);
        }
        // entity model 생성후 반환
        EntityModel entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));  // all-users ->http://localhost:8088/users
        return ResponseEntity.ok(entityModel);

    }

    // 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);

    }

    //추가
    @PostMapping("/users")
    public ResponseEntity<org.springframework.security.core.userdetails.User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UsernotFoundException("id- " +id);
        }
        return user.get().getPosts();
    }


    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post){


        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UsernotFoundException("id=" +id);
        }

        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        //헤테오스 기능으로서 추가되어진 정보값을 어디에서 확인할 수 있는지 연동
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}