package kr.co.consulting.myrestfulservice.Repository;

import kr.co.consulting.myrestfulservice.bean.Post;
import kr.co.consulting.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PostRepository extends JpaRepository<Post,Integer> {
    
}
