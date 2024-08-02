package kr.co.consulting.myrestfulservice.config;

import jakarta.validation.Valid;
import kr.co.consulting.myrestfulservice.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean // 메모리 객체를 사용해서 스토리지로 사용
    UserDetailsService userDetailService(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails newUser = User.withUsername("user")
                .password(passwordEncoder().encode("passOrd"))
                .authorities("read")
                .build();

        userDetailsManager.createUser(newUser);
        return userDetailsManager;

    }

    @Bean // 인코딩 알고리즘 사용 , 사용안할시 오류 발생가능성
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // spirng security 인증을 건너뜀
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    // 자원의 변경(삭제,수정,추가)등이 일어났을때 오류 방지
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http,
                                              HandlerMappingIntrospector introspector) throws Exception  {

        //csrf를 통해서 해커가 어떤 사용자의 권한을 뺏어 가지고 주요한 어떤 기능을 대신 실행하게 하겠다는 의미
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
