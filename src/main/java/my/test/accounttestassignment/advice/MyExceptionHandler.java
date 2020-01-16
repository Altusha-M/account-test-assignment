package my.test.accounttestassignment.advice;

import my.test.accounttestassignment.exception.NotEnoughMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

//    public ResponseEntity<String> notEnoughMoney(){
//        return new ResponseEntity<String>("not_enough_money", HttpStatus.CONFLICT);
//    }
    @ExceptionHandler(NotEnoughMoneyException.class)
    public Exception notEnoughMoney(){
        return new NotEnoughMoneyException("toss a coin");
    }
}
