package kr.co.consulting.myrestfulservice.Dao;


import kr.co.consulting.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component  // bean의 유형을 지정하실수 있는데 특별한 용도를 가지고 있지 않거나 컨트롤러 서비스에 속하지 않는 일반적인 컴포넌트를  어노테이션을사용해서 표시
public class UserDaoService {

    private static   List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static{
        users.add(new User(1, "Kenneth",  new Date(),"test1","1111-11111"));
        users.add(new User(2, "Alice", new Date(),"test2","2222-22222"));
        users.add(new User(3, "Elena", new Date(),"test3","3333-33333"));

    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId()==null){
            user.setId(++usersCount);
        }

        if(user.getJoinDate() ==null){
            user.setJoinDate(new Date());
        }
        users.add(user);

        return user;

    }

    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId()== id){
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
