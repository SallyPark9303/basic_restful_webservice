package kr.co.consulting.myrestfulservice.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password","ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity // 클래스의 이름으로서 테이블 생성
@Table(name ="users")
public class User {

    @Schema(title ="사용자 ID", description="사용자 ID는 자동 생성됩니다.")
    @Id
    @GeneratedValue
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

    // user :기준점
    @OneToMany(mappedBy =  "user")
    private List<Post> posts;

    // posts 떄문에 기본생성자가 생기지 않았다.


    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
