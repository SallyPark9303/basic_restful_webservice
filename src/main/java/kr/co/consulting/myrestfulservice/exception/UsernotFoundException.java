package kr.co.consulting.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND) // 500 대신 404 번 상태코드 발생 서버의 자원이 없을때 , 자원이 없는 것을 요청한 클라이언트의 잘못이기때문에
public class UsernotFoundException extends RuntimeException {

    public UsernotFoundException(String message){
        super(message);
    }

}
