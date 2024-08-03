package kr.co.consulting.myrestfulservice.Repository;

import kr.co.consulting.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// 기존의 레파지토리를 사용
@Repository
//JpaRepository 의 첫번쨰 인자 : 어디에 있는 객체를 다룰 것인지 명시, 두번째 인자 : id 를 integer라고 명시함
public interface UserRepository extends JpaRepository<User,Integer> {
    
}
