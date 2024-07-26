package kr.co.consulting.myrestfulservice.bean;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")
public class AdminUser {

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    private  Integer id;
    @Past(message= "등록일은 미래 날짤르 입력하실 수 없습니다.")
    private  String name;
    private Date joinDate;

    // @JsonIgnore : 외부에 노출하고 싶지 않은 데이터
    //@JsonIgnore
    private String password;
    //@JsonIgnore
    private String ssn;


}
