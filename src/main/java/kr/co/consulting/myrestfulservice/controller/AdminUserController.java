package kr.co.consulting.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.co.consulting.myrestfulservice.Dao.UserDaoService;
import kr.co.consulting.myrestfulservice.bean.AdminUser;
import kr.co.consulting.myrestfulservice.bean.AdminUserV2;
import kr.co.consulting.myrestfulservice.bean.User;
import kr.co.consulting.myrestfulservice.exception.UsernotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    // /admin/users/{id}
    // /admin/users

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUserAdmin(@PathVariable int id){
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UsernotFoundException(String.format("ID[%s] not found", id));
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        // 보여질 데이터를 필터해서 보여줌
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;

    }

    //  uri를 이용한 버전관리 versioning
    // --> /admin/v1/users/{id}
   // @GetMapping("/v1/users/{id}")
  // @GetMapping(value = "/users/{id}", params = "version=1")
  // @GetMapping(value = "/users/{id}",headers = "X-API-VERSION=1")
   @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json") //마인타입
    public MappingJacksonValue retrieveUserAdminV1(@PathVariable int id){
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UsernotFoundException(String.format("ID[%s] not found", id));
        }else{
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        // 보여질 데이터를 필터해서 보여줌
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;

    }

    //--> admin/v2/users/{id}
    //@GetMapping("/v2/users/{id}")
    @GetMapping(value = "/users/{id}", params = "version=2") // 파라미터 버전관리 url + ? 파라미터 : 쿼리 스트링 사용
    public MappingJacksonValue retrieveUserAdminV2(@PathVariable int id){
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UsernotFoundException(String.format("ID[%s] not found", id));
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        // 보여질 데이터를 필터해서 보여줌
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;

    }





    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsersAdimin(){

        List<User> users  = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<AdminUser>();

        AdminUser adminUser = new AdminUser();
        AdminUser adminuser = null;
        for (User user : users){
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser); // user 에 있는 내용을 adminUser 로 복사

            adminUsers.add(adminUser);

        }


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        // 보여질 데이터를 필터해서 보여줌
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;

    }
}
