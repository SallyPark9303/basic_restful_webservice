package kr.co.consulting.myrestfulservice.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "My Restful Service API 명세서",
            description = "Spring Boot 로 개발하는 RESTful API 명세서 입니다.",
            version="v1.0.0"
    )

)
@Configuration   // configure 클래스의 정보를 만들기 위해 반드시 해당하는 클래스 상단에 @Configuration 어노테이션 추가
@RequiredArgsConstructor

public class NewSaggerConfig {
    @Bean
    public GroupedOpenApi customTestOpenAPI(){
        String[] paths = {"/users/**","/admin/**"};
        return GroupedOpenApi.builder()
                .group("일반 사용자와 관리자를 위한 user 도메인에 대한 API")
                .pathsToMatch(paths)
                .build();
    }
}
