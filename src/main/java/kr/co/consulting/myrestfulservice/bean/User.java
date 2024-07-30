package kr.co.consulting.myrestfulservice.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password","ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Schema(title ="사용자 ID", description="사용자 ID는 자동 생성됩니다.")
    private  Integer id;
    @Past(message= "등록일은 미래 날짤르 입력하실 수 없습니다.")
    private  String name;
    @Schema(title ="사용자 등록일", description="사용자 등록일을 입력합니다.입력하지 않으면 현재 날짜가 지정됩니다..")
    private Date joinDate;

    // @JsonIgnore : 외부에 노출하고 싶지 않은 데이터
    //@JsonIgnore
    @Schema(title ="사용자 비밀번호", description="사용자의 비밀번호를 입력합니다..")
    private String password;
    //@JsonIgnore
    @Schema(title ="사용자 주민번호", description="사용자의 주민번호를 입력합니다..")
    private String ssn;



}
