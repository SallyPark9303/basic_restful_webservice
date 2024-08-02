package kr.co.consulting.myrestfulservice.Repository;

import kr.co.consulting.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// 기존의 레파지토리를 사용
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
}
